/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "threadpoolexecutor.h"
#include <QDebug>

/**
 * @brief ThreadPoolExecutor::ThreadPoolExecutor Creates a new ThreadPoolExecutor with the given initial parameters and rejected execution handler.
 * @param corePoolSize the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
 * @param maxPoolSize the maximum number of threads to allow in the pool
 * @param keepAliveTime when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
 * @param policy the handler to use when execution is blocked because the thread bounds and queue capacities are reached
 */
ThreadPoolExecutor::ThreadPoolExecutor(int corePoolSize, int maxPoolSize,
                                     int keepAliveTime, RejectPolicy policy)
    : corePoolSize(corePoolSize),
      maxPoolSize(maxPoolSize),
      keepAliveTime(keepAliveTime),
      rejectPolicy(policy),
      running(true),
      activeThreads(0),
      completedTasks(0) {
    // 创建核心线程
    for (int i = 0; i < corePoolSize; ++i) {
        createThread();
    }
}

/**
 * @brief ThreadPoolExecutor::~ThreadPoolExecutor
 */
ThreadPoolExecutor::~ThreadPoolExecutor() {
    shutdown();
}

/**
 * @brief ThreadPoolExecutor::execute
 * @param task
 */
void ThreadPoolExecutor::execute(std::function<void()> task) {
    QMutexLocker locker(&mutex);

    if (!running.loadRelaxed()) {
        handleRejectedTask(task);
        return;
    }

    // 如果活跃线程数小于核心线程数，创建新线程
    if (activeThreads < corePoolSize) {
        createThread();
    }
    // 如果队列未满，添加到队列
    else if (taskQueue.size() < (maxPoolSize - activeThreads)) {
        taskQueue.enqueue(task);
        condition.wakeOne();
    }
    // 如果还能创建新线程
    else if (activeThreads < maxPoolSize) {
        createThread();
        taskQueue.enqueue(task);
        condition.wakeOne();
    }
    // 否则执行拒绝策略
    else {
        handleRejectedTask(task);
    }
}

/**
 * @brief ThreadPoolExecutor::shutdown
 */
void ThreadPoolExecutor::shutdown() {
    running.storeRelaxed(false);
    condition.wakeAll();

    for (QThread* thread : workerThreads) {
        thread->wait();
        delete thread;
    }
    workerThreads.clear();
}

long ThreadPoolExecutor::getCompletedTaskCount() const {
    return completedTasks.loadRelaxed();
}

int ThreadPoolExecutor::getActiveThreadCount() const {
    return activeThreads.loadRelaxed();
}

/**
 * @brief ThreadPoolExecutor::getQueueSize
 * @return
 */
int ThreadPoolExecutor::getQueueSize() const {
    QMutexLocker locker(&mutex);
    return taskQueue.size();
}

void ThreadPoolExecutor::createThread() {
    QThread* thread = QThread::create([this]() {
        workerRoutine();
    });
    thread->start();
    workerThreads.append(thread);
    activeThreads.fetchAndAddRelaxed(1);
}

void ThreadPoolExecutor::workerRoutine() {
    while (running.loadRelaxed()) {
        std::function<void()> task;

        {
            QMutexLocker locker(&mutex);

            // 等待任务或超时
            if (taskQueue.isEmpty()) {
                bool timedOut = !condition.wait(&mutex, keepAliveTime * 1000);

                // 如果是非核心线程且超时，且没有任务，则退出
                if (timedOut && activeThreads > corePoolSize && taskQueue.isEmpty()) {
                    break;
                }
            }

            if (!taskQueue.isEmpty()) {
                task = taskQueue.dequeue();
            } else if (!running.loadRelaxed()) {
                break;
            } else {
                continue;
            }
        }

        // 执行任务
        try {
            if (task) {
                task();
            }
            completedTasks.fetchAndAddRelaxed(1);
        } catch (...) {
            // 捕获任务中的异常，防止线程退出
            qWarning("Task execution threw an exception");
        }
    }

    // 线程退出
    activeThreads.fetchAndSubRelaxed(1);
}

void ThreadPoolExecutor::handleRejectedTask(std::function<void()>& task) {
    switch (rejectPolicy) {
    case RejectPolicy::Abort:
        throw ThreadPoolException("Task rejected from thread pool");
    case RejectPolicy::Discard:
        return; // 静默丢弃
    case RejectPolicy::CallerRuns:
        // 在调用者线程执行
        task();
        completedTasks.fetchAndAddRelaxed(1);
        break;
    }
}
