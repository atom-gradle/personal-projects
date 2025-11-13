/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QDebug>
#include <QFocusEvent>
#include <QFile>
#include <QFileDialog>
#include <QLabel>
#include <QListWidget>
#include <QListWidgetItem>
#include <QMainWindow>
#include <QMessageBox>
#include <QMouseEvent>
#include <QtPrintSupport/QPrinter>
#include <QtPrintSupport/QPrintDialog>
#include <QPainter>
#include <QString>
#include <QDesktopServices>
#include <QTabWidget>
#include <QUrl>

#include "arraylist.h"
#include "util.h"
#include "dbhelper.h"
#include "threadpoolexecutor.h"
#include "color.h"

QT_BEGIN_NAMESPACE
namespace Ui {
    class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow {
    Q_OBJECT
public:
    explicit MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    QString url_QT_official = "https://www.qt.io";
    QString aboutDialogTitle = "关于 Teaching Management App";
    QString aboutDialogText = "Developed for Data Structure\nAuthor:";
    QString fileChooserFilter = "XML文件 (*.xml);;CSV文件 (*.csv);;文本文件 (*.txt)";
    QString exportFileChooserFilter = "Excel表 (*.xlsx);;PNG文件 (*.png);;JPG文件 (*.jpg)";
    ThreadPoolExecutor* pool;

    QLabel* labelLeft = nullptr;
    QLabel* labelRight = nullptr;

    QString mStudentDataFilePath = "";
    QString mTeacherDataFilePath = "";
    QString mCourseDataFilePath = "";
    ArrayList<shared_ptr<Student>> mStudentList;
    ArrayList<shared_ptr<Teacher>> mTeacherList;
    ArrayList<shared_ptr<Course>> mCourseList;

    //for listViews
    void updateStudentForm(const QString& SQL);
    void updateTeacherForm(const QString& SQL);
    void updateCourseForm(const QString& SQL);

    //for treeViews
    void updateCourseTree();
    void clearAllLabels();

private slots:
    void onListWidgetMainItemClicked(QListWidgetItem *item);
    void onListWidgetSecondaryItemClicked(QListWidgetItem *item);
    void onListWidgetTertiaryItemClicked(QListWidgetItem *item);
};

#endif // MAINWINDOW_H
