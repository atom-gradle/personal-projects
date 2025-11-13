/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "student.h"

Student::Student(){

}

Student::Student(QString id,QString name,int age,float credit) {
    id0 = id;
    name0 = name;
    age0 = age;
    credit0 = credit;
}

QString Student::getId() {
    return id0;
}

QString Student::getName() {
    return name0;
}

int Student::getAge() {
    return age0;
}

float Student::getCredit() {
    return credit0;
}

QString Student::get(QString whatToGet) {
    if(whatToGet == "Id" || whatToGet == "ID") {
        return id0;
    } else if(whatToGet == "Name") {
        return name0;
    } else if(whatToGet == "Age") {
        return QString::number(age0);
    } else if(whatToGet == "Credit" || whatToGet == "Credits") {
        return QString::number(credit0);
    }
}

void Student::setId(QString id) {
    id0 = id;
}

void Student::setName(QString name) {
    name0 = name;
}
void Student::setAge(int age) {
    age0 = age;
}

void Student::setCredit(float credit) {
    credit0 = credit;
}

void Student::set(QString whichFiled,QString value) {
    if(whichFiled == "ID" || whichFiled == "Id") {
        setId(value);
    } else if(whichFiled == "Name") {
        setName(value);
    } else if(whichFiled == "Age") {
        setAge(value.toInt());
    } else if(whichFiled == "Credits" || whichFiled == "Credit") {
        setCredit(value.toFloat());
    }
}
