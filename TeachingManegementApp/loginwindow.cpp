/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "loginwindow.h"
#include "ui_loginwindow.h"


/**
 * @brief LoginWindow::LoginWindow the constructor method for this class
 * @param parent
 */
LoginWindow::LoginWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::LoginWindow) {
    ui->setupUi(this);
    this->setWindowTitle("Teaching Management App Login");
    this->setFixedSize(800,600);
    this->setWindowIcon(QIcon(":/drawable/icon.jpg"));
    this->setStyleSheet("QMainWindow{background:qlineargradient(x1:0,y1:1,x2:0,y2:0,stop: 0 #edf4fe,stop: 0.5 #f6f9ff,stop: 1.0 white)}");
    connect(ui->button_login,&QPushButton::clicked,this,&LoginWindow::openMainWindow);
}

/**
 * @brief LoginWindow::~LoginWindow release resources
 */
LoginWindow::~LoginWindow() {
    delete ui;
}

/**
 * @brief LoginWindow::login to judge whether the user can login
 * @param account obtained from edittext_account
 * @param password obtained from edittext_account
 * @return true is both the account and password is correct,orElse false
 */
bool LoginWindow::login(const QString& account, const QString& password) {
    QString hashedPassword16 = QString::number(qHash(password),16);
    qDebug() << account;
    qDebug() << hashedPassword16;
    if(account == defaultStudentAccount && hashedPassword16 == defaultStudentPasswordHashCode) {
        identityCode = 1;
        return true;
    } else if(account == defaultTeacherAccount && hashedPassword16 == defaultTeacherPasswordHashCode) {
        identityCode = 2;
        return true;
    } else if(account == defaultAdministratorAccount && hashedPassword16 == defaultAdministratorPasswordHashCode) {
        identityCode = 3;
        return true;
    }
    return false;
}

/**
 * @brief LoginWindow::openMainWindow creates an intent to redirect to the MainWindos instance
 */
void LoginWindow::openMainWindow() {
    if(!login(ui->edittext_account->text(),ui->edittext_password->text())) {
        QMessageBox::warning(this,"登录失败","账号密码有误");
        ui->edittext_password->clear();
        ui->edittext_password->setFocus();
        return;
    }
    MainWindow* mainWindow = new MainWindow();
    mainWindow->show();
    this->hide(); // or this->close();
}

