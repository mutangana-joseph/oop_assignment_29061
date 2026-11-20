package com.auca.main;
import java.math.BigDecimal;
import java.util.Scanner;

import com.auca.CorporateTaxpayer;
import com.auca.IndividualTaxpayer;
import com.auca.Penalty;
import com.auca.RevenueOfficer;
import com.auca.TaxAssessment;
import com.auca.TaxClearance;
import com.auca.TaxDeclaration;
import com.auca.TaxObligation;
import com.auca.TaxPayment;
import com.auca.TaxRefund;
import com.auca.TaxType;
import com.auca.abstracts.TaxParty;
import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;

import com.auca.interfaces.Identifiable;

import com.auca.abstracts.RegisteredTaxpayer;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        InputHelper inputHelper = new InputHelper();
        
        
        TaxObligation taxObligation = new TaxObligation();
        TaxDeclaration taxDeclaration = new TaxDeclaration();
        TaxAssessment taxAssessment = new TaxAssessment();
        TaxPayment taxPayment = new TaxPayment();
        Penalty penalty = new Penalty();
        TaxRefund taxRefund = new TaxRefund();
        TaxClearance taxClearance = new TaxClearance();
        
        
        TaxParty[] taxpayers = new TaxParty[100];
        int taxpayerCount = 0;
        int choice;

        do {
            System.out.println();
            System.out.println("========== TAX ADMINISTRATION MANAGEMENT ==========");
            System.out.println("1. Register Individual Taxpayer");
            System.out.println("2. Register Corporate Taxpayer");
            System.out.println("3. Register Revenue Officer");
            System.out.println("4. Register Tax Type");
            System.out.println("5. Create Tax Obligation");
            System.out.println("6. File Tax Declaration");
            System.out.println("7. Assess Declaration");
            System.out.println("8. Confirm Assessment");
            System.out.println("9. View All Records");
            System.out.println("10. View Declaration History");
            System.out.println("11. View Assessment History");
            System.out.println("12. Record Tax Payment");
            System.out.println("13. Calculate / Record Penalty");
            System.out.println("14. Request / Approve Refund");
            System.out.println("15. Request / Issue Tax Clearance");
            System.out.println("16. Search Record by ID / TIN");
            System.out.println("17. Update Eligible Record");
            System.out.println("18. View Taxpayer Statement");
            System.out.println("19. View Outstanding Balances");
            System.out.println("20. View Tax Reports");
            System.out.println("21. Demonstrate Polymorphism");
            System.out.println("0. Exit");
            System.out.println("====================================================");

            System.out.print("Select option: ");

        
            choice = inputHelper.handleNonInt(sc);

            switch (choice) {

                case 1:
                    IndividualTaxpayer individual = new IndividualTaxpayer();
                    try {
                        individual.save(sc, inputHelper);

                    } catch (ValidationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    CorporateTaxpayer corporateTaxpayer = new CorporateTaxpayer();
                    corporateTaxpayer.save(sc, inputHelper);
                    break;

                case 3:
                    RevenueOfficer revenueOfficer = new RevenueOfficer();
                    revenueOfficer.save(sc, inputHelper);
                    break;
                case 4:
                    TaxType taxType = new TaxType();
                    taxType.save(sc, inputHelper);
            
                    break;

                case 5:
                    taxObligation.save(sc, inputHelper);
                    break;
                case 6:
                    taxDeclaration.save(sc, inputHelper);
                    break;
                case 7:
                    taxAssessment.save(sc, inputHelper);
                    break;
                case 9:
                    viewRecords();
                    break;
                case 10:
                    viewDeclarationHistory();
                    break;
                case 11:
                    viewAssessmentHistory();
                    break;
                case 12:
                    taxPayment.save(sc, inputHelper);
                    break;
                case 13:
                    penalty.save(sc, inputHelper);
                    break;
                case 14:
                    taxRefund.save(sc, inputHelper);
                    break;
                case 15:
                    taxClearance.save(sc, inputHelper);
                    break;
                case 16:
                    searchRecord(sc, inputHelper);
                    break;
                case 18:
                    viewTaxPayerStatement(sc, inputHelper);
                    break;
                case 21:
                    demonstratePolymorphism();
                    break;
                case 0:
                    System.out.println("Exiting TAMS...");
                    break;

                default:
                    System.out.println("Invalid option. Try again!");
            }

        } while (choice != 0);

        sc.close();
    }


    public static void viewRecords() {

        System.out.println("\n========== ALL REGISTERED RECORDS ==========");

        // Taxpayers
        System.out.println("\n--- TAXPAYERS ---");
        if (RegisteredTaxpayer.taxpayerCount > 0) {
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                System.out.println(RegisteredTaxpayer.taxpayers[i]);
            }
        } else {
            System.out.println("No taxpayer records found.");
        }

        // Revenue Officers
        System.out.println("\n--- REVENUE OFFICERS ---");
        if (RevenueOfficer.officerCount > 0) {
            for (int i = 0; i < RevenueOfficer.officerCount; i++) {
                System.out.println(RevenueOfficer.officers[i]);
            }
        } else {
            System.out.println("No revenue officer records found.");
        }

        // Tax Types
        System.out.println("\n--- TAX TYPES ---");
        if (TaxType.taxTypeCount > 0) {
            for (int i = 0; i < TaxType.taxTypeCount; i++) {
                System.out.println(TaxType.taxTypes[i]);
            }
        } else {
            System.out.println("No tax type records found.");
        }

        // Tax Obligations
        System.out.println("\n--- TAX OBLIGATIONS ---");
        if (TaxObligation.taxObligationsCount > 0) {
            for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {
                System.out.println(TaxObligation.taxObligations[i]);
            }
        } else {
            System.out.println("No tax obligation records found.");
        }

        // Tax Declarations
        System.out.println("\n--- TAX DECLARATIONS ---");
        if (TaxDeclaration.declarationCount > 0) {
            for (int i = 0; i < TaxDeclaration.declarationCount; i++) {
                System.out.println(TaxDeclaration.declarations[i]);
            }
        } else {
            System.out.println("No tax declaration records found.");
        }

        // Tax Assessments
        System.out.println("\n--- TAX ASSESSMENTS ---");
        if (TaxAssessment.assessmentCount > 0) {
            for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                System.out.println(TaxAssessment.assessments[i]);
            }
        } else {
            System.out.println("No tax assessment records found.");
        }

        // Tax Payments
        System.out.println("\n--- TAX PAYMENTS ---");
        if (TaxPayment.taxPaymentsCount > 0) {
            for (int i = 0; i < TaxPayment.taxPaymentsCount; i++) {
                System.out.println(TaxPayment.taxPayments[i]);
            }
        } else {
            System.out.println("No tax payment records found.");
        }

        // Penalties
        System.out.println("\n--- PENALTIES ---");
        if (Penalty.penaltyCount > 0) {
            for (int i = 0; i < Penalty.penaltyCount; i++) {
                System.out.println(Penalty.penalties[i]);
            }
        } else {
            System.out.println("No penalty records found.");
        }

        // Tax Refunds
        System.out.println("\n--- TAX REFUNDS ---");
        if (TaxRefund.refundCount > 0) {
            for (int i = 0; i < TaxRefund.refundCount; i++) {
                System.out.println(TaxRefund.refunds[i]);
            }
        } else {
            System.out.println("No tax refund records found.");
        }

        // Tax Clearances
        System.out.println("\n--- TAX CLEARANCES ---");
        if (TaxClearance.clearanceCount > 0) {
            for (int i = 0; i < TaxClearance.clearanceCount; i++) {
                System.out.println(TaxClearance.clearances[i]);
            }
        } else {
            System.out.println("No tax clearance records found.");
        }

        System.out.println("\n============================================");
    }
    
    public static void viewTaxPayerStatement() {
        if (RegisteredTaxpayer.taxpayerCount > 0) {
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                System.out.println(RegisteredTaxpayer.taxpayers[i].getSummary());
            }
        } else {
            System.out.println("No taxpayers statements found");
        }
    }

    public static void searchRecord(Scanner sc, InputHelper inputHelper){

        boolean isFound=false;
        if (RegisteredTaxpayer.taxpayerCount > 0) {
            System.out.print("Enter ID / TIN: ");
            String value=inputHelper.handleTin(sc);
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                if(RegisteredTaxpayer.taxpayers[i].getTin().equals(value)){
                    System.out.println(RegisteredTaxpayer.taxpayers[i]);
                    isFound=true;

                }    
            }
            if(!isFound){
                System.out.println("No record matching the ID / TIN found");

            }
           

        } else {
            System.out.println("No taxpayers found");
        }
        

    }

    public static void viewDeclarationHistory() {

        System.out.println("\n========== DECLARATION HISTORY ==========");

        if (TaxDeclaration.declarationCount > 0) {

            for (int i = 0; i < TaxDeclaration.declarationCount; i++) {
                System.out.println(TaxDeclaration.declarations[i]);
            }

        } else {
            System.out.println("No declaration history found.");
        }
    }

    public static void viewAssessmentHistory() {

        System.out.println("\n========== ASSESSMENT HISTORY ==========");

        if (TaxAssessment.assessmentCount > 0) {

            for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                System.out.println(TaxAssessment.assessments[i]);
            }

        } else {
            System.out.println("No assessment history found.");
        }
    }

    public static void viewTaxPayerStatement(Scanner sc, InputHelper inputHelper) {

        if (RegisteredTaxpayer.taxpayerCount > 0) {

            System.out.print("Enter Taxpayer TIN: ");
            String tin = inputHelper.handleTin(sc);

            RegisteredTaxpayer taxpayer = null;

            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {

                if (RegisteredTaxpayer.taxpayers[i].getTin().equals(tin)) {
                    taxpayer = RegisteredTaxpayer.taxpayers[i];
                    break;
                }
            }

            if (taxpayer != null) {
                System.out.println("\n========== TAXPAYER STATEMENT ==========");
                System.out.println(taxpayer);
                System.out.println("\n--- TAX OBLIGATIONS ---");

                boolean hasObligations = false;

                for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {

                    if (TaxObligation.taxObligations[i].getTaxpayer().getTin().equals(tin)) {

                        System.out.println(TaxObligation.taxObligations[i]);
                        hasObligations = true;
                    }
                }

                if (!hasObligations) {
                    System.out.println("No tax obligations found for this taxpayer.");
                }
            } else {
                System.out.println("Taxpayer not found.");
            }

        } else {
            System.out.println("No taxpayers registered.");
        }
    }


    public static void demonstratePolymorphism() {

        TaxParty[] parties = new TaxParty[RegisteredTaxpayer.taxpayerCount + RevenueOfficer.officerCount];

        int partyCount = 0;

        if (RegisteredTaxpayer.taxpayerCount > 0) {
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                parties[partyCount] = RegisteredTaxpayer.taxpayers[i];
                partyCount++;
            }
        }

        if (RevenueOfficer.officerCount > 0) {
            for (int i = 0; i < RevenueOfficer.officerCount; i++) {
                parties[partyCount] = RevenueOfficer.officers[i];
                partyCount++;
            }
        }
        if (partyCount == 0) {
            System.out.println("No TaxParty records available for polymorphism demonstration.");
        }
        for (int i = 0; i < partyCount; i++) {
            parties[i].printDetails();
        }


        Identifiable[] records = new Identifiable[
        RegisteredTaxpayer.taxpayerCount
        + RevenueOfficer.officerCount
        + TaxType.taxTypeCount
        + TaxObligation.taxObligationsCount
        + TaxDeclaration.declarationCount
        + TaxAssessment.assessmentCount
        + TaxPayment.taxPaymentsCount
        + Penalty.penaltyCount
        + TaxRefund.refundCount
        + TaxClearance.clearanceCount
];

        int recordCount = 0;

        if (RegisteredTaxpayer.taxpayerCount > 0) {
            for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                records[recordCount] = RegisteredTaxpayer.taxpayers[i];
                recordCount++;
            }
        }

        if (RevenueOfficer.officerCount > 0) {
            for (int i = 0; i < RevenueOfficer.officerCount; i++) {
                records[recordCount] = RevenueOfficer.officers[i];
                recordCount++;
            }
        }

        if (TaxType.taxTypeCount > 0) {
            for (int i = 0; i < TaxType.taxTypeCount; i++) {
                records[recordCount] = TaxType.taxTypes[i];
                recordCount++;
            }
        }

        if (TaxObligation.taxObligationsCount > 0) {
            for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {
                records[recordCount] = TaxObligation.taxObligations[i];
                recordCount++;
            }
        }

        if (TaxDeclaration.declarationCount > 0) {
            for (int i = 0; i < TaxDeclaration.declarationCount; i++) {
                records[recordCount] = TaxDeclaration.declarations[i];
                recordCount++;
            }
        }

        if (TaxAssessment.assessmentCount > 0) {
            for (int i = 0; i < TaxAssessment.assessmentCount; i++) {
                records[recordCount] = TaxAssessment.assessments[i];
                recordCount++;
            }
        }

        if (TaxPayment.taxPaymentsCount > 0) {
            for (int i = 0; i < TaxPayment.taxPaymentsCount; i++) {
                records[recordCount] = TaxPayment.taxPayments[i];
                recordCount++;
            }
        }

        if (Penalty.penaltyCount > 0) {
            for (int i = 0; i < Penalty.penaltyCount; i++) {
                records[recordCount] = Penalty.penalties[i];
                recordCount++;
            }
        }

        if (TaxRefund.refundCount > 0) {
            for (int i = 0; i < TaxRefund.refundCount; i++) {
                records[recordCount] = TaxRefund.refunds[i];
                recordCount++;
            }
        }

        if (TaxClearance.clearanceCount > 0) {
            for (int i = 0; i < TaxClearance.clearanceCount; i++) {
                records[recordCount] = TaxClearance.clearances[i];
                recordCount++;
            }
        }
        if (recordCount == 0) {
            System.out.println("No Identifiable records available for polymorphism demonstration.");
        }
        
        for (int i = 0; i < recordCount; i++) {
            System.out.println("\nID: " + records[i].getId());
            System.out.println("Name: " + records[i].getDisplayName());
            System.out.println("Active: " + records[i].isActive());
        }
    }

    
   
}
