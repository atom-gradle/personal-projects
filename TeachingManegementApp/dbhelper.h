/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef DBHELPER_H
#define DBHELPER_H

#include <type_traits>
#include <memory>

#include <QObject>
#include <QRegularExpression>
#include <QDebug>

#include "arraylist.h"
#include "constants.h"
#include "student.h"
#include "teacher.h"
#include "course.h"
#include "abstractbuilder.h"
#include "director.h"
#include "util.h"

class DBHelper {
public:
    template<typename T>
    static ArrayList<T>& executeSQL(QString SQL, ArrayList<T>& list) {
        if constexpr (is_same_v<T, shared_ptr<Student>>) {
            return executeSQLForStudent(SQL, list);
        }
        else if constexpr (is_same_v<T, shared_ptr<Teacher>>) {
            return executeSQLForTeacher(SQL, list);
        }
        else if constexpr (is_same_v<T, shared_ptr<Course>>) {
            return executeSQLForCourse(SQL, list);
        }
    }

    //using shared_ptr
    static ArrayList<shared_ptr<Student>>& executeSQLForStudent(QString SQL,ArrayList<shared_ptr<Student>>& studentsList);
    static ArrayList<shared_ptr<Teacher>>& executeSQLForTeacher(QString SQL,ArrayList<shared_ptr<Teacher>>& teacherList);
    static ArrayList<shared_ptr<Course>>& executeSQLForCourse(QString SQL,ArrayList<shared_ptr<Course>>& courseList);

private:
    DBHelper();
    static bool checkPermission(const QString& mainAction,const QString& tableName);
    static bool checkValid(QString SQL);
    static ArrayList<QString> splitExpression(QString expression);
    static ArrayList<QString> splitSQLIntoWords(QString SQL);
    static ArrayList<QString> splitValues(QString values);
    static void sortStudentList(ArrayList<shared_ptr<Student>>& list,const QString& byColumn,const bool& isASC);
    static void sortTeacherList(ArrayList<shared_ptr<Teacher>>& list,const QString& byColumn,const bool& isASC);
    static void sortCourseList(ArrayList<shared_ptr<Course>>& list,const QString& byColumn,const bool& isASC);
    template<typename T>
    static void sortList(ArrayList<T>& list,const QString& byColumn,const bool& isASC) {
        if constexpr (is_same_v<T, shared_ptr<Student>>) {
            sortStudentList(list,byColumn,isASC);
        }
        else if constexpr (is_same_v<T, shared_ptr<Teacher>>) {
            sortTeacherList(list,byColumn,isASC);
        }
        else if constexpr (is_same_v<T, shared_ptr<Course>>) {
            sortCourseList(list,byColumn,isASC);
        }
    }
};

#endif // DBHELPER_H
