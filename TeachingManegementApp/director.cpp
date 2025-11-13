/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "director.h"

std::unique_ptr<Student> Director::constructStudent(AbstractBuilder& builder) {
    builder.buildId();
    builder.buildName();
    builder.buildAge();
    builder.buildCredit();
    return builder.getStudentInstance();
}

std::unique_ptr<Teacher> Director::constructTeacher(AbstractBuilder& builder) {
    builder.buildId();
    builder.buildName();
    builder.buildAge();
    builder.buildCredit();
    return builder.getTeacherInstance();
}

std::unique_ptr<Course> Director::constructCourse(AbstractBuilder& builder) {
    builder.buildId();
    builder.buildName();
    builder.buildAge();
    builder.buildCredit();
    return builder.getCourseInstance();
}
