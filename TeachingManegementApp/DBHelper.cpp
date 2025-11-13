/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "dbhelper.h"

/**
 * @brief DBHelper::DBHelper This class defines tool methods used for data parsing and SQL query
 */
DBHelper::DBHelper() {}

/**
 * @brief DBHelper::executeSQLForStudent parses and executes query index for Student Table
 * @param SQL the SQL query index
 * @param studentList the source data
 * @return a list which is not empty only when the mainAction is SELECT
 */
ArrayList<shared_ptr<Student>>& DBHelper::executeSQLForStudent(QString SQL, ArrayList<shared_ptr<Student>>& studentList) {
    bool matched = checkValid(SQL);
    qDebug() << matched;
    if(!matched) {
        return studentList;
    }
    ArrayList<QString> splitedSQLList = splitSQLIntoWords(SQL);
    QString mainAction = splitedSQLList.get(0);
    if(!checkPermission(mainAction,"Student")) {
        return studentList;
    }
    ArrayList<shared_ptr<Student>>& resultList = studentList;
    if(mainAction == "SELECT") {
        if(splitedSQLList.size() == 4) {
            return studentList;
        } else if(splitedSQLList.size() == 8) {
            QString byColumn = splitedSQLList.get(6);
            bool isASC = splitedSQLList.get(7) == "ASC" ? true : false;
            sortStudentList(studentList,byColumn,isASC);
            return studentList;
        }
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = 0;i < studentList.size();i++) {
            shared_ptr<Student> student = studentList.get(i);
            if(student->get(leftExp) == rightExp) {
                resultList.add(student);
            }
        }
        if(splitedSQLList.size() > 6) {
            QString byColumn = splitedSQLList.get(8);
            bool isASC = splitedSQLList.get(9) == "ASC" ? true : false;
            sortStudentList(studentList,byColumn,isASC);
        }
    } else if(mainAction == "UPDATE") {
        QString expressionOld = splitedSQLList.get(5);
        QString expressionNew = splitedSQLList.get(3);
        ArrayList<QString> expressionOldList = splitExpression(expressionOld);
        ArrayList<QString> expressionNewList = splitExpression(expressionNew);
        QString leftExpOld = expressionOldList.get(0);
        QString rightExpOld = expressionOldList.get(1);
        QString leftExpNew = expressionNewList.get(0);
        QString rightExpNew = expressionNewList.get(1);
        for(int i = 0;i < studentList.size();i++) {
            shared_ptr<Student> student = studentList.get(i);
            if(student->get(leftExpOld) == rightExpOld) {
                student->set(leftExpNew,rightExpNew);
            }
        }
    } else if(mainAction == "INSERT") {
        ArrayList<QString> columnsToInsert = splitValues(splitedSQLList.get(3));
        ArrayList<QString> valuesToInsert = splitValues(splitedSQLList.get(5));
        shared_ptr<Student> student = make_shared<Student>(valuesToInsert.get(0),valuesToInsert.get(1),valuesToInsert.get(2).toInt(),valuesToInsert.get(3).toFloat());
        studentList.add(student);
    } else if(mainAction == "DELETE") {
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = studentList.size()-1;i >= 0;i--) {
            shared_ptr<Student> student = studentList.get(i);
            if(student->get(leftExp) == rightExp) {
                studentList.remove(i);
            }
        }
    }
    return resultList;
}

/**
 * @brief DBHelper::executeSQLForTeacher parses and executes query index for Teacher Table
 * @param SQL the SQL query index
 * @param teacherList the source data
 * @return a list which is not empty only when the mainAction is SELECT
 */
ArrayList<shared_ptr<Teacher>>& DBHelper::executeSQLForTeacher(QString SQL, ArrayList<shared_ptr<Teacher>>& teacherList) {
    bool matched = checkValid(SQL);
    qDebug() << matched;
    if(!matched) {
        return teacherList;
    }
    ArrayList<QString> splitedSQLList = splitSQLIntoWords(SQL);
    QString mainAction = splitedSQLList.get(0);
    if(!checkPermission(mainAction,"Teacher")) {
        return teacherList;
    }
    ArrayList<shared_ptr<Teacher>>& resultList = teacherList;
    if(mainAction == "SELECT") {
        if(splitedSQLList.size() == 4) {
            return teacherList;
        } else if(splitedSQLList.size() == 8) {
            QString byColumn = splitedSQLList.get(6);
            bool isASC = splitedSQLList.get(7) == "ASC" ? true : false;
            sortTeacherList(teacherList,byColumn,isASC);
            return teacherList;
        }
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = 0;i < teacherList.size();i++) {
            shared_ptr<Teacher> teacher = teacherList.get(i);
            if(teacher->get(leftExp) == rightExp) {
                resultList.add(teacher);
            }
        }
        if(splitedSQLList.size() > 6) {
            QString byColumn = splitedSQLList.get(8);
            bool isASC = splitedSQLList.get(9) == "ASC" ? true : false;
            sortTeacherList(teacherList,byColumn,isASC);
        }
    } else if(mainAction == "UPDATE") {
        QString expressionOld = splitedSQLList.get(5);
        QString expressionNew = splitedSQLList.get(3);
        ArrayList<QString> expressionOldList = splitExpression(expressionOld);
        ArrayList<QString> expressionNewList = splitExpression(expressionNew);
        QString leftExpOld = expressionOldList.get(0);
        QString rightExpOld = expressionOldList.get(1);
        QString leftExpNew = expressionNewList.get(0);
        QString rightExpNew = expressionNewList.get(1);
        for(int i = 0;i < teacherList.size();i++) {
            shared_ptr<Teacher> teacher = teacherList.get(i);
            if(teacher->get(leftExpOld) == rightExpOld) {
                teacher->set(leftExpNew,rightExpNew);
            }
        }
    } else if(mainAction == "INSERT") {
        ArrayList<QString> columnsToInsert = splitValues(splitedSQLList.get(3));
        ArrayList<QString> valuesToInsert = splitValues(splitedSQLList.get(5));
        shared_ptr<Teacher> teacher = make_shared<Teacher>(valuesToInsert.get(0),valuesToInsert.get(1),valuesToInsert.get(2).toInt());
        teacherList.add(teacher);
    } else if(mainAction == "DELETE") {
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = teacherList.size()-1;i >= 0;i--) {
            shared_ptr<Teacher> teacher = teacherList.get(i);
            if(teacher->get(leftExp) == rightExp) {
                teacherList.remove(i);
            }
        }
    }
    return resultList;
}

/**
 * @brief DBHelper::executeSQLForCourse parses and executes query index for Course Table
 * @param SQL the SQL query index
 * @param courseList the source data
 * @return a list which is not empty only when the mainAction is SELECT
 */
ArrayList<shared_ptr<Course>>& DBHelper::executeSQLForCourse(QString SQL, ArrayList<shared_ptr<Course>>& courseList) {
    bool matched = checkValid(SQL);
    qDebug() << matched;
    if(!matched) {
        return courseList;
    }
    ArrayList<QString> splitedSQLList = splitSQLIntoWords(SQL);
    QString mainAction = splitedSQLList.get(0);
    if(!checkPermission(mainAction,"Course")) {
        return courseList;
    }
    ArrayList<shared_ptr<Course>>& resultList = courseList;
    if(mainAction == "SELECT") {
        if(splitedSQLList.size() == 4) {
            return courseList;
        } else if(splitedSQLList.size() == 8) {
            QString byColumn = splitedSQLList.get(6);
            bool isASC = splitedSQLList.get(7) == "ASC" ? true : false;
            sortCourseList(courseList,byColumn,isASC);
            return courseList;
        }
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = 0;i < courseList.size();i++) {
            shared_ptr<Course> course = courseList.get(i);
            if(course->get(leftExp) == rightExp) {
                resultList.add(course);
            }
        }
        if(splitedSQLList.size() > 6) {
            QString byColumn = splitedSQLList.get(8);
            bool isASC = splitedSQLList.get(9) == "ASC" ? true : false;
            sortCourseList(courseList,byColumn,isASC);
        }
    } else if(mainAction == "UPDATE") {
        QString expressionOld = splitedSQLList.get(5);
        QString expressionNew = splitedSQLList.get(3);
        ArrayList<QString> expressionOldList = splitExpression(expressionOld);
        ArrayList<QString> expressionNewList = splitExpression(expressionNew);
        QString leftExpOld = expressionOldList.get(0);
        QString rightExpOld = expressionOldList.get(1);
        QString leftExpNew = expressionNewList.get(0);
        QString rightExpNew = expressionNewList.get(1);
        for(int i = 0;i < courseList.size();i++) {
            shared_ptr<Course> course = courseList.get(i);
            if(course->get(leftExpOld) == rightExpOld) {
                course->set(leftExpNew,rightExpNew);
            }
        }
    } else if(mainAction == "INSERT") {
        ArrayList<QString> columnsToInsert = splitValues(splitedSQLList.get(3));
        ArrayList<QString> valuesToInsert = splitValues(splitedSQLList.get(5));
        shared_ptr<Course> course = make_shared<Course>(valuesToInsert.get(0),valuesToInsert.get(1),valuesToInsert.get(2).toFloat(),valuesToInsert.get(3),valuesToInsert.get(4));
        courseList.add(course);
    } else if(mainAction == "DELETE") {
        QString expression = splitedSQLList.get(5);
        ArrayList<QString> expressionList = splitExpression(expression);
        QString leftExp = expressionList.get(0);
        QString rightExp = expressionList.get(1);
        for(int i = courseList.size()-1;i >= 0;i--) {
            shared_ptr<Course> course = courseList.get(i);
            if(course->get(leftExp) == rightExp) {
                courseList.remove(i);
            }
        }
    }
    return resultList;
}
/*
 * So far,we have made these four SQL orders available
 * 1.SELECT * FROM [tablename] WHERE [expression] ORDER BY [column] ASC/DESC
 *   SELECT * FROM [tablename]
 *   SELECT * FROM [tablename] ORDER By [column] ASC/DESC
 *   SELECT * FROM [tablename] WHERE [expression]
 *   SELECT * FROM [tablename] WHERE [expression] ORDER BY [column] ASC/DESC
 * 2.UPDATE [tablename] SET [columnn=value] WHERE [expression]
 * 3.INSERT INTO [tablename] (column1,column2,column3,...) VALUES (arg1,arg2,arg3,...)
 * 4.DELETE * FROM [tablename] WHERE [expression]
 * 5.ALTER TABLE [tablename] ADD INDEX [idx_username] [column]
 *
 * for Expressions,we have made these available:
 * 1. > < =
 */


/**
 * @brief DBHelper::checkValid check if the query index is valid
 * @param SQL the source SQL
 * @return true if the SQL is valid,otherwise,return false
 */
bool DBHelper::checkValid(QString SQL) {
    if(SQL == "") {
        return false;
    }
    SQL = SQL.toUpper();
    QRegularExpression selectPattern("SELECT \\* FROM \\w+( WHERE .+)?( ORDER BY ASC|DESC)?");
    QRegularExpression updatePattern("UPDATE \\w+ SET \\w+\\s*=\\s*.+( WHERE .+)?");
    QRegularExpression insertPattern("INSERT INTO \\w+ \\(\\w+(,\\w+)*\\) VALUES \\(.+(,.+)*\\)");
    QRegularExpression deletePattern("DELETE \\* FROM \\w+( WHERE .+)?");
    QRegularExpression alterPattern("ALTER TABLE \\w+ ADD INDEX \\w+ \\w+");

    return selectPattern.match(SQL).hasMatch() ||
           updatePattern.match(SQL).hasMatch() ||
           insertPattern.match(SQL).hasMatch() ||
           deletePattern.match(SQL).hasMatch() ||
           alterPattern.match(SQL).hasMatch();
}

/**
 * @brief DBHelper::splitExpression
 * @param expression the expression which needs to be splited
 * @return a list containing splited parts of the expression
 */
ArrayList<QString> DBHelper::splitExpression(QString expression) {
    ArrayList<QString> list;
    int andPos = expression.indexOf("and");
    int orPos = expression.indexOf("or");
    int equalsPos = expression.indexOf("=");
    QString leftExp = expression.mid(0,equalsPos);
    QString rightExp = expression.mid(equalsPos+1);
    list.add(leftExp);
    list.add(rightExp);
    return list;
}

ArrayList<QString> DBHelper::splitSQLIntoWords(QString SQL) {
    ArrayList<QString> list;
    QString splitArray[SQL.length()];
    QRegularExpression reg("(\\S+)");
    QRegularExpressionMatchIterator iter = reg.globalMatch(SQL);
    while(iter.hasNext()) {
        QRegularExpressionMatch match = iter.next();
        list.add(match.captured(0));
    }
    return list;
}

ArrayList<QString> DBHelper::splitValues(QString values) {
    ArrayList<QString> resultList;
    QString pureValues = values.mid(1,values.size()-2);
    qDebug() << "pureValues are" << pureValues;
    QStringList parts = pureValues.split(",");
    for(int i= 0;i < parts.size();i++) {
        resultList.add(parts[i]);
    }
    return resultList;
}

void DBHelper::sortStudentList(ArrayList<shared_ptr<Student>>& list, const QString& byColumn, const bool& isASC) {
    list.sort([&](shared_ptr<Student>& a, shared_ptr<Student>& b) {
        // 处理空指针
        if (!a || !b) return !a && b;

        // 统一获取比较值
        QVariant valA, valB;
        if (byColumn == "Id" || byColumn == "Name") {
            valA = a->get(byColumn);
            valB = b->get(byColumn);
        } else if (byColumn == "Age") {
            valA = a->getAge();
            valB = b->getAge();
        } else if (byColumn == "Credit") {
            valA = a->getCredit();
            valB = b->getCredit();
        }

        return isASC ? (valA < valB) : (valA > valB);
    });
}

void DBHelper::sortTeacherList(ArrayList<shared_ptr<Teacher>>& list, const QString& byColumn, const bool& isASC) {
    list.sort([&](shared_ptr<Teacher>& a, shared_ptr<Teacher>& b) {
        if (!a || !b) return !a && b;

        QVariant valA, valB;
        if (byColumn == "Id" || byColumn == "Name") {
            valA = a->get(byColumn);
            valB = b->get(byColumn);
        } else if (byColumn == "Age") {
            valA = a->getAge();
            valB = b->getAge();
        }
        return isASC ? (valA < valB) : (valA > valB);
    });
}

void DBHelper::sortCourseList(ArrayList<shared_ptr<Course>>& list, const QString& byColumn, const bool& isASC) {
    list.sort([&](shared_ptr<Course>& a, shared_ptr<Course>& b) {
        if (!a || !b) return !a && b;

        QVariant valA, valB;
        if (byColumn == "Credit") {
            valA = a->getCredit();
            valB = b->getCredit();
        } else {
            valA = a->get(byColumn);
            valB = b->get(byColumn);
        }
        return isASC ? (valA < valB) : (valA > valB);
    });
}

bool DBHelper::checkPermission(const QString& mainAction,const QString& tableName) {
    if(identityCode == 1) {
        if((tableName != "Course") || (tableName == "Course" && mainAction == "UPDATE")) {
            return false;
        }
    } else if(identityCode == 2) {
        if((mainAction != "SELECT") || (mainAction == "SELECT" && tableName == "Teacher")) {
            return false;
        }
    }
    return true;
}

