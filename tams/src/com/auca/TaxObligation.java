package com.auca;

import java.time.LocalDate;

import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.abstracts.TaxParty;
import com.auca.exceptions.ValidationException;
import com.auca.exceptions.InputHelper;
import java.util.Scanner;
import com.auca.interfaces.*;

public class TaxObligation implements Identifiable, Validatable {

    

    private String obligationId;
    private RegisteredTaxpayer taxpayer;
    private TaxType taxType;
    private LocalDate periodStart;
    private LocalDate periodEnd;
    private LocalDate dueDate;

    public static TaxObligation[] taxObligations = new TaxObligation[100];
    public static int taxObligationsCount=0;

    public TaxObligation() {
    } 
    
    
    public TaxObligation(String obligationId, RegisteredTaxpayer taxpayer, TaxType taxType, LocalDate periodStart,
            LocalDate periodEnd, LocalDate dueDate) {
        this.obligationId = obligationId;
        this.taxpayer = taxpayer;
        this.taxType = taxType;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.dueDate = dueDate;
    }


    public String getObligationId() {
        return obligationId;
    }

    public void setObligationId(String obligationId) {
        this.obligationId = obligationId;
    }

    public RegisteredTaxpayer getTaxpayer() {
        return taxpayer;
    }

    public void setTaxpayer(RegisteredTaxpayer taxpayer) {
        this.taxpayer = taxpayer;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String getDisplayName() {
        return taxpayer.getDisplayName() + " - " + taxType.getTaxName();
    }

    @Override
    public String getId() {
        return obligationId;
    }

    @Override
    public boolean isActive() {
        return taxpayer.isActive();
    }

    @Override
    public String getSummary() {
        return obligationId +
                " | Taxpayer: " + taxpayer.getDisplayName() +
                " | TIN: " + taxpayer.getTin() +
                " | Tax Type: " + taxType.getTaxName() +
                " | Due Date: " + dueDate;
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }



    @Override
    public void validate() {
        
        
    }

    public void save(Scanner sc, InputHelper inputHelper){

        boolean isTaxPayerfound = false;
        
        do{
            System.out.print("Enter TIN number: ");
            String tin = inputHelper.handleTin(sc);

            if(RegisteredTaxpayer.taxpayerCount <=0){
                System.out.println("Taxpayers list is empty!");
                break;
            }


            
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                if (RegisteredTaxpayer.taxpayers[i].getTin().equals(tin)) {
                    isTaxPayerfound = true;
                    setTaxpayer(RegisteredTaxpayer.taxpayers[i]);
                    break;
                }
            }

            if(!isTaxPayerfound){
                System.out.print("TaxPayer not found. Try gain: ");
            }
            
      
        }while(!isTaxPayerfound);



        if (isTaxPayerfound) {

            if(getTaxpayer().isActive()){
                boolean isTaxTypefound = false;
                System.out.print("Enter Obligation ID: ");
                setObligationId(inputHelper.handleExistingObligationId(sc, inputHelper));

                if (TaxType.taxTypeCount <= 0) {
                    System.out.println("No tax type found");
                } else {

                    if (taxObligationsCount < TaxObligation.taxObligations.length) {
                        for (int i = 0; i < TaxType.taxTypeCount; i++) {
                            System.out.println("TaxType Id: " + TaxType.taxTypes[i].getTaxTypeId() + " | TaxType name:"
                                    + TaxType.taxTypes[i].getTaxName() + " | Tax rate: "
                                    + TaxType.taxTypes[i].getRate());
                        }
                        do {
                            System.out.print("Enter Tax Type ID: ");
                            String taxTypeId = inputHelper.handleBlank(sc);
                            for (int i = 0; i < TaxType.taxTypeCount; i++) {
                                if (TaxType.taxTypes[i].getTaxTypeId().equals(taxTypeId)) {
                                    isTaxTypefound = true;
                                    setTaxType(TaxType.taxTypes[i]);
                                    break;

                                }
                                
                            }

                            if(!isTaxTypefound){
                                System.out.print("Invalid Id. Tray again: ");
                            }
                           

                        } while (!isTaxTypefound);

                        if(getTaxType().isActive()){
                        
                            System.out.print("Enter Period Start (YYYY-MM-DD): ");
                            setPeriodStart(inputHelper.handleLocalDate(sc));

                            System.out.print("Enter Period End (YYYY-MM-DD): ");
                            setPeriodEnd(inputHelper.handleDateBefore(sc, getPeriodStart()));

                            // Check for overlapping obligations
                            boolean isOverlapping = false;

                            for (int i = 0; i < taxObligationsCount; i++) {

                                TaxObligation existing = taxObligations[i];

                                boolean sameTaxpayer = existing.getTaxpayer().getTin()
                                        .equals(getTaxpayer().getTin());

                                boolean sameTaxType = existing.getTaxType().getTaxTypeId()
                                        .equals(getTaxType().getTaxTypeId());

                                boolean datesOverlap =
                                        (getPeriodStart().isBefore(existing.getPeriodEnd()) ||
                                        getPeriodStart().isEqual(existing.getPeriodEnd()))
                                        &&
                                        (getPeriodEnd().isAfter(existing.getPeriodStart()) ||
                                        getPeriodEnd().isEqual(existing.getPeriodStart()));

                                if (sameTaxpayer && sameTaxType && datesOverlap) {
                                    isOverlapping = true;
                                    break;
                                }
                            }

                            if (isOverlapping) {
                                System.out.println(
                                        "An overlapping obligation already exists for this taxpayer and tax type.");
                                return;
                            }

                            System.out.print("Enter Due Date (YYYY-MM-DD): ");
                            setDueDate(inputHelper.handleDateBefore(sc, getPeriodEnd()));

                            taxObligations[taxObligationsCount] = new TaxObligation(
                                    getObligationId(), getTaxpayer(), getTaxType(),
                                    getPeriodStart(), getPeriodEnd(), getDueDate());

                            taxObligationsCount++;

                            System.out.println("Tax obligation saved successfully");

                        }
                        else{
                            System.out.println("Entered Tax Type is not active");
                        }
                        
                    } else {
                        System.out.println("Tax obligations storage is full");
                    }

                }



            }
            else{
                System.out.println("Taxpayer not active");
            }
            
            
            
        }
        
        isTaxPayerfound=false;


    }

    @Override
    public String toString() {

        return "TaxObligation {" +
                "\nObligation ID: " + obligationId +
                "\nTaxpayer: " + taxpayer.getDisplayName() +
                "\nTaxpayer TIN: " + taxpayer.getTin() +
                "\nTax Type: " + taxType.getTaxName() +
                "\nTax Type Code: " + taxType.getTaxCode() +
                "\nPeriod Start: " + periodStart +
                "\nPeriod End: " + periodEnd +
                "\nDue Date: " + dueDate +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + taxObligations.length +
                "\n}";
    }








    

    
    
    
}
