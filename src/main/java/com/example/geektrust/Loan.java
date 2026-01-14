package com.example.geektrust;

import java.util.Map;
import java.util.TreeMap;

public class Loan {
    final String borrowerName;
    final String bankName;
    private final int principalAmount;
    private final int years;
    private final int roi;
    private final int emiAmount;
    private final double repaymentAmount;
    Map<Integer,Integer> lumpSums = new TreeMap<>();

    //constructor for the Loan class which will initialize all the values properly
    // this is responsible for creating a new Loan Object
    Loan(String[] inpLine) {
        this.bankName = inpLine[1];
        this.borrowerName = inpLine[2];
        this.principalAmount = Integer.parseInt(inpLine[3]);
        this.years = Integer.parseInt(inpLine[4]);
        this.roi = Integer.parseInt(inpLine[5]);
        this.repaymentAmount = this.calculateRepaymentAmount();
        this.emiAmount = (int) Math.ceil((this.repaymentAmount/(double)(this.years*12.0)));
    }

    private double calculateRepaymentAmount() {
        double interestAmount = this.principalAmount*((double)this.roi/(double)100)*this.years;
        return this.principalAmount + interestAmount;
    }

    private int calculateRemainingEMI(double remainingAmount) {
         return (int) Math.ceil(remainingAmount/(double)emiAmount);
    }

    public void processPayment(int depositAmount, int emiMonth) {
        // checking if there is already a mapping corresponding to the emi month in the arguments
        int curDeposit;
        int newDeposit = depositAmount;
        if(lumpSums.containsKey(emiMonth)) {
            curDeposit = lumpSums.get(emiMonth);
            newDeposit += curDeposit;
        }
        lumpSums.put(emiMonth, newDeposit);
    }

    public void processBalance(int emiMonth) {
        // have to process the balance command
        // the main return value in this is the amount that has been paid by the user + the number of emis left
        // Output format - BANK_NAME BORROWER_NAME AMOUNT_PAID NO_OF_EMIS_LEFT

        // amount paid = all lumpsum payment made till that month + emi payments made
        int emiPayments = emiMonth*this.emiAmount;
        int lumpSumPayments = 0;

        // iterating over the map and checking if each payment made was made before the target EMI Month
        // have to handle the case when lumpSums is null
        if(lumpSums != null) {
            for (Map.Entry<Integer, Integer> entry : this.lumpSums.entrySet()) {
                if(entry.getKey() <= emiMonth) {
                    lumpSumPayments += entry.getValue();
                }
            }
        }
        double amountPaid = emiPayments + lumpSumPayments;
        if(amountPaid > repaymentAmount ) {
            amountPaid = repaymentAmount;
        }
        double remainingAmount = repaymentAmount - amountPaid;
        int remainingEMIs = 0;
        if(remainingAmount <= 0) {
            remainingAmount = 0;
        }
        else if(remainingAmount < emiAmount) {
            remainingEMIs = 1;
        }
        else {
            remainingEMIs = calculateRemainingEMI(remainingAmount);
        }

        View view = new View();
        View.renderView(this,amountPaid,remainingEMIs);
    }
}
