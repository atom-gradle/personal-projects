/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef STUDENT_H
#define STUDENT_H

#include <QString>
#include <QDebug>

class Student {

private:
    QString id0;
    QString name0;
    int age0;
    float credit0;
public:
    Student();
    Student(QString id,QString name,int age,float credit);
    //Student(QString fieldId="",QString fieldName="",QString fieldAge="",QString fieldCredits="");
    QString getId();
    QString getName();
    int getAge();
    float getCredit();

    QString get(QString whatToGet);

    void setId(QString id);
    void setName(QString name);
    void setAge(int age);
    void setCredit(float credit);

    void set(QString whichField,QString value);

    friend QDebug operator<<(QDebug debug, const Student& student) {
        debug << "Student(name=" << student.name0 << ", age=" << student.age0 << ")";
        return debug;
    }
};

#endif // STUDENT_H
