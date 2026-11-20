package com.auca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Validatable;

import com.auca.exceptions.InputHelper;



public class TaxRefund implements  Validatable, Identifiable{
    private String refundId;
    private RegisteredTaxpayer taxpayer;
    private TaxAssessment assessment;
    private BigDecimal requestedAmount;
    private LocalDate requestDate;
    private String status;
    public static TaxRefund[] refunds = new TaxRefund[100];
    public static int refundCount = 0;

    public TaxRefund() {
       
    }

    
  
    public TaxRefund(String refundId, RegisteredTaxpayer taxpayer, TaxAssessment assessment, BigDecimal requestedAmount,
            LocalDate requestDate, String status) {
        this.refundId = refundId;
        this.taxpayer = taxpayer;
        this.assessment = assessment;
        this.requestedAmount = requestedAmount;
        this.requestDate = requestDate;
        this.status = status;
    }



    public String getRefundId() {
        return refundId;
    }
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }
    public void setTaxpayer(RegisteredTaxpayer taxpayer) {
        this.taxpayer = taxpayer;
    }
    public TaxAssessment getAssessment() {
        return assessment;
    }
    public void setAssessment(TaxAssessment assessment) {
        this.assessment = assessment;
    }
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }
    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
    public LocalDate getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String getDisplayName() {
        return refundId + " - " + taxpayer.getDisplayName();
    }

    @Override
    public String getId() {
        return refundId;
    }

    @Override
    public boolean isActive() {
        return status.equalsIgnoreCase("PENDING");
    }

    @Override
    public String getSummary() {
        return refundId +
                " | Taxpayer: " + taxpayer.getDisplayName() +
                " | Requested Amount: " + requestedAmount +
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
        return "TaxRefund {" +
                "\nRefund ID: " + refundId +
                "\nTaxpayer: " + taxpayer.getDisplayName() +
                "\nTaxpayer TIN: " + taxpayer.getTin() +
                "\nAssessment ID: " + assessment.getAssessmentId() +
                "\nAssessed Tax: " + assessment.getAssessedTax() +
                "\nRequested Amount: " + requestedAmount +
                "\nRequest Date: " + requestDate +
                "\nStatus: " + status +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + refunds.length +
                "\n}";
    }



    public void save(Scanner sc, InputHelper inputHelper) {

    boolean isTaxpayerFound = false;
    boolean isAssessmentFound = false;

    System.out.print("Enter Refund ID: ");
    setRefundId(inputHelper.handleBlank(sc));

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

        if (TaxAssessment.assessmentCount <= 0) {
            System.out.println("No tax assessments found!");
        }
        else{
            boolean hasAssessment = false;

            for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                if (TaxAssessment.assessments[i].getDeclaration().getObligation()
                        .getTaxpayer().getTin().equals(getTaxpayer().getTin())) {

                    hasAssessment = true;

                    System.out.println("Assessment ID: " + TaxAssessment.assessments[i].getAssessmentId()
                            + " | Assessed Tax: " + TaxAssessment.assessments[i].getAssessedTax());
                }
            }

            if (!hasAssessment) {
                System.out.println("No tax assessment found for this taxpayer!");
            } 
            else {

                do {
                    System.out.print("Enter Assessment ID: ");
                    String assessmentId = inputHelper.handleBlank(sc);

                    for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                        if (TaxAssessment.assessments[i].getAssessmentId().equals(assessmentId)
                                && TaxAssessment.assessments[i].getDeclaration().getObligation()
                                        .getTaxpayer().getTin().equals(getTaxpayer().getTin())) {

                            isAssessmentFound = true;
                            setAssessment(TaxAssessment.assessments[i]);
                            break;
                        }
                    }

                    if (!isAssessmentFound) {
                        System.out.println("Assessment not found for this taxpayer. Try again.");
                    }

                } while (!isAssessmentFound);

                System.out.print("Enter Requested Amount: ");
                setRequestedAmount(inputHelper.handleBigDecimal(sc));

                System.out.print("Enter Request Date (YYYY-MM-DD): ");
                setRequestDate(inputHelper.handleFilingDate(sc));

                System.out.print("Enter Status: ");
                setStatus(inputHelper.handleBlank(sc));

                if (refundCount < refunds.length) {

                    refunds[refundCount] = new TaxRefund(
                            getRefundId(),
                            getTaxpayer(),
                            getAssessment(),
                            getRequestedAmount(),
                            getRequestDate(),
                            getStatus());

                    refundCount++;

                    System.out.println("Tax refund saved successfully!");

                } else {
                    System.out.println("Refund storage is full!");
                }
            }

        }

        
    }
}

    

    
    
}
