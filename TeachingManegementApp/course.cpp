/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "course.h"

Course::Course() {

}

Course::Course(QString id, QString name, float credit, QString teacherName, QString courseTime) {
    id0 = id;
    name0 = name;
    credits0 = credit;
    teacherId0 = teacherName;
    courseTime0 = courseTime;
}

QString Course::getId() {
    return id0;
}

QString Course::getName() {
    return name0;
}

float Course::getCredit() {
    return credits0;
}

QString Course::getTeacherId() {
    return teacherId0;
}

QString Course::getCourseTime() {
    return courseTime0;
}

QString Course::get(QString whatToGet) {
    if(whatToGet == "ID" || whatToGet == "Id") {
        return id0;
    } else if(whatToGet == "Name") {
        return name0;
    } else if(whatToGet == "Credits") {
        return QString::number(credits0);
    } else if(whatToGet == "Teacher" || whatToGet == "TeacherName") {
        return teacherId0;
    }
}

void Course::set(QString whichField, QString value) {
    if(whichField == "ID" || whichField == "Id") {
        id0 = value;
    } else if(whichField == "Name") {
        name0 = value;
    } else if(whichField == "Credits") {
        credits0 = value.toFloat();
    } else if(whichField == "Teacher" || whichField == "TeacherField") {
        teacherId0 = value;
    }
}
