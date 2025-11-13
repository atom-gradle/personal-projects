/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include <QString>
#include <QPainter>
#include <QSize>
#include <QStyledItemDelegate>
#include <QDebug>

/**
 * @brief The CustomCourseDelegate class Serves as an adapter for binding and displaying course data
 * @author Qian
 * @since 1.0.0
 */
class CustomCourseDelegate : public QStyledItemDelegate {
public:
    void paint(QPainter *painter, const QStyleOptionViewItem &option,
               const QModelIndex &index) const override {
        QStyledItemDelegate::paint(painter, option, index);

        // 获取自定义数据
        int number_index = index.data(Qt::UserRole).toInt();
        QString id = index.data(Qt::UserRole + 1).toString();
        QString name = index.data(Qt::UserRole + 2).toString();
        QString teacherName = index.data(Qt::UserRole + 3).toString();
        QString credits = index.data(Qt::UserRole + 4).toString();
        QString courseTime = index.data(Qt::UserRole + 5).toString();

        // 在左侧绘制行号
        QRect rect = option.rect.adjusted(0, 0, -10, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,
                         QString("%1").arg(number_index));
        // 在右侧依次绘制id,name,teacherName,credits,courseTime
        rect = option.rect.adjusted(50, 0, 0, 0);
        //painter->setFont(QFont("Arial", 14));
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,id);
        rect = option.rect.adjusted(260, 0, 0, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,name);
        rect = option.rect.adjusted(620, 0, 0, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,teacherName);
        rect = option.rect.adjusted(720, 0, 0, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,credits);
        rect = option.rect.adjusted(890, 0, 0, 0);
        painter->drawText(rect, Qt::TextWordWrap,courseTime);
    }

    QSize sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const override {
        QFontMetrics fm(option.font);
        int width = 1400 - 20;  // 预留边距
        QString text = index.data(Qt::DisplayRole).toString();
        QRect rect = fm.boundingRect(QRect(0, 0, width, 0),
              Qt::TextWordWrap, text);
        return rect.size();
    }
};
