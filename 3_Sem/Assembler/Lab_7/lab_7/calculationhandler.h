#ifndef CALCULATIONHANDLER_H
#define CALCULATIONHANDLER_H

#include <QString>
#include <string.h>
#include <QDebug>

#include "resulthandler.h"

extern "C" double checkstring(const char* str);
extern "C" double discriminant(double a, double b, double c);
extern "C" double calculate_x_minus(double a, double b, double sqrt_d);
extern "C" double calculate_x_plus(double a, double b, double sqrt_d);
extern "C" double calculate_x_a_zero(double a, double b, double sqrt_d, double c);

class CalculationHandler
{
public:
    CalculationHandler();
    QList<std::pair<double,bool>> Start(QString A, QString B, QString C);

private:
    double D = 0;

    double X_A = 0;
    double X_B = 0;
};

#endif // CALCULATIONHANDLER_H
