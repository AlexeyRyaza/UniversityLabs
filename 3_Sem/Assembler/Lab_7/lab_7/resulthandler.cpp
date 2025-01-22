#include "resulthandler.h"

ResultHandler::ResultHandler(double X_A, double X_B)
{
    this->X_A = X_A;
    this->X_B = X_B;

    for(int i = 0; i <= 3; ++i){
        list.append(null_pair);
    }
}

void ResultHandler::CulcResult()
{
    if(IS_X_A_valid){
        X_1 = sqrt(X_A);
        X_2 = -sqrt(X_A);
    }
    else{
        IS_X_1 = false;
        IS_X_2 = false;
    }


    if(IS_X_B_valid){
        X_3 = sqrt(X_B);
        X_4 = -sqrt(X_B);
    }
    else{
        IS_X_3 = false;
        IS_X_4 = false;
    }
}

//std::pair<double,bool> null_pair(0,false);

void ResultHandler::CheckValidity()
{
    if(X_A < 0){
        IS_X_A_valid = false;
    }
    if(X_B < 0){
        IS_X_B_valid = false;
    }
}

void ResultHandler::SetListInOrder()
{
    if(IS_X_1){
        list[0].first = X_1;
        list[0].second = true;
        list[1].first = X_2;
        list[1].second = true;

        if(IS_X_2){
            list[2].first = X_3;
            list[2].second = true;
            list[3].first = X_4;
            list[3].second = true;
        }
    }
    else if(IS_X_3){
        list[0].first = X_3;
        list[0].second = true;
        list[1].first = X_4;
        list[1].second = true;
    }
}

QList<std::pair<double,bool>>ResultHandler::Start()
{
    CheckValidity();
    CulcResult();
    SetListInOrder();

    return list;
}
