/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef TEACHER_H
#define TEACHER_H
#include <QString>

class Teacher {
private:
    QString id0;
    QString name0;
    int age0;

public:
    Teacher();
    /**
     * @brief Teacher
     * @param id
     * @param name
     * @param age
     */
    Teacher(QString id,QString name,int age);
    QString getId();
    QString getName();
    int getAge();

    QString get(QString whatToGet);

    void setId(QString id);
    void setName(QString name);
    void setAge(int age);

    void set(QString whichField,QString value);
};

#endif // TEACHER_H
