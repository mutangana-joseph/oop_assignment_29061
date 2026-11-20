package com.auca;

import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.*;

import java.math.BigDecimal;
import java.util.Scanner;

public class TaxType implements Identifiable, Validatable {

    private String taxTypeId;
    private String taxCode;
    private String taxName;
    private BigDecimal rate;
    private String filingFrequency;
    private boolean active;

    public static TaxType[] taxTypes = new TaxType[100];
    public static int taxTypeCount = 0;

    
    public TaxType() {
    }


    public TaxType(String taxTypeId, String taxCode, String taxName, BigDecimal rate, String filingFrequency,
            boolean active) {
        this.taxTypeId = taxTypeId;
        this.taxCode = taxCode;
        this.taxName = taxName;
        this.rate = rate;
        this.filingFrequency = filingFrequency;
        this.active = active;
    }


    


    public String getTaxTypeId() {
        return taxTypeId;
    }


    public void setTaxTypeId(String taxTypeId) {
        this.taxTypeId = taxTypeId;
    }


    public String getTaxCode() {
        return taxCode;
    }


    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }


    public String getTaxName() {
        return taxName;
    }


    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }


    public BigDecimal getRate() {
        return rate;
    }


    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }


    public String getFilingFrequency() {
        return filingFrequency;
    }


    public void setFilingFrequency(String filingFrequency) {
        this.filingFrequency = filingFrequency;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String getDisplayName() {
        // TODO Auto-generated method stub
        return taxName;
    }


    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return taxTypeId;
    }


    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return active;
    }


    @Override
    public String getSummary() {
        return taxName +
                " | Code: " + taxCode +
                " | Rate: " + rate + "%" +
                " | Frequency: " + filingFrequency +
                " | Active: " + active;
    }


    @Override
    public void printDetails() {
        System.out.println(this);
    }


    @Override
    public void validate() throws ValidationException {
        // TODO Auto-generated method stub
        
    }

    public void save(Scanner sc, InputHelper inputHelper) {

    int step = 1;
    boolean completed = false;

    while (step >= 1 && step <= 3 && !completed) {

        switch (step) {

            case 1:
                System.out.println("\n--- TAX TYPE IDENTITY ---");

                System.out.print("Tax Type ID: ");
                setTaxTypeId(inputHelper.handleBlank(sc));

                System.out.print("Tax Code: ");
                setTaxCode(inputHelper.handleBlank(sc).toUpperCase());

                System.out.print("Tax Name: ");
                setTaxName(inputHelper.handleTaxName(sc));

                
                break;

            case 2:
                System.out.println("\n--- TAX SETTINGS ---");

                System.out.print("Tax Rate (%): ");
                BigDecimal enteredRate;

                while (true) {
                    enteredRate = inputHelper.handleBigDecimal(sc);

                    if (enteredRate.compareTo(new BigDecimal("100")) <= 0) {
                        break;
                    }

                    System.out.print("Tax rate cannot exceed 100%. Try again: ");
                }

                setRate(enteredRate);

                System.out.print("Filing Frequency (MONTHLY, QUARTERLY, ANNUAL): ");

                while (true) {
                    String frequency = inputHelper.handleBlank(sc).toUpperCase();

                    if (frequency.equals("MONTHLY") ||
                            frequency.equals("QUARTERLY") ||
                            frequency.equals("ANNUAL")) {

                        setFilingFrequency(frequency);
                        break;
                    }

                    System.out.print("Enter MONTHLY, QUARTERLY or ANNUAL: ");
                }

                System.out.print("Is this tax type active? (true/false): ");
                setActive(inputHelper.handleBoolean(sc));
                break;

            case 3:
                System.out.println("\n--- REVIEW TAX TYPE ---");
                System.out.println(toString());

                System.out.println("\n[1] Save");
                System.out.println("[2] Edit");
                System.out.println("[0] Cancel");
                System.out.print("Choose an option: ");

                int choice = inputHelper.handleNonInt(sc);

                switch (choice) {

                    case 1:
                        if (taxTypeCount < taxTypes.length) {
                            taxTypes[taxTypeCount] = this;
                            taxTypeCount++;

                            completed = true;
                            System.out.println("Tax type saved successfully!");
                            break;
                        } else {
                            System.out.println("Tax type storage is full!");
                            
                        }
                        break;

                    case 2:
                        System.out.print("Enter step to edit (1 or 2): ");
                        int editStep = inputHelper.handleNonInt(sc);

                        if (editStep == 1 || editStep == 2) {
                            step = editStep;
                        } else {
                            System.out.println("Invalid step.");
                        }
                        break;

                    case 0:
                        completed = true;
                        System.out.println("Tax type registration cancelled.");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
                break;
        }
        if (step >= 1 && step <= 3) {

            String navigation = inputHelper.handleStepNavigation(sc, step);

            if (navigation.equals("NEXT")) {
                step++;
            } else if (navigation.equals("BACK")) {
                step--;
            } else if (navigation.equals("CANCEL")) {
                step = 0;
            }
        }
    }
}

@Override
public String toString() {
    return "TaxType {" +
            "\nTax Type ID: " + taxTypeId +
            "\nTax Code: " + taxCode +
            "\nTax Name: " + taxName +
            "\nRate: " + rate + "%" +
            "\nFiling Frequency: " + filingFrequency +
            "\nActive: " + active +
            "\nJava Type: " + this.getClass().getSimpleName() +
            "\nAllowed range: " + taxTypes.length +
            "\n}";
}
    

    
    
}
