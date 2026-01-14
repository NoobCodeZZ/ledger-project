package com.example.geektrust;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;


// there are 3 types of input that the program takes:
// LOAN : Format - LOAN BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST
// PAYMENT :  PAYMENT BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO
// BALANCE : BALANCE BANK_NAME BORROWER_NAME EMI_NO
public class Main {
    //creating a hashmap with key as (borrower name + bank Name)
    // and value as the Loan Object
    static HashMap<String,Loan> loanMap = new HashMap<>();

    private static ArrayList<String[]> readFile(String fileName) {
        ArrayList<String[]> allInputs = new ArrayList<>();
        try {
            // the file to be opened for reading
            File inpFile = new File(fileName);
            Scanner sc = new Scanner(inpFile); // file to be scanned
            // returns true if there is another line to read
            while (sc.hasNextLine()) {
                //Need to add code here to process the input commands
                String curLine = sc.nextLine();
                String[] wordsArr = curLine.split(" ");
                allInputs.add(wordsArr);
            }
            sc.close(); // closes the scanner
        } catch (FileNotFoundException e) {
            System.out.println("Error : The file was not found");
        }
        return allInputs;
    }

    private static void processInput(String[] curLine) {
        // now process the inputs depending on the first line
        String curKey = curLine[1] + curLine[2];
        if(curLine[0].equals("LOAN")) {
            //creating a new Loan object
            Loan curLoan = new Loan(curLine);
            // the value of the key is derived by combining the name of the bank and the name of the borrower
            // adding the details of the current loan in the hashmap as the value corresponding to the aforementioned key
            loanMap.put(curKey, curLoan);
        }
        else if(curLine[0].equals("PAYMENT")) {
            // have to process what happens when a payment is made
            // first find the target Loan object corresponding to the current key
            Loan curLoan = loanMap.get(curKey);
            // now this is the loanMap corresponding to this object
            int paymentAmount = Integer.parseInt(curLine[3]);
            int emiMonth = Integer.parseInt(curLine[4]);
            curLoan.processPayment(paymentAmount,emiMonth);
        }
        else if(curLine[0].equals("BALANCE")) {
            Loan curLoan = loanMap.get(curKey);
            int emiMonth = Integer.parseInt(curLine[3]);
            curLoan.processBalance(emiMonth);
        }
    }
    public static void main(String[] args) {

        // setting the default value of the file name as input1.txt in case the user does not provide any input argument
        String fName = "input1.txt";
        if(args.length > 0){
            fName = args[0];
        }
        else {
            fName = "input1.txt";
            System.out.println("No command line arguments given, please run it as java Main.java inpName.txt");
        }
        ArrayList<String[]> allInpLines = readFile(fName);
        for(String[] inpLine  : allInpLines) {
            processInput(inpLine);
        }
    }
}
