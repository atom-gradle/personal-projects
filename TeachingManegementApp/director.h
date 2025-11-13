/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#ifndef DIRECTOR_H
#define DIRECTOR_H

#include <memory>

#include "abstractbuilder.h"

class Director {
public:
    std::unique_ptr<Student> constructStudent(AbstractBuilder& builder);
    std::unique_ptr<Teacher> constructTeacher(AbstractBuilder& builder);
    std::unique_ptr<Course> constructCourse(AbstractBuilder& builder);
};

#endif // DIRECTOR_H
