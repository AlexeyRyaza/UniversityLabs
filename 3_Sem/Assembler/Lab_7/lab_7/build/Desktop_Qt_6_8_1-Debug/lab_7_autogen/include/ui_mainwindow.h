/********************************************************************************
** Form generated from reading UI file 'mainwindow.ui'
**
** Created by: Qt User Interface Compiler version 6.8.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MAINWINDOW_H
#define UI_MAINWINDOW_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>
#include <QtWidgets/QLineEdit>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QPushButton>
#include <QtWidgets/QVBoxLayout>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_MainWindow
{
public:
    QWidget *centralwidget;
    QLabel *label;
    QLabel *label_2;
    QPushButton *PB_Calculate;
    QWidget *layoutWidget;
    QVBoxLayout *verticalLayout;
    QLabel *label_A;
    QLabel *label_B;
    QLabel *label_C;
    QWidget *layoutWidget1;
    QVBoxLayout *verticalLayout_2;
    QLineEdit *LE_A;
    QLineEdit *LE_B;
    QLineEdit *LE_C;
    QWidget *layoutWidget2;
    QVBoxLayout *verticalLayout_3;
    QLabel *label_x1;
    QLabel *label_x2;
    QLabel *label_x3;
    QLabel *label_x4;
    QWidget *layoutWidget3;
    QVBoxLayout *V_Layout_10;
    QLabel *label_res_x1_10;
    QLabel *label_res_x2_10;
    QLabel *label_res_x3_10;
    QLabel *label_res_x4_10;
    QWidget *layoutWidget_2;
    QVBoxLayout *V_Layout_2;
    QLabel *label_res_x1_2;
    QLabel *label_res_x2_2;
    QLabel *label_res_x3_2;
    QLabel *label_res_x4_2;
    QWidget *layoutWidget_3;
    QVBoxLayout *V_Layout_16;
    QLabel *label_res_x1_16;
    QLabel *label_res_x2_16;
    QLabel *label_res_x3_16;
    QLabel *label_res_x4_16;
    QLabel *label_3;
    QLabel *label_4;
    QLabel *label_5;

    void setupUi(QMainWindow *MainWindow)
    {
        if (MainWindow->objectName().isEmpty())
            MainWindow->setObjectName("MainWindow");
        MainWindow->resize(1160, 212);
        centralwidget = new QWidget(MainWindow);
        centralwidget->setObjectName("centralwidget");
        label = new QLabel(centralwidget);
        label->setObjectName("label");
        label->setGeometry(QRect(30, 30, 66, 18));
        label_2 = new QLabel(centralwidget);
        label_2->setObjectName("label_2");
        label_2->setGeometry(QRect(170, 30, 66, 18));
        PB_Calculate = new QPushButton(centralwidget);
        PB_Calculate->setObjectName("PB_Calculate");
        PB_Calculate->setGeometry(QRect(30, 170, 88, 26));
        layoutWidget = new QWidget(centralwidget);
        layoutWidget->setObjectName("layoutWidget");
        layoutWidget->setGeometry(QRect(30, 70, 21, 81));
        verticalLayout = new QVBoxLayout(layoutWidget);
        verticalLayout->setObjectName("verticalLayout");
        verticalLayout->setContentsMargins(0, 0, 0, 0);
        label_A = new QLabel(layoutWidget);
        label_A->setObjectName("label_A");

        verticalLayout->addWidget(label_A);

        label_B = new QLabel(layoutWidget);
        label_B->setObjectName("label_B");

        verticalLayout->addWidget(label_B);

        label_C = new QLabel(layoutWidget);
        label_C->setObjectName("label_C");

        verticalLayout->addWidget(label_C);

        layoutWidget1 = new QWidget(centralwidget);
        layoutWidget1->setObjectName("layoutWidget1");
        layoutWidget1->setGeometry(QRect(60, 70, 41, 92));
        verticalLayout_2 = new QVBoxLayout(layoutWidget1);
        verticalLayout_2->setObjectName("verticalLayout_2");
        verticalLayout_2->setContentsMargins(0, 0, 0, 0);
        LE_A = new QLineEdit(layoutWidget1);
        LE_A->setObjectName("LE_A");

        verticalLayout_2->addWidget(LE_A);

        LE_B = new QLineEdit(layoutWidget1);
        LE_B->setObjectName("LE_B");

        verticalLayout_2->addWidget(LE_B);

        LE_C = new QLineEdit(layoutWidget1);
        LE_C->setObjectName("LE_C");

        verticalLayout_2->addWidget(LE_C);

        layoutWidget2 = new QWidget(centralwidget);
        layoutWidget2->setObjectName("layoutWidget2");
        layoutWidget2->setGeometry(QRect(200, 70, 24, 92));
        verticalLayout_3 = new QVBoxLayout(layoutWidget2);
        verticalLayout_3->setObjectName("verticalLayout_3");
        verticalLayout_3->setContentsMargins(0, 0, 0, 0);
        label_x1 = new QLabel(layoutWidget2);
        label_x1->setObjectName("label_x1");

        verticalLayout_3->addWidget(label_x1);

        label_x2 = new QLabel(layoutWidget2);
        label_x2->setObjectName("label_x2");

        verticalLayout_3->addWidget(label_x2);

        label_x3 = new QLabel(layoutWidget2);
        label_x3->setObjectName("label_x3");

        verticalLayout_3->addWidget(label_x3);

        label_x4 = new QLabel(layoutWidget2);
        label_x4->setObjectName("label_x4");

        verticalLayout_3->addWidget(label_x4);

        layoutWidget3 = new QWidget(centralwidget);
        layoutWidget3->setObjectName("layoutWidget3");
        layoutWidget3->setGeometry(QRect(240, 70, 81, 92));
        V_Layout_10 = new QVBoxLayout(layoutWidget3);
        V_Layout_10->setObjectName("V_Layout_10");
        V_Layout_10->setContentsMargins(0, 0, 0, 0);
        label_res_x1_10 = new QLabel(layoutWidget3);
        label_res_x1_10->setObjectName("label_res_x1_10");

        V_Layout_10->addWidget(label_res_x1_10);

        label_res_x2_10 = new QLabel(layoutWidget3);
        label_res_x2_10->setObjectName("label_res_x2_10");

        V_Layout_10->addWidget(label_res_x2_10);

        label_res_x3_10 = new QLabel(layoutWidget3);
        label_res_x3_10->setObjectName("label_res_x3_10");

        V_Layout_10->addWidget(label_res_x3_10);

        label_res_x4_10 = new QLabel(layoutWidget3);
        label_res_x4_10->setObjectName("label_res_x4_10");

        V_Layout_10->addWidget(label_res_x4_10);

        layoutWidget_2 = new QWidget(centralwidget);
        layoutWidget_2->setObjectName("layoutWidget_2");
        layoutWidget_2->setGeometry(QRect(340, 70, 561, 92));
        V_Layout_2 = new QVBoxLayout(layoutWidget_2);
        V_Layout_2->setObjectName("V_Layout_2");
        V_Layout_2->setContentsMargins(0, 0, 0, 0);
        label_res_x1_2 = new QLabel(layoutWidget_2);
        label_res_x1_2->setObjectName("label_res_x1_2");

        V_Layout_2->addWidget(label_res_x1_2);

        label_res_x2_2 = new QLabel(layoutWidget_2);
        label_res_x2_2->setObjectName("label_res_x2_2");

        V_Layout_2->addWidget(label_res_x2_2);

        label_res_x3_2 = new QLabel(layoutWidget_2);
        label_res_x3_2->setObjectName("label_res_x3_2");

        V_Layout_2->addWidget(label_res_x3_2);

        label_res_x4_2 = new QLabel(layoutWidget_2);
        label_res_x4_2->setObjectName("label_res_x4_2");

        V_Layout_2->addWidget(label_res_x4_2);

        layoutWidget_3 = new QWidget(centralwidget);
        layoutWidget_3->setObjectName("layoutWidget_3");
        layoutWidget_3->setGeometry(QRect(910, 70, 201, 92));
        V_Layout_16 = new QVBoxLayout(layoutWidget_3);
        V_Layout_16->setObjectName("V_Layout_16");
        V_Layout_16->setContentsMargins(0, 0, 0, 0);
        label_res_x1_16 = new QLabel(layoutWidget_3);
        label_res_x1_16->setObjectName("label_res_x1_16");

        V_Layout_16->addWidget(label_res_x1_16);

        label_res_x2_16 = new QLabel(layoutWidget_3);
        label_res_x2_16->setObjectName("label_res_x2_16");

        V_Layout_16->addWidget(label_res_x2_16);

        label_res_x3_16 = new QLabel(layoutWidget_3);
        label_res_x3_16->setObjectName("label_res_x3_16");

        V_Layout_16->addWidget(label_res_x3_16);

        label_res_x4_16 = new QLabel(layoutWidget_3);
        label_res_x4_16->setObjectName("label_res_x4_16");

        V_Layout_16->addWidget(label_res_x4_16);

        label_3 = new QLabel(centralwidget);
        label_3->setObjectName("label_3");
        label_3->setGeometry(QRect(250, 30, 66, 18));
        label_4 = new QLabel(centralwidget);
        label_4->setObjectName("label_4");
        label_4->setGeometry(QRect(350, 30, 66, 18));
        label_5 = new QLabel(centralwidget);
        label_5->setObjectName("label_5");
        label_5->setGeometry(QRect(910, 30, 66, 18));
        MainWindow->setCentralWidget(centralwidget);

        retranslateUi(MainWindow);

        QMetaObject::connectSlotsByName(MainWindow);
    } // setupUi

    void retranslateUi(QMainWindow *MainWindow)
    {
        MainWindow->setWindowTitle(QCoreApplication::translate("MainWindow", "MainWindow", nullptr));
        label->setText(QCoreApplication::translate("MainWindow", "Input:", nullptr));
        label_2->setText(QCoreApplication::translate("MainWindow", "Result:", nullptr));
        PB_Calculate->setText(QCoreApplication::translate("MainWindow", "Calculate", nullptr));
        label_A->setText(QCoreApplication::translate("MainWindow", "A:", nullptr));
        label_B->setText(QCoreApplication::translate("MainWindow", "B:", nullptr));
        label_C->setText(QCoreApplication::translate("MainWindow", "C:", nullptr));
        label_x1->setText(QCoreApplication::translate("MainWindow", "X1:", nullptr));
        label_x2->setText(QCoreApplication::translate("MainWindow", "X2:", nullptr));
        label_x3->setText(QCoreApplication::translate("MainWindow", "X3:", nullptr));
        label_x4->setText(QCoreApplication::translate("MainWindow", "X4:", nullptr));
        label_res_x1_10->setText(QString());
        label_res_x2_10->setText(QString());
        label_res_x3_10->setText(QString());
        label_res_x4_10->setText(QString());
        label_res_x1_2->setText(QString());
        label_res_x2_2->setText(QString());
        label_res_x3_2->setText(QString());
        label_res_x4_2->setText(QString());
        label_res_x1_16->setText(QString());
        label_res_x2_16->setText(QString());
        label_res_x3_16->setText(QString());
        label_res_x4_16->setText(QString());
        label_3->setText(QCoreApplication::translate("MainWindow", "Decimal", nullptr));
        label_4->setText(QCoreApplication::translate("MainWindow", "Bin", nullptr));
        label_5->setText(QCoreApplication::translate("MainWindow", "Hex", nullptr));
    } // retranslateUi

};

namespace Ui {
    class MainWindow: public Ui_MainWindow {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MAINWINDOW_H
