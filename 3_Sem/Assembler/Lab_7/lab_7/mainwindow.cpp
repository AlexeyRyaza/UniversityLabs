#include "mainwindow.h"
#include "./ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    //Hexadecimal Container
    Lables_16.append(ui->label_res_x1_16);
    Lables_16.append(ui->label_res_x2_16);
    Lables_16.append(ui->label_res_x3_16);
    Lables_16.append(ui->label_res_x4_16);

    //Decimal Container
    Lables_10.append(ui->label_res_x1_10);
    Lables_10.append(ui->label_res_x2_10);
    Lables_10.append(ui->label_res_x3_10);
    Lables_10.append(ui->label_res_x4_10);

    //Binary Container
    Lables_2.append(ui->label_res_x1_2);
    Lables_2.append(ui->label_res_x2_2);
    Lables_2.append(ui->label_res_x3_2);
    Lables_2.append(ui->label_res_x4_2);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::on_PB_Calculate_clicked()
{
    ResetResult();

    list_10 = calcHandler->Start(
        ui->LE_A->text(),
        ui->LE_B->text(),
        ui->LE_C->text());

    CulcBinResult();
    CulcHexResult();

    DisplayResult();
}

void MainWindow::CulcBinResult()
{
    for(int i = 0; i <= 3; ++i){
        quint64 binaryRepresentation;
        memcpy(&binaryRepresentation, &list_10.at(i).first, sizeof(double));

        QString binaryString;
        for (int i = 63; i >= 0; --i) {
            binaryString += ((binaryRepresentation >> i) & 1) ? '1' : '0';
        }

        list_2.append(std::pair<QString, bool>(binaryString, list_10.at(i).second));
    }
}

void MainWindow::CulcHexResult()
{
    for(int i = 0; i <= 3; ++i){
        quint64 HexRepresentation;
        memcpy(&HexRepresentation, &list_10.at(i).first, sizeof(double));

        QString hexString = QString::number(HexRepresentation, 16).toUpper();

        list_16.append(std::pair<QString, bool>(hexString, list_10.at(i).second));
    }
}

QString MainWindow::reverseString(const QString &str)
{
    QString reversed;

    for(auto it = str.cbegin(); it != str.cend(); ++it){
        reversed.append(*it);
    }

    return reversed;
}

void MainWindow::ResetResult()
{
    for(int i = 0; i <= 3; ++i){
        SetUiText(Lables_2.at(i), "-", true);
        SetUiText(Lables_10.at(i), "-", true);
        SetUiText(Lables_16.at(i), "-", true);
    }

    list_2.clear();
    list_16.clear();
}

void MainWindow::DisplayResult()
{
    //Display decimal
    for(int i = 0; i <= 3; ++i){
        SetUiText(Lables_10.at(i), QString().number(list_10.at(i).first), list_10.at(i).second);
    }

    //Display Binary
    for(int i = 0; i <= 3; ++i){
        SetUiText(Lables_2.at(i), list_2.at(i).first, list_2.at(i).second);
    }

    //Display Hexadecimal
    for(int i = 0; i <= 3; ++i){
        SetUiText(Lables_16.at(i), list_16.at(i).first, list_16.at(i).second);
    }
}

//QString().number(list.at(i).first)
void MainWindow::SetUiText(QLabel *label, QString text, bool flag)
{
    if(flag){
        label->setText(text);
    }
    else{
        label->setText(QString("-"));
    }


}

