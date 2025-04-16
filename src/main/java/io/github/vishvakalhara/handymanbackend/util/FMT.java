package io.github.vishvakalhara.handymanbackend.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FMT {

    public static String formatCurrency(double amount){

        // Set custom symbol
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setCurrencySymbol("LKR ");
        symbols.setGroupingSeparator(',');
        symbols.setMonetaryDecimalSeparator('.');

        // Create formatter
        DecimalFormat formatter = new DecimalFormat("¤#,##0.00", symbols);
        return formatter.format(amount);
    }
}
