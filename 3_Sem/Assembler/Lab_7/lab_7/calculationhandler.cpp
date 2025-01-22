#include "calculationhandler.h"

CalculationHandler::CalculationHandler(){}

QList<std::pair<double,bool>> CalculationHandler::Start(QString A, QString B, QString C)
{
    double a = A.toDouble();
    double b = B.toDouble();
    double c = C.toDouble();

    D = discriminant(a,b,c);

    if(D == -1){
        QList<std::pair<double,bool>>* list = new QList<std::pair<double,bool>>;
        std::pair<double,bool> null_pair(0,false);

        list->append(null_pair);
        list->append(null_pair);
        list->append(null_pair);
        list->append(null_pair);

        return *list;
    }

    D = sqrt(D);

    if(a == 0 and b == 0){
        //chill and do nothing
    }
    else if(a == 0){
        X_A = calculate_x_a_zero(a, b, D, c);
        X_B = -1;
    }
    else{
        X_A = calculate_x_minus(a, b, D);
        X_B = calculate_x_plus (a, b, D);
    }

    ResultHandler *resHandler = new ResultHandler(X_A, X_B);
    return resHandler->Start();
}


