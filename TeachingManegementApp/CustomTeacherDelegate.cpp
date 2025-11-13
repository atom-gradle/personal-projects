/*
 * SPDX-License-Identifier: AGPL-3.0-only WITH LicenseRef-Commercial-Exception
 * Copyright (C) 2025 Qian
 * See NOTICE file for additional terms.
 */

#include <QString>
#include <QPainter>
#include <QSize>
#include <QStyledItemDelegate>

/**
 * @brief The CustomCourseDelegate class Serves as an adapter for binding and displaying teacher data
 * @author Qian
 * @since 1.0.0
 */
class CustomTeacherDelegate : public QStyledItemDelegate {
public:
    void paint(QPainter *painter, const QStyleOptionViewItem &option,
               const QModelIndex &index) const override {
        QStyledItemDelegate::paint(painter, option, index);

        // 获取自定义数据
        QString number_index = index.data(Qt::UserRole).toString();
        QString id = index.data(Qt::UserRole + 1).toString();
        QString name = index.data(Qt::UserRole + 2).toString();
        QString age = index.data(Qt::UserRole+3).toString();

        // 在左侧绘制行号
        QRect rect = option.rect.adjusted(0, 0, -10, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,number_index);

        // 在右侧依次绘制id,name,age,credits
        rect = option.rect.adjusted(50, 0, 0, 0);
        //painter->setFont(QFont("Arial", 14));
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,id);
        rect = option.rect.adjusted(300, 0, 0, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,name);
        rect = option.rect.adjusted(450, 0, 0, 0);
        painter->drawText(rect, Qt::AlignLeft | Qt::AlignVCenter,age);
    }

    QSize sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const override {
        return QSize(200, 40); // 增加行高以显示额外数据
    }
};
