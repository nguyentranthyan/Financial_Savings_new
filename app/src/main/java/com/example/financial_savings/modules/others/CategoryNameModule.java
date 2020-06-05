package com.example.financial_savings.modules.others;

public class CategoryNameModule {

    public static String getLabelByText(String text) {
        if(text.equals("Doanh thu")) {
            return "doanhthu";
        }
        return "khoanchi";
    }
}
