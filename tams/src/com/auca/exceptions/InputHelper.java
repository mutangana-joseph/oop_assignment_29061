
package com.auca.exceptions;

import java.util.Scanner;

import com.auca.abstracts.RegisteredTaxpayer;

import com.auca.TaxPayment;

import com.auca.TaxObligation;

import com.auca.RevenueOfficer;
import com.auca.CorporateTaxpayer;
import com.auca.IndividualTaxpayer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InputHelper {


    // Is party ID valid?
    public String handlePartyId(Scanner sc) {
        while (true) {
            String id = sc.nextLine().trim();

            if (id.length() >= 4 && id.length() <= 30) {
                return id;
            }

            System.out.print("Party ID must contain 4-30 characters. Try again: ");
        }
    }


    // Handle blank input
    public String handleBlank(Scanner sc) {
        while (true) {
            String value = sc.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.print("Input cannot be blank. Try again: ");
        }
    }

    // Handle integer input
    public int handleNonInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number. Try again: ");
            }
        }
    }

    // Handle BigDecimal input
    public BigDecimal handleBigDecimal(Scanner sc) {
        while (true) {
            try {
                BigDecimal value = new BigDecimal(sc.nextLine().trim());

                if (value.scale() <= 2 &&
                        value.compareTo(new BigDecimal("0")) >= 0 &&
                        value.compareTo(new BigDecimal("999999999999.99")) <= 0) {
                    return value;
                }

                System.out.print(
                        "Amount must be non-negative, have at most 2 decimal places and not exceed 999999999999.99. Try again: ");

            } catch (NumberFormatException e) {
                System.out.print("Enter a valid decimal amount. Try again: ");
            }
        }
    }

    // Handle Declared Tax
    public BigDecimal handleDeclaredTax(Scanner sc, BigDecimal expectedTax) {

        while (true) {

            BigDecimal value = handleBigDecimal(sc);

            if (value.subtract(expectedTax).abs()
                    .compareTo(new BigDecimal("0.01")) <= 0) {
                return value;
            }

            System.out.println("Declared tax does not match expected tax!");
            System.out.println("Expected Tax: " + expectedTax);
            System.out.print("Enter declared tax again: ");
        }
    }


    // Handle Adjustment Amount
    public BigDecimal handleAdjustmentAmount(Scanner sc) {
        while (true) {
            try {
                BigDecimal value = new BigDecimal(sc.nextLine().trim());

                if (value.scale() <= 2 &&
                        value.abs().compareTo(new BigDecimal("999999999999.99")) <= 0) {
                    return value;
                }

                System.out.print(
                        "Adjustment must have at most 2 decimal places and not exceed the allowed limit. Try again: ");

            } catch (NumberFormatException e) {
                System.out.print("Enter a valid decimal amount. Try again: ");
            }
        }
    }

    // Handle Payment Amount
    public BigDecimal handlePaymentAmount(Scanner sc) {

        while (true) {

            BigDecimal value = handleBigDecimal(sc);

            if (value.compareTo(BigDecimal.ZERO) > 0) {
                return value;
            }

            System.out.print("Payment amount must be greater than zero. Try again: ");
        }
    }

    // Handle Payment Within Outstanding Balance
    public BigDecimal handlePaymentWithinBalance(Scanner sc, BigDecimal outstandingBalance) {

        while (true) {

            BigDecimal value = handlePaymentAmount(sc);

            if (value.compareTo(outstandingBalance) <= 0) {
                return value;
            }

            System.out.println("Payment exceeds the outstanding balance!");
            System.out.println("Outstanding Balance: " + outstandingBalance);
            System.out.print("Enter Payment Amount again: ");
        }
    }


    // Handle Payment Method
    public String handlePaymentMethod(Scanner sc) {
        while (true) {
            String value = handleBlank(sc);

            if (value.equalsIgnoreCase("CASH")) {
                return "CASH";
            } else if (value.equalsIgnoreCase("BANK TRANSFER")) {
                return "BANK TRANSFER";
            }
            else if (value.equalsIgnoreCase("AIRTEL MONEY")) {
                return "AIRTEL MONEY";
            }
            else if (value.equalsIgnoreCase("MoMo")) {
                return "MoMo";
            }

            System.out.print("Invalid payment method. Choose CASH, BANK TRANSFER, AIRTEL MONEY or MOBILE MONEY: ");
        }
    }

    // Handle Existing Payment Reference
    public static String handleExistingPaymentReference(Scanner sc, InputHelper inputHelper) {

        while (true) {

            String value = inputHelper.handleBlank(sc);
            boolean isExist = false;

            for (int i = 0; i < TaxPayment.taxPaymentsCount; i++) {

                if (TaxPayment.taxPayments[i].getPaymentReference().equals(value)) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                System.out.print("Payment reference already exists. Enter another one: ");
            } else {
                return value;
            }
        }
    }

    

    // Handle boolean input
    public boolean handleBoolean(Scanner sc) {
        while (true) {
            String value = sc.nextLine().trim();

            if (value.equalsIgnoreCase("true")) {
                return true;
            }

            if (value.equalsIgnoreCase("false")) {
                return false;
            }

            System.out.print("Enter true or false. Try again: ");
        }
    }

    // Handle email input
    public String handleEmail(Scanner sc) {
        while (true) {
            String email = sc.nextLine().trim();

            if (email.length() <= 120 &&
                    email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return email;
            }

            System.out.print("Invalid email. Maximum 120 characters. Try again: ");
        }
    }

    // Handle phone number input
    public String handlePhoneNumber(Scanner sc) {
        while (true) {
            String phone = sc.nextLine().trim();

            if (phone.matches("\\+?\\d{10,15}")) {
                return phone;
            }

            System.out.print("Phone must contain 10-15 digits, optionally starting with +. Try again: ");
        }
    }

    // Handle taxpayer status
    public String handleTaxpayerStatus(Scanner sc) {
        while (true) {
            String status = sc.nextLine().trim();

            if (status.equalsIgnoreCase("ACTIVE")) {
                return "ACTIVE";
            }

            if (status.equalsIgnoreCase("INACTIVE")) {
                return "INACTIVE";
            }

            System.out.print("Enter ACTIVE or INACTIVE. Try again: ");
        }
    }

    // Handle names (2-60 characters)
    public String handleName(Scanner sc) {
        while (true) {
            String name = sc.nextLine().trim();

            if (name.length() >= 2 && name.length() <= 60) {
                return name;
            }

            System.out.print("Name must contain 2-60 characters. Try again: ");
        }
    }

    // Handle national ID (exactly 16 digits)
    public String handleNationalId(Scanner sc) {
        while (true) {
            String id = sc.nextLine().trim();

            if (id.matches("\\d{16}")) {
                return id;
            }

            System.out.print("National ID must contain exactly 16 digits. Try again: ");
        }
    }

    // Handle TIN (exactly 9 digits)
    public String handleTin(Scanner sc) {
        while (true) {
            String tin = sc.nextLine().trim();

            if (tin.matches("\\d{9}")) {
                return tin;
            }

            System.out.print("TIN must contain exactly 9 digits. Try again: ");
        }
    }

    //Handle Company registration number
    public String handleCompanyRegistratioNumber(Scanner sc){
        while(true){
            String value = handleBlank(sc);
            if(value.matches("[A-Z0-9-]{5,30}")){
                return value;

            }
            System.out.print("Company registration number must have UpperCase and between 5-30 lenght. Try again: ");

        }
    }

    // Handle NEXT, BACK and CANCEL
    public String handleStepNavigation(Scanner sc, int step) {

        while (true) {

            System.out.println("\nNEXT / BACK / CANCEL");
            System.out.print("Select: ");

            String choice = handleBlank(sc);

            if (choice.equalsIgnoreCase("NEXT")) {
                return "NEXT";
            }

            else if (choice.equalsIgnoreCase("BACK")) {

                if (step > 1) {
                    return "BACK";
                }

                System.out.println("You are already at the first step.");
            }

            else if (choice.equalsIgnoreCase("CANCEL")) {
                return "CANCEL";
            }

            else {
                System.out.println("Wrong choice. Try again.");
            }
        }
    }

    //Handle tax name
    public String handleTaxName(Scanner sc) {
        while (true) {
            String taxName = sc.nextLine().trim().toUpperCase();

            if (taxName.equals("INCOME TAX") ||
                    taxName.equals("VAT") ||
                    taxName.equals("WITHHOLDING TAX")) {
                return taxName;
            }

            System.out.print("Enter INCOME TAX, VAT or WITHHOLDING TAX: ");
        }
    }



    // Handle LocalDate input
    public LocalDate handleLocalDate(Scanner sc) {
        while (true) {
            try {
                String value = sc.nextLine().trim();

                if (!value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println("Invalid date format");
                }

                return LocalDate.parse(value);

            } catch (DateTimeParseException e) {
                System.out.print("Invalid date. Use YYYY-MM-DD. Try again: ");
            }
        }
    }

    //Handle Date after
    public LocalDate handleDateAfter(Scanner sc, LocalDate previousDate){
        while(true){
            LocalDate value =handleLocalDate(sc);
            if(value.isAfter(previousDate)){
                return value;

            }
            System.out.print("Incorporation date cannot be after registration date! Try again: ");

        }
        
    }

    //Handle Date before
    public LocalDate handleDateBefore(Scanner sc, LocalDate previousDate){
        while(true){
            LocalDate value = handleLocalDate(sc);
            if (!value.isBefore(previousDate)) {
                return value;

            }
            System.out.print("Thi date can not be less than previous date! Try again: ");

        }
    }

    //Handle Current date
    public LocalDate handleFilingDate(Scanner sc) {
        while (true) {
            LocalDate filingDate = handleLocalDate(sc);

            if (!filingDate.isAfter(LocalDate.now())) {
                return filingDate;
            }

            System.out.print("Filing date cannot be in the future. Enter again (YYYY-MM-DD): ");
        }
    }


    //Handle existing TIN
    public static String handleExistingTin(Scanner sc, InputHelper inputHelper){
       
            while(true){
                String value = inputHelper.handleTin(sc);
                boolean isExist = false;
                if (RegisteredTaxpayer.taxpayerCount > 0) {
                    for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                        if (RegisteredTaxpayer.taxpayers[i].getTin().equals(value)) {
                            isExist=true;
                        
                        }
                    }
                    if (isExist) {
                        System.out.print("Taxpayer with this TIN already exists. Try new one: ");
                    } else {
                        return value;
                    }    
                }
                 else {
                    return value;
                }

            }
           
    }

    // Handle existing national ID
    public static String handleExistingNationalId(Scanner sc, InputHelper inputHelper) {

        while (true) {
            String value = inputHelper.handleNationalId(sc);
            boolean isExist = false;
            if (RegisteredTaxpayer.taxpayerCount > 0) {
                for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {

                    if (RegisteredTaxpayer.taxpayers[i] instanceof IndividualTaxpayer) {
                        IndividualTaxpayer individual = (IndividualTaxpayer) RegisteredTaxpayer.taxpayers[i];

                        if (individual.getNationalId().equals(value)) {
                            isExist = true;
                        }
                       
                    }
                }
                if (isExist) {
                    System.out.print("Taxpayer with this National ID already exists. Try new one: ");
                } else {
                    return value;
                }    
            
            } else {
                return value;
            }
        }
    }

    // Handle existing Party ID
    public static String handleExistingPartyId(Scanner sc, InputHelper inputHelper) {

        while (true) {
            String value = inputHelper.handlePartyId(sc);
            boolean isExist=false;
            if (RegisteredTaxpayer.taxpayerCount > 0) {
                for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                    if (RegisteredTaxpayer.taxpayers[i].getPartyId().equals(value)) {
                        isExist=true;
                        break;
                    }
                }
            if(isExist){
                System.out.print("Taxpayer with this PartyID already exists. Try new one: ");
            }
            else{
                return value;
            }

            } else {
                return value;
            }
        }
    }


    // handleExistingStaffNumber
    public static String handleExistingStaffNumber(Scanner sc) {

        while (true) {
            String value = sc.nextLine();
            boolean isExist = false;
            if (RevenueOfficer.officerCount > 0) {
                for (int i = 0; i < RevenueOfficer.officerCount; i++) {
                    
                    if (RevenueOfficer.officers[i].getStaffNumber().equals(value)) {
                        isExist = true;
                        break;

                    }
                }
                if (isExist) {
                    System.out.print("Officer with this Staff No already exists. Try new one: ");
                } else {
                    return value;
                }
            } else {
                return value;
            }

        }

    }

    // handleExistingCompanyRegistrationNumber
    public static String handleExistingCompanyRegistrationNumber(Scanner sc, InputHelper inputHelper) {

        while (true) {
            String value = inputHelper.handleCompanyRegistratioNumber(sc);
            boolean isExist = false;
            if (RegisteredTaxpayer.taxpayerCount > 0) {
                for (int i = 0; i < RegisteredTaxpayer.taxpayerCount; i++) {
                    if(RegisteredTaxpayer.taxpayers[i] instanceof CorporateTaxpayer){
                        CorporateTaxpayer corporateTaxpayer = (CorporateTaxpayer) RegisteredTaxpayer.taxpayers[i];
                        if (corporateTaxpayer.getCompanyRegistrationNo().equals(value)) {
                            isExist = true;
                            break;
                        }
                    }
                    
                }
                if (isExist) {
                    System.out.print("Company Registration already exists. Try new one: ");
                } else {
                    return value;
                }

            } else {
                return value;
            }
        }
    }

    // Handle existing Obligation ID
public static String handleExistingObligationId(Scanner sc, InputHelper inputHelper) {

    while (true) {

        String value = inputHelper.handleBlank(sc);
        boolean isExist = false;

        if (TaxObligation.taxObligationsCount > 0) {

            for (int i = 0; i < TaxObligation.taxObligationsCount; i++) {

                if (TaxObligation.taxObligations[i].getObligationId().equals(value)) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                System.out.print("Obligation ID already exists. Enter another one: ");
            } else {
                return value;
            }

        } else {
            return value;
        }
    }
}

}

