package com.auca;

import com.auca.interfaces.*;

import com.auca.exceptions.InputHelper;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.Scanner;


public class TaxClearance implements Validatable, Identifiable{
    private String clearanceId;
    private RegisteredTaxpayer taxpayer;
    private LocalDate requestDate;
    private LocalDate expiryDate;
    private String status;
    private RevenueOfficer approvedBy;
    public static TaxClearance[] clearances = new TaxClearance[100];
    public static int clearanceCount = 0;

    
    public TaxClearance() {
    }


    public TaxClearance(String clearanceId, RegisteredTaxpayer taxpayer, LocalDate requestDate, LocalDate expiryDate,
            String status, RevenueOfficer approvedBy) {
        this.clearanceId = clearanceId;
        this.taxpayer = taxpayer;
        this.requestDate = requestDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.approvedBy = approvedBy;
    }


    public String getClearanceId() {
        return clearanceId;
    }


    public void setClearanceId(String clearanceId) {
        this.clearanceId = clearanceId;
    }


    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }


    public void setTaxpayer(RegisteredTaxpayer taxpayer) {
        this.taxpayer = taxpayer;
    }


    public LocalDate getRequestDate() {
        return requestDate;
    }


    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }


    public LocalDate getExpiryDate() {
        return expiryDate;
    }


    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public RevenueOfficer getApprovedBy() {
        return approvedBy;
    }


    public void setApprovedBy(RevenueOfficer approvedBy) {
        this.approvedBy = approvedBy;
    }


    @Override
    public String getDisplayName() {
        return clearanceId + " - " + taxpayer.getDisplayName();
    }

    @Override
    public String getId() {
        return clearanceId;
    }

    @Override
    public boolean isActive() {
        return status.equalsIgnoreCase("APPROVED")
                && !expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public String getSummary() {
        
        return clearanceId +
                " | Taxpayer: " + taxpayer.getDisplayName() +
                " | TIN: " + taxpayer.getTin() +
                " | Request Date: " + requestDate +
                " | Expiry Date: " + expiryDate +
                " | Status: " + status;

        
        
        
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
        return "TaxClearance {" +
                "\nClearance ID: " + clearanceId +
                "\nTaxpayer: " + taxpayer.getDisplayName() +
                "\nTaxpayer TIN: " + taxpayer.getTin() +
                "\nRequest Date: " + requestDate +
                "\nExpiry Date: " + expiryDate +
                "\nStatus: " + status +
                "\nApproved By: " + (approvedBy == null ? "Not approved yet" : approvedBy.getDisplayName()) +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + clearances.length +
                "\n}";
    }

    public void save(Scanner sc, InputHelper inputHelper) {

    boolean isTaxpayerFound = false;

    
    if (RegisteredTaxpayer.taxpayerCount <= 0) {
        System.out.println("No taxpayers found!");
    }
    else {

        for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
            System.out.println("TIN: " + RegisteredTaxpayer.taxpayers[i].getTin()
                    + " | Taxpayer: " + RegisteredTaxpayer.taxpayers[i].getDisplayName());
        }

        do {
            System.out.print("Enter Taxpayer TIN: ");
            String tin = inputHelper.handleBlank(sc);

            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                if (RegisteredTaxpayer.taxpayers[i].getTin().equals(tin)) {
                    isTaxpayerFound = true;
                    setTaxpayer(RegisteredTaxpayer.taxpayers[i]);
                    break;
                }
            }

            if (!isTaxpayerFound) {
                System.out.println("Taxpayer not found. Try again.");
            }

        } while (!isTaxpayerFound);
        System.out.print("Enter Clearance ID: ");
        setClearanceId(inputHelper.handleBlank(sc));

        System.out.print("Enter Request Date (YYYY-MM-DD): ");
        setRequestDate(inputHelper.handleFilingDate(sc));

        System.out.print("Enter Expiry Date (YYYY-MM-DD): ");
        setExpiryDate(inputHelper.handleLocalDate(sc));

        setStatus("PENDING");
        setApprovedBy(null);

        if (clearanceCount < clearances.length) {

            clearances[clearanceCount] = new TaxClearance(
                    getClearanceId(),
                    getTaxpayer(),
                    getRequestDate(),
                    getExpiryDate(),
                    getStatus(),
                    getApprovedBy()
            );

            clearanceCount++;

            System.out.println("Tax clearance request saved successfully!");

        } else {
            System.out.println("Clearance storage is full!");
        }
    }
}


    


    



    



    

}