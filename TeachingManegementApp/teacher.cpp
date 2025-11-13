/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "teacher.h"

Teacher::Teacher(){

}

Teacher::Teacher(QString id, QString name, int age) {
    id0 = id;
    name0 = name;
    age0 = age;
}

QString Teacher::getId() {
    return id0;
}

QString Teacher::getName(){
    return name0;
}

int Teacher::getAge() {
    return age0;
}

QString Teacher::get(QString whatToGet) {
    if(whatToGet == "ID" || whatToGet == "Id") {
        return id0;
    } else if(whatToGet == "Name") {
        return name0;
    } else if(whatToGet == "Age") {
        return QString::number(age0);
    }
}

void Teacher::set(QString whichField, QString value) {
    if(whichField == "ID" || whichField == "Id") {
        id0 = value;
    } else if(whichField == "Name") {
        name0 = value;
    } else if(whichField == "Age") {
        age0 = value.toInt();
    }
}

