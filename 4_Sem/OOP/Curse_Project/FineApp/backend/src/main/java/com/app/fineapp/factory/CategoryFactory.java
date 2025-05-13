//package com.app.fineapp.factory;
//
//import com.app.fineapp.model.Category;
//import com.app.fineapp.model.enums.CategoryType;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Component
//public class CategoryFactory {
//
//    private final AtomicInteger idGenerator = new AtomicInteger(1);
//
//    public Category createIncomeCategory() {
//        return new Category(
//                "",
//                0,
//                CategoryType.Income,
//                idGenerator.getAndIncrement()
//        );
//    }
//
//    public Category createExpenseCategory() {
//        return new Category(
//                "",
//                0,
//                CategoryType.Expense,
//                idGenerator.getAndIncrement()
//        );
//    }
//}
