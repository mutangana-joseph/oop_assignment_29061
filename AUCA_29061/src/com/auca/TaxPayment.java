package com.auca;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;

import com.auca.exceptions.InputHelper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class TaxPayment implements Validatable, Identifiable{
    private String paymentId;
    private TaxAssessment assessment;
    private String paymentReference;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;

    public static TaxPayment[] taxPayments = new TaxPayment[100];
    public static int taxPaymentsCount =0;
    
    public TaxPayment() {
    }
    public TaxPayment(String paymentId, TaxAssessment assessment, String paymentReference, BigDecimal amount,
            LocalDate paymentDate, String paymentMethod) {
        this.paymentId = paymentId;
        this.assessment = assessment;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
    public TaxAssessment getAssessment() {
        return assessment;
    }
    public void setAssessment(TaxAssessment assessment) {
        this.assessment = assessment;
    }
    public String getPaymentReference() {
        return paymentReference;
    }
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
   
    @Override
    public String getDisplayName() {
        return paymentReference;
    }

    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public boolean isActive() {
        return assessment != null;
    }

    @Override
    public String getSummary() {
        return paymentId +
                " | Payment Reference: " + paymentReference +
                " | Taxpayer: " + assessment.getDeclaration().getObligation().getTaxpayer().getDisplayName() +
                " | Amount: " + amount +
                " | Payment Method: " + paymentMethod;
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
        return "TaxPayment {" +
                "\nPayment ID: " + paymentId +
                "\nAssessment ID: " + assessment.getAssessmentId() +
                "\nTaxpayer: " + assessment.getDeclaration().getObligation().getTaxpayer().getDisplayName() +
                "\nTaxpayer TIN: " + assessment.getDeclaration().getObligation().getTaxpayer().getTin() +
                "\nTax Type: " + assessment.getDeclaration().getObligation().getTaxType().getTaxName() +
                "\nPayment Reference: " + paymentReference +
                "\nAmount: " + amount +
                "\nPayment Date: " + paymentDate +
                "\nPayment Method: " + paymentMethod +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + taxPayments.length +
                "\n}";
    }

    public void save(Scanner sc, InputHelper inputHelper) {

    boolean isAssessmentFound = false;

    if (TaxAssessment.assessmentCount <= 0) {
        System.out.println("No tax assessments found!");
    }
    else {

        for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
            System.out.println("Assessment ID: " + TaxAssessment.assessments[i].getAssessmentId()
                    + " | Taxpayer: " + TaxAssessment.assessments[i].getDeclaration().getObligation().getTaxpayer().getDisplayName()
                    + " | Assessed Tax: " + TaxAssessment.assessments[i].getAssessedTax()
                    + " | Status: " + TaxAssessment.assessments[i].getStatus());
        }

        do {
            System.out.print("Enter Assessment ID: ");
            String assessmentId = inputHelper.handleBlank(sc);

            for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                if (TaxAssessment.assessments[i].getAssessmentId().equals(assessmentId)) {
                    isAssessmentFound = true;
                    setAssessment(TaxAssessment.assessments[i]);
                    break;
                }
            }

            if (!isAssessmentFound) {
                System.out.println("Assessment not found. Try again.");
            }

        } while (!isAssessmentFound);

        if (!getAssessment().getStatus().equals("CONFIRMED")) {
            System.out.println("Payment is only allowed for confirmed assessments!");
            return;
        }
        
        System.out.print("Enter Payment ID: ");
        setPaymentId(inputHelper.handleBlank(sc));
        System.out.print("Enter Payment Reference: ");
        setPaymentReference(inputHelper.handleExistingPaymentReference(sc, inputHelper));
       
        BigDecimal assessmentTotal = getAssessment().getAssessedTax()
                .add(getAssessment().getAdjustmentAmount());

        BigDecimal totalPenalties = BigDecimal.ZERO;

        if (Penalty.penaltyCount > 0) {

            for (int i = 0; i < Penalty.penaltyCount; i++) {

                if (Penalty.penalties[i].getObligation().getObligationId()
                        .equals(getAssessment().getDeclaration().getObligation().getObligationId())) {

                    totalPenalties = totalPenalties.add(Penalty.penalties[i].getPenaltyAmount());
                }
            }
        }

        BigDecimal totalPayments = BigDecimal.ZERO;

        if (taxPaymentsCount > 0) {

            for (int i = 0; i < taxPaymentsCount; i++) {

                if (taxPayments[i].getAssessment().getAssessmentId()
                        .equals(getAssessment().getAssessmentId())) {

                    totalPayments = totalPayments.add(taxPayments[i].getAmount());
                }
            }
        }

        BigDecimal outstandingBalance = assessmentTotal.add(totalPenalties).subtract(totalPayments);
        System.out.print("Enter Payment Amount: ");
        setAmount(inputHelper.handlePaymentWithinBalance(sc, outstandingBalance));

        
        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        setPaymentDate(inputHelper.handleFilingDate(sc));

           

        System.out.println("Payment Methods: CASH | BANK TRANSFER | AIRTEL MONEY | MOBILE MONEY");
        System.out.print("Enter Payment Method: ");
        setPaymentMethod(inputHelper.handlePaymentMethod(sc));

        if (taxPaymentsCount < taxPayments.length) {

            taxPayments[taxPaymentsCount] = new TaxPayment(
                    getPaymentId(),
                    getAssessment(),
                    getPaymentReference(),
                    getAmount(),
                    getPaymentDate(),
                    getPaymentMethod()
            );

            taxPaymentsCount++;

            System.out.println("Tax payment saved successfully!");

        } else {
            System.out.println("Payment storage is full!");
        }
    }
}

    

    

    
    
}
