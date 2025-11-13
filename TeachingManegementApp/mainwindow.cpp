/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "CustomStudentDelegate.cpp"
#include "CustomTeacherDelegate.cpp"
#include "CustomCourseDelegate.cpp"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow),
    pool(new ThreadPoolExecutor(2, 4, 60, RejectPolicy::CallerRuns)) {

//init window
    ui->setupUi(this);
    this->setWindowTitle("Teaching Management App");
    this->setFixedWidth(2000);
    this->setFixedHeight(1200);
    this->setWindowIcon(QIcon(":/drawable/icon.jpg"));
    this->setStyleSheet("QMainWindow{background:white}");

//init welcome label
    if(identityCode == 1) {
        ui->label_welcome->setText("欢迎！学生"+defaultStudentAccount);
    } else if(identityCode == 2) {
        ui->label_welcome->setText("欢迎！教师"+defaultTeacherAccount);
    } else if(identityCode == 3) {
        ui->label_welcome->setText("欢迎！管理员"+defaultAdministratorAccount);
    }

//init statusBar
    labelLeft = new QLabel("就绪",this);
    labelRight = new QLabel("UTF-8",this);
    labelLeft->setStyleSheet("QLabel{color:black;font:24px 'Microsoft YaHei UI Light';padding:2px}");
    labelRight->setStyleSheet("QLabel{color:black;font:24px 'Microsoft YaHei UI Light';padding:2px}");
    ui->statusbar->addWidget(labelLeft);
    ui->statusbar->addPermanentWidget(labelRight);
    this->setStatusBar(ui->statusbar);

//init tabWidget
    ui->tabWidget->setContextMenuPolicy(Qt::CustomContextMenu);
    ui->treeWidget_student->hide();
    ui->treeWidget_course->hide();
    ui->tabWidget->setCurrentIndex(0);

//init listviews
    //init student listview
    ui->listWidget_main->setItemDelegate(new CustomStudentDelegate());
    ui->listWidget_main->setAlternatingRowColors(true);
    //init teacher listview
    ui->listWidget_secondary->setItemDelegate(new CustomTeacherDelegate());
    ui->listWidget_secondary->setAlternatingRowColors(true);
    //init course listview
    ui->listWidget_tertiary->setItemDelegate(new CustomCourseDelegate());
    ui->listWidget_tertiary->setAlternatingRowColors(true);

//connect slots/onClickListeners
    connect(ui->listWidget_main, &QListWidget::itemClicked,
            this, &MainWindow::onListWidgetMainItemClicked);
    connect(ui->listWidget_secondary, &QListWidget::itemClicked,
            this, &MainWindow::onListWidgetSecondaryItemClicked);
    connect(ui->listWidget_tertiary, &QListWidget::itemClicked,
            this, &MainWindow::onListWidgetTertiaryItemClicked);
    connect(ui->button_execute,&QPushButton::clicked,[=]() {
        QString SQL = ui->edittext_SQL_input->toPlainText();
        if(SQL.size() < 5) {
            QMessageBox::information(this,"输入错误","请输入合法的SQL语句");
            return;
        }
        int whichPage = ui->tabWidget->currentIndex();
        qDebug() << "currentPageIndex " << whichPage;
        if(whichPage == 0) {
            updateStudentForm(SQL);
        } else if(whichPage == 1) {
            updateTeacherForm(SQL);
        } else if(whichPage == 2) {
            updateCourseForm(SQL);
        }
    });
    //menu_file
    connect(ui->menuItem_new,&QAction::triggered,[=]() {
        //TODO
    });
    connect(ui->menuItem_open,&QAction::triggered,[=]() {
        QString filePath = QFileDialog::getOpenFileName(nullptr, "选择文件", "", fileChooserFilter);
        if (filePath.isEmpty()) return;
        int whichPage = ui->tabWidget->currentIndex();
        if(whichPage == 0) {
            mStudentList = Util::fetchStudentDataFromNative(filePath);
            updateStudentForm("");
        } else if(whichPage == 1) {
            mTeacherList = Util::fetchTeacherDataFromNative(filePath);
            updateTeacherForm("");
        } else if(whichPage == 2) {
            mCourseList = Util::fetchCourseDataFromNative(filePath);
            updateCourseForm("");
        }
    });
    connect(ui->menuItem_save,&QAction::triggered,[=]() {
        int whichPage = ui->tabWidget->currentIndex();
        bool saved = false;
        if(whichPage == 0) {
            saved = Util::saveDataToNative("",mStudentList);
        } else if(whichPage == 1) {
            saved = Util::saveDataToNative("",mTeacherList);
        } else if(whichPage == 2) {
            saved = Util::saveDataToNative("",mCourseList);
        }
        if(saved) {
            qDebug() << "保存成功";
        } else {
            qDebug() << "保存失败";
        }
    });
    connect(ui->menuItem_save_as,&QAction::triggered,[=]() {
        QString fileName = QFileDialog::getSaveFileName(nullptr, "另存为", "", fileChooserFilter);

    });
    connect(ui->menuItem_export,&QAction::triggered,[=]() {
        QString fileName = QFileDialog::getSaveFileName(nullptr, "导出为", "", exportFileChooserFilter);

    });
    connect(ui->menuItem_print,&QAction::triggered,[=]() {
        QString filePath = QFileDialog::getOpenFileName(nullptr, "选择文件", "", fileChooserFilter);
    });
    connect(ui->menuItem_exit,&QAction::triggered,[=]() {
        this->close();
    });
    //menu_view
    connect(ui->menuItem_list_view,&QAction::triggered,[=]() {
        int whichPage = ui->tabWidget->currentIndex();
        if(whichPage == 0) {
            ui->listWidget_main->show();
            ui->treeWidget_student->hide();
        } else if(whichPage == 2) {
            ui->listWidget_tertiary->show();
            ui->treeWidget_course->hide();
        }
    });
    connect(ui->menuItem_tree_view,&QAction::triggered,[=]() {
        int whichPage = ui->tabWidget->currentIndex();
        if(whichPage == 0) {
            ui->listWidget_main->hide();
            ui->treeWidget_student->show();
        } else if(whichPage == 2) {
            ui->listWidget_tertiary->hide();
            ui->treeWidget_course->show();
            updateCourseTree();
        }
    });
    connect(ui->menuItem_table_view,&QAction::triggered,[=]() {

    });

    //menu_help
    connect(ui->menuItem_help,&QAction::triggered,[=]() {
        QDesktopServices::openUrl(QUrl(url_QT_official));
    });
    connect(ui->menuItem_about,&QAction::triggered,[=]() {
        QMessageBox::information(this,aboutDialogTitle,aboutDialogText);
    });

}

/**
 * @brief MainWindow::updateStudentForm
 * @param SQL the SQL instruction
 * @param studentsList the list containing students to update
 */
void MainWindow::updateStudentForm(const QString& SQL) {
    qDebug() << "update Student Form!";
    ArrayList<shared_ptr<Student>> list = DBHelper::executeSQL(SQL,mStudentList);
    qDebug() << mStudentList.size();
    ui->listWidget_main->setUpdatesEnabled(false);
    ui->listWidget_main->clear();
//set column
    QListWidgetItem* column = new QListWidgetItem();
    column->setData(Qt::UserRole + 1, "Id");
    column->setData(Qt::UserRole + 2, "Name");
    column->setData(Qt::UserRole + 3, "Age");
    column->setData(Qt::UserRole + 4, "Credit");
    ui->listWidget_main->addItem(column);
//set data
    int size = (list.size() == 0 ? mStudentList.size() : list.size());
    labelLeft->setText("查询到"+QString::number(size)+"条数据");
    if(list.size() == 0) {
        for(int i = 0;i < size;i++) {
            shared_ptr<Student> student = mStudentList.get(i);
            QListWidgetItem *item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, student->getId());
            item->setData(Qt::UserRole + 2, student->getName());
            item->setData(Qt::UserRole + 3, QString::number(student->getAge()));
            item->setData(Qt::UserRole + 4, QString::number(student->getCredit()));
            ui->listWidget_main->addItem(item);
        }
    } else {
        for(int i = 0;i < size;i++) {
            shared_ptr<Student> student = list.get(i);
            QListWidgetItem *item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, student->getId());
            item->setData(Qt::UserRole + 2, student->getName());
            item->setData(Qt::UserRole + 3, QString::number(student->getAge()));
            item->setData(Qt::UserRole + 4, QString::number(student->getCredit()));
            ui->listWidget_main->addItem(item);
        }
    }
    ui->listWidget_main->setUpdatesEnabled(true);
}

/**
 * @brief MainWindow::updateTeacherForm
 * @param SQL the SQL instruction
 * @param teacherList the list containing teachers to update
 */
void MainWindow::updateTeacherForm(const QString& SQL) {
    qDebug() << "update Teacher Form!";
    ArrayList<shared_ptr<Teacher>> list = DBHelper::executeSQL(SQL,mTeacherList);
    qDebug() << "查询返回的list.size():" << list.size();
    ui->listWidget_secondary->setUpdatesEnabled(false);
    ui->listWidget_secondary->clear();
//set column
    QListWidgetItem* column = new QListWidgetItem();
    column->setData(Qt::UserRole + 1, "Id");
    column->setData(Qt::UserRole + 2, "Name");
    column->setData(Qt::UserRole + 3, "Age");
    ui->listWidget_secondary->addItem(column);
//set data
    int size = (list.size() == 0 ? mTeacherList.size() : list.size());
    labelLeft->setText("查询到"+QString::number(size)+"条数据");
    if(list.size() == 0) {
        for(int i = 0;i < size;i++) {
            shared_ptr<Teacher> teacher = mTeacherList.get(i);
            QListWidgetItem* item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, teacher->getId());
            item->setData(Qt::UserRole + 2, teacher->getName());
            item->setData(Qt::UserRole + 3, QString::number(teacher->getAge()));
            ui->listWidget_secondary->addItem(item);
        }
    } else {
        for(int i = 0;i < size;i++) {
            shared_ptr<Teacher> teacher = list.get(i);
            QListWidgetItem* item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, teacher->getId());
            item->setData(Qt::UserRole + 2, teacher->getName());
            item->setData(Qt::UserRole + 3, QString::number(teacher->getAge()));
            ui->listWidget_secondary->addItem(item);
        }
    }
    ui->listWidget_secondary->setUpdatesEnabled(true);
}

/**
 * @brief MainWindow::updateCourseForm
 * @param SQL the SQL instruction
 * @param courseList the list containing courses to update
 */
void MainWindow::updateCourseForm(const QString& SQL) {
    qDebug() << "update Course Form!";
    ArrayList<shared_ptr<Course>> list = DBHelper::executeSQL(SQL,mCourseList);
    qDebug() << "查询返回的list.size():" << list.size();
    ui->listWidget_tertiary->setUpdatesEnabled(false);
    ui->listWidget_tertiary->clear();
//set column
    QListWidgetItem* column = new QListWidgetItem();
    column->setData(Qt::UserRole + 1, "Id");
    column->setData(Qt::UserRole + 2, "Name");
    column->setData(Qt::UserRole + 3, "Credit");
    column->setData(Qt::UserRole + 4, "Teacher");
    column->setData(Qt::UserRole + 5, "CourseTime");
    ui->listWidget_tertiary->addItem(column);
//set data
    int size = (list.size() == 0 ? mCourseList.size() : list.size());
    labelLeft->setText("查询到"+QString::number(size)+"条数据");
    if(list.size() == 0) {
        for(int i = 0;i < size;i++) {
            shared_ptr<Course> course = mCourseList.get(i);
            QListWidgetItem* item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, course->getId());
            item->setData(Qt::UserRole + 2, course->getName());
            item->setData(Qt::UserRole + 3, QString::number(course->getCredit()));
            item->setData(Qt::UserRole + 4, course->getTeacherId());
            item->setData(Qt::UserRole + 5, course->getCourseTime());
            ui->listWidget_tertiary->addItem(item);
        }
    } else {
        for(int i = 0;i < size;i++) {
            shared_ptr<Course> course = list.get(i);
            QListWidgetItem* item = new QListWidgetItem();
            item->setData(Qt::UserRole, i+1);
            item->setData(Qt::UserRole + 1, course->getId());
            item->setData(Qt::UserRole + 2, course->getName());
            item->setData(Qt::UserRole + 3, QString::number(course->getCredit()));
            item->setData(Qt::UserRole + 4, course->getTeacherId());
            item->setData(Qt::UserRole + 5, course->getCourseTime());
            ui->listWidget_tertiary->addItem(item);
        }
    }
    ui->listWidget_tertiary->setUpdatesEnabled(true);
}

/**
 * @brief MainWindow::updateCourseTree
 */
void MainWindow::updateCourseTree() {
    ui->treeWidget_course->setColumnCount(2);
    ui->treeWidget_course->setHeaderLabels({"课程", "先修课程"});
    // 添加顶级项目
    QTreeWidgetItem *root1 = new QTreeWidgetItem(ui->treeWidget_course);
    root1->setText(0, "数据库");
    root1->setText(1, "");
    // 添加子项目
    QTreeWidgetItem *child1 = new QTreeWidgetItem(root1);
    child1->setText(0, "");
    child1->setText(1, "数据结构");

    QTreeWidgetItem *child2 = new QTreeWidgetItem(root1);
    child2->setText(0, "");
    child2->setText(1, "算法");

    // 另一个顶级项目
    QTreeWidgetItem *root2 = new QTreeWidgetItem(ui->treeWidget_course);
    root2->setText(0, "Java Programming");
    root2->setText(1, "");
    // 添加子项目
    QTreeWidgetItem *child3 = new QTreeWidgetItem(root2);
    child3->setText(0, "");
    child3->setText(1, "c/c++ Programming");

    QTreeWidgetItem *child4 = new QTreeWidgetItem(root2);
    child4->setText(0, "");
    child4->setText(1, "Python Programming");

    ui->treeWidget_course->expandAll(); // 展开所有项目
    ui->treeWidget_course->show();
}

/**
 * @brief MainWindow::onListWidgetMainItemClicked
 * @param item
 */
void MainWindow::onListWidgetMainItemClicked(QListWidgetItem* item) {
    if (item) {
        int index = item->data(Qt::UserRole).toInt();
        labelLeft->setText("已选中1个学生数据，可在右侧查看当前选中的学生信息");
        if(index == 0) {
            clearAllLabels();
            labelLeft->setText("就绪");
        } else {
            ui->label_id->setText("Id");
            ui->label_name->setText("Name");
            ui->label_showId->setText(item->data(Qt::UserRole+1).toString());
            ui->label_showName->setText(item->data(Qt::UserRole+2).toString());
            ui->label_extraInfo1->setText("Age");
            ui->label_showExtraInfo1->setText(item->data(Qt::UserRole+3).toString());
            ui->label_extraInfo2->show();
            ui->label_showExtraInfo2->show();
            ui->label_extraInfo2->setText("Credit");
            ui->label_showExtraInfo2->setText(item->data(Qt::UserRole+4).toString());
        }
    }
}

/**
 * @brief MainWindow::onListWidgetSecondaryItemClicked
 * @param item
 */
void MainWindow::onListWidgetSecondaryItemClicked(QListWidgetItem* item) {
    if (item) {
        int index = item->data(Qt::UserRole).toInt();
        labelLeft->setText("已选中1个教师数据，可在右侧查看当前选中的教师信息");
        if(index == 0) {
            clearAllLabels();
            labelLeft->setText("就绪");
        } else {
            ui->label_id->setText("Id");
            ui->label_name->setText("Name");
            ui->label_showId->setText(item->data(Qt::UserRole+1).toString());
            ui->label_showName->setText(item->data(Qt::UserRole+2).toString());
            ui->label_extraInfo1->setText("Age");
            ui->label_showExtraInfo1->setText(item->data(Qt::UserRole+3).toString());
            ui->label_extraInfo2->hide();
            ui->label_showExtraInfo2->hide();
        }
    }
}

/**
 * @brief MainWindow::onListWidgetTertiaryItemClicked
 * @param item
 */
void MainWindow::onListWidgetTertiaryItemClicked(QListWidgetItem* item) {
    if (item) {
        int index = item->data(Qt::UserRole).toInt();
        labelLeft->setText("已选中1个课程数据，可在右侧查看当前选中的课程信息");
        if(index == 0) {
            clearAllLabels();
            labelLeft->setText("就绪");
        } else {
            ui->label_id->setText("Id");
            ui->label_name->setText("Name");
            ui->label_showId->setText(item->data(Qt::UserRole+1).toString());
            ui->label_showName->setText(item->data(Qt::UserRole+2).toString());
            ui->label_extraInfo1->setText("Credit");
            ui->label_showExtraInfo1->setText(item->data(Qt::UserRole+3).toString());
            ui->label_extraInfo2->show();
            ui->label_showExtraInfo2->show();
            ui->label_extraInfo2->setText("CourseTime");
            ui->label_showExtraInfo2->setText(item->data(Qt::UserRole+5).toString());
        }
    }
}

/**
 * @brief MainWindow::clearAllLabels equivalent to invoking hide() method for each label
 */
void MainWindow::clearAllLabels() {
    ui->label_id->setText("");
    ui->label_name->setText("");
    ui->label_extraInfo1->setText("");
    ui->label_extraInfo2->setText("");
    ui->label_showId->setText("");
    ui->label_showName->setText("");
    ui->label_showExtraInfo1->setText("");
    ui->label_showExtraInfo2->setText("");
}

/**
 * @brief MainWindow::~MainWindow releases resources
 */
MainWindow::~MainWindow() {
    delete pool;
    delete ui;
}
