package com.auca;

import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Scanner;

public class TaxDeclaration implements Identifiable, Validatable{
    private String declarationId;

    private TaxObligation obligation;
    private LocalDate filingDate;
    private BigDecimal taxableAmount;
    private BigDecimal declaredTax;
    private String status;

    public static TaxDeclaration[] declarations = new TaxDeclaration[100];
    public static int declarationCount = 0;

    
    public TaxDeclaration() {
    }


    public TaxDeclaration(String declarationId, TaxObligation obligation, LocalDate filingDate,
            BigDecimal taxableAmount, BigDecimal declaredTax, String status) {
        this.declarationId = declarationId;
        this.obligation = obligation;
        this.filingDate = filingDate;
        this.taxableAmount = taxableAmount;
        this.declaredTax = declaredTax;
        this.status = status;
    }


    


    public String getDeclarationId() {
        return declarationId;
    }


    public void setDeclarationId(String declarationId) {
        this.declarationId = declarationId;
    }


    public TaxObligation getObligation() {
        return obligation;
    }


    public void setObligation(TaxObligation obligation) {
        this.obligation = obligation;
    }


    public LocalDate getFilingDate() {
        return filingDate;
    }


    public void setFilingDate(LocalDate filingDate) {
        this.filingDate = filingDate;
    }


    public BigDecimal getTaxableAmount() {
        return taxableAmount;
    }


    public void setTaxableAmount(BigDecimal taxableAmount) {
        this.taxableAmount = taxableAmount;
    }


    public BigDecimal getDeclaredTax() {
        return declaredTax;
    }


    public void setDeclaredTax(BigDecimal declaredTax) {
        this.declaredTax = declaredTax;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public static TaxDeclaration[] getDeclarations() {
        return declarations;
    }


    public static void setDeclarations(TaxDeclaration[] declarations) {
        TaxDeclaration.declarations = declarations;
    }


    public static int getDeclarationCount() {
        return declarationCount;
    }


    public static void setDeclarationCount(int declarationCount) {
        TaxDeclaration.declarationCount = declarationCount;
    }


    @Override
    public String getDisplayName() {
        return declarationId + " - " + obligation.getTaxType().getTaxName();
    }

    @Override
    public String getId() {
        return declarationId;
    }

    @Override
    public boolean isActive() {
        return !getStatus().equals("REJECTED");
    }

    @Override
    public String getSummary() {
        
        return declarationId +
                " | Taxpayer: " + obligation.getTaxpayer().getDisplayName() +
                " | Tax Type: " + obligation.getTaxType().getTaxName() +
                " | Declared Tax: " + declaredTax +
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
        boolean isObligationFound = false;

        System.out.print("Enter Declaration ID: ");
        setDeclarationId(inputHelper.handleBlank(sc));

        if (TaxObligation.taxObligationsCount <= 0) {
            System.out.println("No tax obligations found!");
        }
        else {

            for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {
                System.out.println("Obligation ID: " + TaxObligation.taxObligations[i].getObligationId()
                        + " | Taxpayer: " + TaxObligation.taxObligations[i].getTaxpayer().getDisplayName()
                        + " | Tax Type: " + TaxObligation.taxObligations[i].getTaxType().getTaxName());
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
            
            for (int i = 0; i < declarationCount; i++) {
                if (declarations[i].getObligation().getObligationId()
                        .equals(getObligation().getObligationId())
                        && declarations[i].isActive()) {

                    System.out.println("An active declaration already exists for this obligation!");
                    return;
                }
            }
            
            System.out.print("Enter Filing Date (YYYY-MM-DD): ");
            setFilingDate(inputHelper.handleFilingDate(sc));
            if (getFilingDate().isAfter(getObligation().getDueDate())) {
                setStatus("LATE");
            } else {
                setStatus("SUBMITTED");
            }
           
            System.out.print("Enter Taxable Amount: ");
            setTaxableAmount(inputHelper.handleBigDecimal(sc));
            BigDecimal expectedTax = getTaxableAmount().multiply(getObligation().getTaxType().getRate()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

            System.out.println("Expected tax: "+expectedTax);
            System.out.print("Enter Declared Tax: ");
            setDeclaredTax(inputHelper.handleDeclaredTax(sc, expectedTax));

            
            

            if (declarationCount < declarations.length) {

                declarations[declarationCount] = new TaxDeclaration(
                        getDeclarationId(),
                        getObligation(),
                        getFilingDate(),
                        getTaxableAmount(),
                        getDeclaredTax(),
                        getStatus()
                );

                declarationCount++;

                System.out.println("Tax declaration saved successfully!");

            } else {
                System.out.println("Declaration storage is full!");
            }
    }
}

@Override
public String toString() {
    return "TaxDeclaration {" +
            "\nDeclaration ID: " + declarationId +
            "\nObligation ID: " + obligation.getObligationId() +
            "\nTaxpayer: " + obligation.getTaxpayer().getDisplayName() +
            "\nTaxpayer TIN: " + obligation.getTaxpayer().getTin() +
            "\nTax Type: " + obligation.getTaxType().getTaxName() +
            "\nFiling Date: " + filingDate +
            "\nTaxable Amount: " + taxableAmount +
            "\nDeclared Tax: " + declaredTax +
            "\nStatus: " + status +
            "\nJava Type: " + this.getClass().getSimpleName() +
            "\nAllowed range: " + declarations.length +
            "\n}";
}
    
    
}
