/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include "util.h"

/**
 * @brief requires empty constructor
 */
Util::Util() {

}

void Util::writeXmlFile(const QString &filePath) {
    QFile file(filePath);
    if (!file.open(QIODevice::WriteOnly | QIODevice::Text)) {
        qWarning() << "无法打开文件：" << filePath;
        return;
    }

    QXmlStreamWriter xml(&file);
    xml.setAutoFormatting(true); // 自动格式化输出

    xml.writeStartDocument(); // 开始文档
    xml.writeStartElement("library"); // 开始根元素

    xml.writeStartElement("book");
    xml.writeAttribute("id", "1");
    xml.writeTextElement("title", "Qt Programming");
    xml.writeTextElement("author", "John Doe");
    xml.writeEndElement(); // 结束 book 元素

    xml.writeStartElement("book");
    xml.writeAttribute("id", "2");
    xml.writeTextElement("title", "Advanced C++");
    xml.writeTextElement("author", "Jane Smith");
    xml.writeEndElement(); // 结束 book 元素

    xml.writeEndElement(); // 结束根元素
    xml.writeEndDocument(); // 结束文档

    file.close();
    qDebug() << "XML 文件已写入：" << filePath;
}

ArrayList<shared_ptr<Student>> Util::fetchStudentDataFromNative(QString filePath) {
    qDebug() << "start parsing students data";
    ArrayList<shared_ptr<Student>> students;

    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        qWarning("Could not open file %s", qUtf8Printable(filePath));
        return students;
    }

    QXmlStreamReader xml(&file);

    while (!xml.atEnd() && !xml.hasError()) {
        QXmlStreamReader::TokenType token = xml.readNext();

        if (token == QXmlStreamReader::StartElement && xml.name() == "student") {
            QString id, name;
            int age = 0;
            float credit = 0.0f;

            while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == "student")) {
                if (xml.tokenType() == QXmlStreamReader::StartElement) {
                    if (xml.name() == "id") {
                        id = xml.readElementText();
                    } else if (xml.name() == "name") {
                        name = xml.readElementText();
                    } else if (xml.name() == "age") {
                        age = xml.readElementText().toInt();
                    } else if (xml.name() == "credit") {
                        credit = xml.readElementText().toFloat();
                    }
                }
                xml.readNext();
            }

            if (!id.isEmpty() && !name.isEmpty()) {
                students.add(std::make_shared<Student>(id, name, age, credit));
            }
        }
    }

    if (xml.hasError()) {
        qWarning("XML error: %s", qUtf8Printable(xml.errorString()));
    }

    file.close();
    return students;
}

ArrayList<shared_ptr<Teacher>> Util::fetchTeacherDataFromNative(QString filePath) {
    qDebug() << "start parsing teachers data";
    ArrayList<shared_ptr<Teacher>> teachers;

    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        qWarning("Could not open file %s", qUtf8Printable(filePath));
        return teachers;
    }

    QXmlStreamReader xml(&file);

    while (!xml.atEnd() && !xml.hasError()) {
        QXmlStreamReader::TokenType token = xml.readNext();

        if (token == QXmlStreamReader::StartElement && xml.name() == "teacher") {
            QString id, name;
            int age = 0;

            while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == "teacher")) {
                if (xml.tokenType() == QXmlStreamReader::StartElement) {
                    if (xml.name() == "id") {
                        id = xml.readElementText();
                    } else if (xml.name() == "name") {
                        name = xml.readElementText();
                    } else if (xml.name() == "age") {
                        age = xml.readElementText().toInt();
                    }
                }
                xml.readNext();
            }

            if (!id.isEmpty() && !name.isEmpty()) {
                teachers.add(std::make_shared<Teacher>(id, name, age));
            }
        }
    }

    if (xml.hasError()) {
        qWarning("XML error: %s", qUtf8Printable(xml.errorString()));
    }

    file.close();
    return teachers;
}

ArrayList<shared_ptr<Course>> Util::fetchCourseDataFromNative(QString filePath) {
    qDebug() << "start parsing courses data";
    ArrayList<shared_ptr<Course>> courses;

    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        qWarning("Could not open file %s", qUtf8Printable(filePath));
        return courses;
    }

    QXmlStreamReader xml(&file);

    while (!xml.atEnd() && !xml.hasError()) {
        QXmlStreamReader::TokenType token = xml.readNext();

        if (token == QXmlStreamReader::StartElement && xml.name() == "course") {
            QString id, name,teacherName,courseTime;
            float age = 0.0f;

            while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == "course")) {
                if (xml.tokenType() == QXmlStreamReader::StartElement) {
                    if (xml.name() == "id") {
                        id = xml.readElementText();
                    } else if (xml.name() == "name") {
                        name = xml.readElementText();
                    } else if (xml.name() == "credits") {
                        age = xml.readElementText().toFloat();
                    } else if(xml.name() == "teacher") {
                        teacherName = xml.readElementText();
                    } else if(xml.name() == "coursetime") {
                        courseTime = xml.readElementText();
                    }
                }
                xml.readNext();
            }

            if (!id.isEmpty() && !name.isEmpty()) {
                courses.add(std::make_shared<Course>(id, name, age,teacherName,courseTime));
            }
        }
    }

    if (xml.hasError()) {
        qWarning("XML error: %s", qUtf8Printable(xml.errorString()));
    }

    file.close();
    return courses;
}

bool Util::saveStudentDataToNative(QString filePath, ArrayList<shared_ptr<Student> >) {
    if(filePath == "") {
        filePath = generateDateTimeFilePath("StudentData",".xml");
    }
    return true;
}

bool Util::saveTeacherDataToNative(QString filePath, ArrayList<shared_ptr<Teacher> >) {
    if(filePath == "") {
        filePath = generateDateTimeFilePath("TeacherData",".xml");
    }
    return true;
}

bool Util::saveCourseDataToNative(QString filePath, ArrayList<shared_ptr<Course> >) {
    if(filePath == "") {
        filePath = generateDateTimeFilePath("CourseData",".xml");
    }
    return true;
}

/**
 * @brief Util::generateDateTimeFilePath
 * @param who Student,Teacher,or Course
 * @param fileExt the extension for the file
 * @return a datatime fileName
 */
QString Util::generateDateTimeFilePath(const QString& who,const QString& fileExt) {
    // 获取当前时间并格式化为字符串（例如：20240610_153045）
    QString timestamp = QDateTime::currentDateTime().toString("yyyyMMdd_HHmmss");
    // 拼接完整路径：baseDir/prefix_timestamp.extension
    QString fileName = QString("%1_%2.%3").arg(who,timestamp, fileExt);
    return fileName;
}

/**
 * @brief getPrerequisiteCourseMap
 * @param filePath
 * @return a hashmap containing
 */
HashMap<QString, ArrayList<shared_ptr<QString>>> getPrerequisiteCourseMap(const QString& filePath) {
    qDebug() << "start parsing prerequisitecourses data";
    HashMap<QString,ArrayList<shared_ptr<QString>>> resultMap;
    ArrayList<shared_ptr<QString>> prerequisiteCourseList;

    QFile file(filePath);
    if (!file.open(QIODevice::ReadOnly | QIODevice::Text)) {
        qWarning("Could not open file %s", qUtf8Printable(filePath));
        return resultMap;
    }

    QXmlStreamReader xml(&file);

    while (!xml.atEnd() && !xml.hasError()) {
        QXmlStreamReader::TokenType token = xml.readNext();

        if (token == QXmlStreamReader::StartElement && xml.name() == "course") {
            QString id, name;

            while (!(xml.tokenType() == QXmlStreamReader::EndElement && xml.name() == "course")) {
                if (xml.tokenType() == QXmlStreamReader::StartElement) {
                    if (xml.name() == "id") {
                        id = xml.readElementText();
                    }
                }
                xml.readNext();
            }

            if (!id.isEmpty() && !name.isEmpty()) {
                resultMap.put("a",prerequisiteCourseList);
            }
        }
    }

    if (xml.hasError()) {
        qWarning("XML error: %s", qUtf8Printable(xml.errorString()));
    }

    file.close();
    return resultMap;
}
