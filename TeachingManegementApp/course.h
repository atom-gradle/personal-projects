/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef COURSE_H
#define COURSE_H
#include <iostream>
#include <QString>
#include "teacher.h"
#include "arraylist.h"

using namespace std;

class Course {
private:
    QString id0;
    QString name0;
    float credits0;
    QString teacherId0;
    QString courseTime0;

    QString courseId0;

    Teacher teacher0;

public:
    Course();
    Course(QString id,QString name,float credits,QString teacherName,QString courseTime);

    QString getId();
    QString getName();
    float getCredit();
    QString getTeacherId();
    QString getCourseTime();

    QString get(QString whatToGet);

    void set(QString whichField,QString value);
};

#endif // COURSE_H
