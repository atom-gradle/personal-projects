/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef UTIL_H
#define UTIL_H

#include <QCoreApplication>
#include <QDateTime>
#include <QDebug>
#include <QFile>
#include <QString>
#include <QtPrintSupport/QPrinter>
#include <QtPrintSupport/QPrintDialog>
#include <QPainter>
#include <QTextStream>
#include <QXmlStreamReader>

#include "student.h"
#include "teacher.h"
#include "course.h"
#include "arraylist.h"
#include "hashmap.h"

class Util {
public:
    Util();

    static ArrayList<shared_ptr<Student>> fetchStudentDataFromNative(QString filePath);
    static ArrayList<shared_ptr<Teacher>> fetchTeacherDataFromNative(QString filePath);
    static ArrayList<shared_ptr<Course>> fetchCourseDataFromNative(QString filePath);

    template<typename T>
    static bool saveDataToNative(QString SQL, ArrayList<T> list) {
        if constexpr (is_same_v<T, shared_ptr<Student>>) {
            return saveStudentDataToNative(SQL, list);
        }
        else if constexpr (is_same_v<T, shared_ptr<Teacher>>) {
            return saveTeacherDataToNative(SQL, list);
        }
        else if constexpr (is_same_v<T, shared_ptr<Course>>) {
            return saveCourseDataToNative(SQL, list);
        }
    }

private:
    static bool saveStudentDataToNative(QString filePath,ArrayList<shared_ptr<Student>>);
    static bool saveTeacherDataToNative(QString filePath,ArrayList<shared_ptr<Teacher>>);
    static bool saveCourseDataToNative(QString filePath,ArrayList<shared_ptr<Course>>);

    //Key-Id,Value-prerequisiteCourseList
    static HashMap<QString,ArrayList<shared_ptr<QString>>> getPrerequisiteCourseMap(const QString& filePath);
    //Key-Id,Value-course instance
    static HashMap<QString,shared_ptr<Course>> getCourseMap(const QString& filePath);



    static void readXmlFile(const QString &filePath);
    void writeXmlFile(const QString &filePath);
    static void printXMLFile(const QString& filePath);

    static QString generateDateTimeFilePath(const QString& who,const QString& fileExt);

};

#endif // UTIL_H
