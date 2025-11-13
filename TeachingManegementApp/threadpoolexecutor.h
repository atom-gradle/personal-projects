/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef THREADPOOLEXECUTOR_H
#define THREADPOOLEXECUTOR_H

#include <QObject>
#include <QThread>
#include <QQueue>
#include <QMutex>
#include <QWaitCondition>
#include <QAtomicInteger>
#include <functional>
#include <exception>

// 拒绝策略枚举
enum class RejectPolicy {
    Abort,      // 直接抛出异常
    Discard,    // 静默丢弃
    CallerRuns  // 由调用者线程执行
};

// 自定义异常类
class ThreadPoolException : public std::exception {
public:
    ThreadPoolException(const char* msg) : message(msg) {}
    const char* what() const noexcept override { return message; }
private:
    const char* message;
};

class ThreadPoolExecutor : public QObject {
    Q_OBJECT
public:
    // 构造函数
    ThreadPoolExecutor(int corePoolSize, int maxPoolSize,
                      int keepAliveTime = 60,
                      RejectPolicy policy = RejectPolicy::Abort);
    // 析构函数
    ~ThreadPoolExecutor();
    // 提交任务
    void execute(std::function<void()> task);
    // 关闭线程池
    void shutdown();
    // 获取已完成任务数
    long getCompletedTaskCount() const;
    // 获取活跃线程数
    int getActiveThreadCount() const;
    // 获取任务队列大小
    int getQueueSize() const;

private:
    // 创建工作线程
    void createThread();
    // 工作线程例程
    void workerRoutine();
    // 处理被拒绝的任务
    void handleRejectedTask(std::function<void()>& task);

private:
    const int corePoolSize;      // 核心线程数
    const int maxPoolSize;       // 最大线程数
    const int keepAliveTime;     // 线程空闲超时时间(秒)
    const RejectPolicy rejectPolicy; // 拒绝策略

    QAtomicInteger<bool> running; // 线程池是否运行
    QAtomicInteger<int> activeThreads; // 活跃线程数
    QAtomicInteger<long> completedTasks; // 已完成任务数

    QList<QThread*> workerThreads; // 工作线程列表
    QQueue<std::function<void()>> taskQueue; // 任务队列

    mutable QMutex mutex;        // 互斥锁
    QWaitCondition condition;    // 条件变量
};

#endif // THREADPOOLEXECUTOR_H
