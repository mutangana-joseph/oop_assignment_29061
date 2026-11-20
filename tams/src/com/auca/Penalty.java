package com.auca;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;

import com.auca.exceptions.InputHelper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Penalty implements Validatable, Identifiable{

    private String penaltyId;
    private TaxObligation obligation;
    private String penaltyType;
    private BigDecimal baseAmount;
    private BigDecimal penaltyRate;
    private BigDecimal penaltyAmount;

    public static Penalty[] penalties = new Penalty[100];
    public static int penaltyCount = 0;


    
    public Penalty() {
    }
    public Penalty(String penaltyId, TaxObligation obligation, String penaltyType, BigDecimal baseAmount,
            BigDecimal penaltyRate, BigDecimal penaltyAmount) {
        this.penaltyId = penaltyId;
        this.obligation = obligation;
        this.penaltyType = penaltyType;
        this.baseAmount = baseAmount;
        this.penaltyRate = penaltyRate;
        this.penaltyAmount = penaltyAmount;
    }
    public String getPenaltyId() {
        return penaltyId;
    }
    public void setPenaltyId(String penaltyId) {
        this.penaltyId = penaltyId;
    }
    public TaxObligation getObligation() {
        return obligation;
    }
    public void setObligation(TaxObligation obligation) {
        this.obligation = obligation;
    }
    public String getPenaltyType() {
        return penaltyType;
    }
    public void setPenaltyType(String penaltyType) {
        this.penaltyType = penaltyType;
    }
    public BigDecimal getBaseAmount() {
        return baseAmount;
    }
    public void setBaseAmount(BigDecimal baseAmount) {
        this.baseAmount = baseAmount;
    }
    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }
    public void setPenaltyRate(BigDecimal penaltyRate) {
        this.penaltyRate = penaltyRate;
    }
    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }
    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    @Override
    public String getDisplayName() {
        return penaltyType + " - " + obligation.getObligationId();
    }

    @Override
    public String getId() {
        return penaltyId;
    }

    @Override
    public boolean isActive() {
        return obligation.getTaxpayer().isActive();
    }

    @Override
    public String getSummary() {
        return penaltyId +
                " | Penalty Type: " + penaltyType +
                " | Taxpayer: " + obligation.getTaxpayer().getDisplayName() +
                " | Base Amount: " + baseAmount +
                " | Penalty Amount: " + penaltyAmount;
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public void validate() throws ValidationException {

    }

    @Override
    public String toString() {
        return "Penalty {" +
                "\nPenalty ID: " + penaltyId +
                "\nObligation ID: " + obligation.getObligationId() +
                "\nTaxpayer: " + obligation.getTaxpayer().getDisplayName() +
                "\nTaxpayer TIN: " + obligation.getTaxpayer().getTin() +
                "\nTax Type: " + obligation.getTaxType().getTaxName() +
                "\nPenalty Type: " + penaltyType +
                "\nBase Amount: " + baseAmount +
                "\nPenalty Rate: " + penaltyRate +
                "\nPenalty Amount: " + penaltyAmount +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + penalties.length +
                "\n}";
    }
    
    

    public void save(Scanner sc, InputHelper inputHelper){

    boolean isObligationFound = false;

    System.out.print("Enter Penalty ID: ");
    setPenaltyId(inputHelper.handleBlank(sc));

    if (TaxObligation.taxObligationsCount <= 0) {
        System.out.println("No tax obligations found!");
    }
    else {

        for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {
            System.out.println("Obligation ID: " + TaxObligation.taxObligations[i].getObligationId()
                    + " | Taxpayer: " + TaxObligation.taxObligations[i].getTaxpayer().getDisplayName()
                    + " | Tax Type: " + TaxObligation.taxObligations[i].getTaxType().getTaxName()
                    + " | Due Date: " + TaxObligation.taxObligations[i].getDueDate());
        }

        do {
            System.out.print("Enter Obligation ID: ");
            String obligationId = inputHelper.handleBlank(sc);

            for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {
                if (TaxObligation.taxObligations[i].getObligationId().equals(obligationId)) {
                    isObligationFound = true;
                    setObligation(TaxObligation.taxObligations[i]);
                    break;
                }
            }

            if (!isObligationFound) {
                System.out.println("Obligation not found. Try again.");
            }

        } while (!isObligationFound);

        System.out.print("Enter Penalty Type: ");
        setPenaltyType(inputHelper.handleBlank(sc));

        System.out.print("Enter Base Amount: ");
        setBaseAmount(inputHelper.handleBigDecimal(sc));

        System.out.print("Enter Penalty Rate: ");
        setPenaltyRate(inputHelper.handleBigDecimal(sc));

        System.out.print("Enter Penalty Amount: ");
        setPenaltyAmount(inputHelper.handleBigDecimal(sc));

        if (penaltyCount < penalties.length) {

            penalties[penaltyCount] = new Penalty(
                    getPenaltyId(),
                    getObligation(),
                    getPenaltyType(),
                    getBaseAmount(),
                    getPenaltyRate(),
                    getPenaltyAmount()
            );

            penaltyCount++;

            System.out.println("Penalty saved successfully!");

        } else {
            System.out.println("Penalty storage is full!");
        }
    }
    

    
}



    

    
    
}
