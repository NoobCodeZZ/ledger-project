package com.example.geektrust;

public class View {

    public static void renderView(Loan curLoan, double amountPaid, int remainingEMIs) {
        System.out.printf("%s %s %.2f %d %n", curLoan.bankName, curLoan.borrowerName, amountPaid, remainingEMIs);
    }
}
