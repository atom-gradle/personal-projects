/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef LOGINWINDOW_H
#define LOGINWINDOW_H

#include <QGraphicsBlurEffect>
#include <QMainWindow>
#include <QString>
#include "mainwindow.h"
#include "constants.h"

QT_BEGIN_NAMESPACE
namespace Ui {class LoginWindow;}
QT_END_NAMESPACE

class LoginWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit LoginWindow(QWidget *parent = nullptr);
    ~LoginWindow();

private:
    Ui::LoginWindow *ui;
    bool login(const QString& account,const QString& password);

private slots:
    void openMainWindow();
};

#endif // LOGINWINDOW_H
