#ifndef RESULTHANDLER_H
#define RESULTHANDLER_H

#include <QList>

class ResultHandler
{
public:
    ResultHandler(double X_A, double X_B);
    QList<std::pair<double,bool>> Start();

private:
    double X_A;
    double X_B;
    bool IS_X_A_valid = true;
    bool IS_X_B_valid = true;

    double X_1;
    double X_2;
    double X_3;
    double X_4;
    bool IS_X_1 = true;
    bool IS_X_2 = true;
    bool IS_X_3 = true;
    bool IS_X_4 = true;


    std::pair<double,bool> null_pair = std::pair<double,bool>(0,false);

    void CulcResult();
    void CheckValidity();
    void SetListInOrder();


    QList<std::pair<double,bool>> list;
};

#endif // RESULTHANDLER_H
