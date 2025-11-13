/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include <QApplication>
#include <QMainWindow>
#include <QSystemTrayIcon>
#include <QMenu>
#include <QAction>

#include "color.h"
#include "loginwindow.h"

/**
 * @brief qMain the entry for the application
 * @param argc
 * @param argv
 * @return QApplication a.messageLoop()
 * @author Qian
 * @date 2025
 * @version 3.0.0
 */

int main(int argc, char *argv[]) {
    QApplication app(argc, argv);
    LoginWindow w;
    app.setStyleSheet("QAction{background-color:"+menuItem+"}"
                    "QAction:hover{color:black;background-color:black}"
                    "QDockWidget{background-color:"+menuBar+";}"
                    "QMenuBar{background-color:"+menuBar+"}"
                    "QMenu{background-color:"+menuItem+"}"
                    "QPlainTextEdit{color:white;background-color:"+mainCodeBackground+";border-radius:8px;border:2px solid black}"
                    "QPushButton{color:#FFF;background-color:"+button+";border-radius:8px;min-width:50px;min-height:28px;padding:5px;}"
                    "QPushButton:hover{color:#FFF;background-color:"+button+";border-radius:8px;border:2px solid black}"
                    "QLabel{color:#000000}"
                    "QLineEdit{border:2px solid "+button+";border-radius:8px}"

                    "QListWidget {background-color:white;color:black;padding:10px;margin:5px;border:2px solid black;border-radius:8px}"
                    "QListWidget::item:alternate{background-color:#e7f2ec;}"
                    "QListWidget::item:hover{background-color:#b7d8c6;}"
                    "QListWidget::item:selected{background-color:#6fb08e;color: white;}"
                    "QStatusBar{background:"+chrome_blue+";border-radius:5px;margin:2px;border:1px solid white;}"
                    "QTabWidget::pane{border:1px solid white;top: -1px;background: white;}"
                    "QTabWidget::tab-bar{left:10px;}"
                    "QTabBar::tab{background:qlineargradient(x1:0,y1:1,x2:0,y2:0,stop: 0 #0f7c42,stop: 0.2 white);margin-left:2px;margin-right:2px;"
                    "border:3px solid white;border-bottom-color:white;border-top-left-radius:5px;border-top-right-radius:5px;min-width:8ex;padding:2px 5px 2px 5px;}"
                    "QTabBar::tab:selected{font-weight:bold;background:qlineargradient(x1:0,y1:1,x2:0,y2:0,stop: 0 #0f7c42,stop: 0.5 white);}"
            );
    app.setApplicationName("Teaching");

    //create SystemTrayIcon
    QSystemTrayIcon trayIcon;
    trayIcon.setIcon(QIcon(":/drawable/icon.jpg"));
    QMenu* trayMenu = new QMenu();
    QAction* action_exit = new QAction("退出",trayMenu);
    QObject::connect(action_exit, &QAction::triggered, [=]() {
        QApplication::quit();
    });
    trayMenu->addAction(action_exit);
    trayIcon.setContextMenu(trayMenu);
    trayIcon.show();

    w.show();

    return app.exec();
}
