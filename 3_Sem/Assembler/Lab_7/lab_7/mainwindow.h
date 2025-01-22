#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

#include "calculationhandler.h"
#include <QLabel>

QT_BEGIN_NAMESPACE
namespace Ui {
class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

private slots:
    void on_PB_Calculate_clicked();

private:
    Ui::MainWindow *ui;

    QList<QLabel*> Lables_16;
    QList<QLabel*> Lables_10;
    QList<QLabel*> Lables_2;

    void CulcBinResult();
    void CulcHexResult();
    QString reverseString(const QString& str);

    void ResetResult();
    void DisplayResult();
    void SetUiText(QLabel *label, QString text, bool flag);

    CalculationHandler *calcHandler = new CalculationHandler();
    QList<std::pair<double,bool>> list_10;
    QList<std::pair<QString,bool>> list_2;
    QList<std::pair<QString,bool>> list_16;
};
#endif // MAINWINDOW_H
