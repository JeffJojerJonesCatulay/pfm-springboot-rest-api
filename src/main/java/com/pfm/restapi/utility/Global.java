package com.pfm.restapi.utility;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Global {
    public static String getMonthName(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        LocalDate date = LocalDate.parse(dateStr); // expects yyyy-MM-dd
        return date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    public static int getYear(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return 0;
        LocalDate date = LocalDate.parse(dateStr);
        return date.getYear();
    }

    public double computeGrowthRate(double present, double past){
        double step1 = present - past;
        double step2 = step1 / past;
        double step3 = step2 * 100;
        return (step3);
    }
}
