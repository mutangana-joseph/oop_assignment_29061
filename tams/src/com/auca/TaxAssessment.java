package com.auca;


import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class TaxAssessment implements Identifiable, Validatable {
    private String assessmentId;
    private TaxDeclaration declaration;
    private BigDecimal assessedTax;
    private BigDecimal adjustmentAmount;
    private LocalDate assessmentDate;
    private String status;
    public static TaxAssessment[] assessments = new TaxAssessment[100];
    public static int assessmentCount = 0;
    

    public TaxAssessment() {
    }


    public TaxAssessment(String assessmentId, TaxDeclaration declaration, BigDecimal assessedTax,
            BigDecimal adjustmentAmount, LocalDate assessmentDate, String status) {
        this.assessmentId = assessmentId;
        this.declaration = declaration;
        this.assessedTax = assessedTax;
        this.adjustmentAmount = adjustmentAmount;
        this.assessmentDate = assessmentDate;
        this.status = status;
    }


    public String getAssessmentId() {
        return assessmentId;
    }


    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }


    public TaxDeclaration getDeclaration() {
        return declaration;
    }


    public void setDeclaration(TaxDeclaration declaration) {
        this.declaration = declaration;
    }


    public BigDecimal getAssessedTax() {
        return assessedTax;
    }


    public void setAssessedTax(BigDecimal assessedTax) {
        this.assessedTax = assessedTax;
    }


    public BigDecimal getAdjustmentAmount() {
        return adjustmentAmount;
    }


    public void setAdjustmentAmount(BigDecimal adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }


    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }


    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String getDisplayName() {
        return assessmentId + " - " + declaration.getObligation().getTaxType().getTaxName();
    }

    @Override
    public String getId() {
        return assessmentId;
    }

    @Override
    public boolean isActive() {
        return status.equalsIgnoreCase("APPROVED");
    }

    @Override
    public String getSummary() {
        
        return assessmentId +
                " | Taxpayer: " + declaration.getObligation().getTaxpayer().getDisplayName() +
                " | Tax Type: " + declaration.getObligation().getTaxType().getTaxName() +
                " | Assessed Tax: " + assessedTax +
                " | Status: " + status;
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public void validate() throws ValidationException {

    }

    public void save(Scanner sc, InputHelper inputHelper) {

    boolean isDeclarationFound = false;

    System.out.print("Enter Assessment ID: ");
    setAssessmentId(inputHelper.handleBlank(sc));

    if (TaxDeclaration.declarationCount <= 0) {
        System.out.println("No tax declarations found!");
    }
    else {

        for (int i = 0; i < TaxDeclaration.declarationCount; i++) {
            System.out.println("Declaration ID: " + TaxDeclaration.declarations[i].getDeclarationId()
                    + " | Taxpayer: " + TaxDeclaration.declarations[i].getObligation().getTaxpayer().getDisplayName()
                    + " | Tax Type: " + TaxDeclaration.declarations[i].getObligation().getTaxType().getTaxName()
                    + " | Declared Tax: " + TaxDeclaration.declarations[i].getDeclaredTax());
        }

        do {
            System.out.print("Enter Declaration ID: ");
            String declarationId = inputHelper.handleBlank(sc);

            for (int i = 0; i < TaxDeclaration.declarationCount; i++) {
                if (TaxDeclaration.declarations[i].getDeclarationId().equals(declarationId)) {
                    isDeclarationFound = true;
                    setDeclaration(TaxDeclaration.declarations[i]);
                    
                }
            }

            if (!isDeclarationFound) {
                System.out.println("Declaration not found. Try again.");
            }

        } while (!isDeclarationFound);

        if(!getDeclaration().isActive()){
            System.out.println("Can not assess a rejected tax declaration.");
            return;
        }
        System.out.print("Enter Assessed Tax: ");
        setAssessedTax(inputHelper.handleBigDecimal(sc));

        System.out.print("Enter Adjustment Amount: ");
        setAdjustmentAmount(inputHelper.handleAdjustmentAmount(sc));

        System.out.print("Enter Assessment Date (YYYY-MM-DD): ");
        setAssessmentDate(inputHelper.handleLocalDate(sc));
        BigDecimal assessmentTotal = getAssessedTax().add(getAdjustmentAmount());
        if (assessmentTotal.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("Assessment total cannot be negative!");
            return;
        }

       
        setStatus("DRAFT");

        if (assessmentCount < assessments.length) {

            assessments[assessmentCount] = new TaxAssessment(
                    getAssessmentId(),
                    getDeclaration(),
                    getAssessedTax(),
                    getAdjustmentAmount(),
                    getAssessmentDate(),
                    getStatus()
            );

            assessmentCount++;

            System.out.println("Tax assessment saved successfully!");

        } else {
            System.out.println("Assessment storage is full!");
        }
    }
}

    @Override
    public String toString() {
        return "TaxAssessment {" +
                "\nAssessment ID: " + assessmentId +
                "\nDeclaration ID: " + declaration.getDeclarationId() +
                "\nTaxpayer: " + declaration.getObligation().getTaxpayer().getDisplayName() +
                "\nTaxpayer TIN: " + declaration.getObligation().getTaxpayer().getTin() +
                "\nTax Type: " + declaration.getObligation().getTaxType().getTaxName() +
                "\nDeclared Tax: " + declaration.getDeclaredTax() +
                "\nAssessed Tax: " + assessedTax +
                "\nAdjustment Amount: " + adjustmentAmount +
                "\nAssessment Date: " + assessmentDate +
                "\nStatus: " + status +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + assessments.length +
                "\n}";
}

    

    

    
    
}
