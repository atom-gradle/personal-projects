/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */
#ifndef ABSTRACTBUILDER_H
#define ABSTRACTBUILDER_H

#include <memory>

#include "student.h"
#include "teacher.h"
#include "course.h"

class AbstractBuilder {
public:
    virtual ~AbstractBuilder() = default;
    virtual void buildId() = 0;
    virtual void buildName() = 0;
    virtual void buildAge() = 0;
    virtual void buildCredit() = 0;
    virtual void buildTeacherId() = 0;
    virtual void buildCourseTime() = 0;
    virtual std::unique_ptr<Student> getStudentInstance() = 0;
    virtual std::unique_ptr<Teacher> getTeacherInstance() = 0;
    virtual std::unique_ptr<Course> getCourseInstance() = 0;
};

class StudentBuilder : public AbstractBuilder {
public:
    StudentBuilder();
    void buildId() override;
    void buildName() override;
    void buildAge() override;
    void buildCredit() override;
    std::unique_ptr<Student> getStudentInstance() override;

private:
    std::unique_ptr<Student> mStudent;
};

class TeacherBuilder : public AbstractBuilder {
public:
    TeacherBuilder();
    void buildId() override;
    void buildName() override;
    void buildAge() override;
    std::unique_ptr<Teacher> getTeacherInstance() override;

private:
    std::unique_ptr<Teacher> mTeacher;
};

class CourseBuilder : public AbstractBuilder {
public:
    CourseBuilder();
    void buildId() override;
    void buildName() override;
    void buildAge() override;
    void buildTeacherId() override;
    void buildCourseTime() override;
    std::unique_ptr<Course> getCourseInstance() override;

private:
    std::unique_ptr<Course> mCourse;
};



#endif // ABSTRACTBUILDER_H
