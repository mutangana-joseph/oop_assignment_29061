
package com.auca;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;
import com.auca.abstracts.RegisteredTaxpayer;
import com.auca.abstracts.TaxParty;

public class IndividualTaxpayer extends RegisteredTaxpayer {

    private String nationalId;
    private String occupation;
    private String businessName;
    private LocalDate businessStartDate;
    private BigDecimal annualTurnover;
    private boolean resident;

    

    public IndividualTaxpayer() {
    }

    public IndividualTaxpayer(String partyId, String firstName, String lastName, String email,
            String phoneNumber, LocalDate dateOfBirth, String tin, LocalDate registrationDate,
            String taxpayerStatus, String district, String sector, String taxOffice,
            String nationalId, String occupation, String businessName,
            LocalDate businessStartDate, BigDecimal annualTurnover, boolean resident) {

        super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth,
                tin, registrationDate, taxpayerStatus, district, sector, taxOffice);

        this.nationalId = nationalId;
        this.occupation = occupation;
        this.businessName = businessName;
        this.businessStartDate = businessStartDate;
        this.annualTurnover = annualTurnover;
        this.resident = resident;
    }

    // Getters and setters

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public LocalDate getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(LocalDate businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    public boolean getIsResident() {
        return resident;
    }

    public void setIsResident(boolean resident) {
        this.resident = resident;
    }

    // Interface methods

    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String getId() {
        return nationalId;
    }

    @Override
    public boolean isActive() {
        return getTaxpayerStatus().equalsIgnoreCase("ACTIVE");
    }

    @Override
    public String getSummary() {
        return getDisplayName() + " | TIN: " + getTin()
                + " | Status: " + getTaxpayerStatus();
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public void validate() throws ValidationException {

        // 1. Check Party ID
        if (getPartyId() == null ||
                !getPartyId().matches(".{4,30}")) {

            throw new ValidationException(
                    "Party ID must contain 4-30 characters.");
        }

        // 2. Check names
        if (getFirstName() == null ||
                getFirstName().trim().length() < 2 ||
                getFirstName().trim().length() > 60) {

            throw new ValidationException(
                    "First name must contain 2-60 characters.");
        }

        if (getLastName() == null ||
                getLastName().trim().length() < 2 ||
                getLastName().trim().length() > 60) {

            throw new ValidationException(
                    "Last name must contain 2-60 characters.");
        }

        // 3. Check email
        if (getEmail() == null ||
                getEmail().length() > 120 ||
                !getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

            throw new ValidationException("Invalid email address.");
        }

        // 4. Check phone number
        if (getPhoneNumber() == null ||
                !getPhoneNumber().matches("\\+?\\d{10,15}")) {

            throw new ValidationException("Invalid phone number.");
        }

        // 5. Check TIN
        if (getTin() == null ||
                !getTin().matches("\\d{9}")) {

            throw new ValidationException("TIN must contain exactly 9 digits.");
        }

        // 6. Check National ID
        if (nationalId == null ||
                !nationalId.matches("\\d{16}")) {

            throw new ValidationException(
                    "National ID must contain exactly 16 digits.");
        }

        // 7. Check dates
        if (getDateOfBirth() == null ||
                getRegistrationDate() == null ||
                !getDateOfBirth().isBefore(getRegistrationDate())) {

            throw new ValidationException(
                    "Date of birth must be before registration date.");
        }

        if (businessStartDate == null ||
                !businessStartDate.isBefore(getRegistrationDate())) {

            throw new ValidationException(
                    "Business start date must be before registration date.");
        }

        // 8. Check annual turnover
        if (annualTurnover == null ||
                annualTurnover.compareTo(BigDecimal.ZERO) < 0 ||
                annualTurnover.scale() > 2 ||
                annualTurnover.compareTo(
                        new BigDecimal("999999999999.99")) > 0) {

            throw new ValidationException(
                    "Invalid annual turnover amount.");
        }

        // 9. Check taxpayer status
        if (getTaxpayerStatus() == null ||
                (!getTaxpayerStatus().equals("ACTIVE") &&
                        !getTaxpayerStatus().equals("INACTIVE"))) {

            throw new ValidationException(
                    "Taxpayer status must be ACTIVE or INACTIVE.");
        }

        // 10. Check other required information
        if (getDistrict() == null || getDistrict().trim().isEmpty() ||
                getSector() == null || getSector().trim().isEmpty() ||
                getTaxOffice() == null || getTaxOffice().trim().isEmpty() ||
                occupation == null || occupation.trim().isEmpty() ||
                businessName == null || businessName.trim().isEmpty()) {

            throw new ValidationException(
                    "Some required taxpayer details are missing.");
        }

        System.out.println("All individual taxpayer validations passed!");
    }

    

    // Registration form
    public void save(Scanner sc, InputHelper inputHelper) throws ValidationException {

        int step = 1;
        boolean completed = false;

        while (step >= 1 && step <= 4) {

            switch (step) {

                case 1:

                    System.out.println("\n===== REGISTER INDIVIDUAL TAXPAYER =====");
                    System.out.println("STEP 1 OF 4 - PERSONAL DETAILS");

                    System.out.print("Enter Party ID: ");
                    setPartyId(inputHelper.handleExistingPartyId(sc, inputHelper));

                    System.out.print("Enter First Name: ");
                    setFirstName(inputHelper.handleName(sc));

                    System.out.print("Enter Last Name: ");
                    setLastName(inputHelper.handleName(sc));

                    System.out.print("Enter Email: ");
                    setEmail(inputHelper.handleEmail(sc));

                    System.out.print("Enter Phone Number: ");
                    setPhoneNumber(inputHelper.handlePhoneNumber(sc));

                    System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
                    setDateOfBirth(inputHelper.handleLocalDate(sc));
                    break;

                case 2:

                    System.out.println("\n===== TAX REGISTRATION DETAILS =====");
                    System.out.println("STEP 2 OF 4");

                    System.out.print("Enter TIN: ");
                    setTin(inputHelper.handleExistingTin(sc, inputHelper));

                    System.out.print("Enter Registration Date (YYYY-MM-DD): ");
                    setRegistrationDate(inputHelper.handleDateBefore(sc, getDateOfBirth()));

                    System.out.print("Enter Taxpayer Status: ");
                    setTaxpayerStatus(inputHelper.handleTaxpayerStatus(sc));

                    System.out.print("Enter District: ");
                    setDistrict(inputHelper.handleBlank(sc));

                    System.out.print("Enter Sector: ");
                    setSector(inputHelper.handleBlank(sc));

                    System.out.print("Enter Tax Office: ");
                    setTaxOffice(inputHelper.handleBlank(sc));

                    break;

                case 3:

                    System.out.println("\n===== INDIVIDUAL BUSINESS DETAILS =====");
                    System.out.println("STEP 3 OF 4");

                    System.out.print("Enter National ID: ");
                    setNationalId(inputHelper.handleExistingNationalId(sc, inputHelper));

                    System.out.print("Enter Occupation: ");
                    setOccupation(inputHelper.handleBlank(sc));

                    System.out.print("Enter Business Name: ");
                    setBusinessName(inputHelper.handleBlank(sc));

                    System.out.print("Enter Business Start Date (YYYY-MM-DD): ");
                    setBusinessStartDate(inputHelper.handleDateBefore(sc, getRegistrationDate()));

                    System.out.print("Enter Annual Turnover: ");
                    setAnnualTurnover(inputHelper.handleBigDecimal(sc));

                    System.out.print("Is the taxpayer a resident? (true/false): ");
                    setIsResident(inputHelper.handleBoolean(sc));

                    break;

                case 4:

                    System.out.println("\n===== REVIEW TAXPAYER DETAILS =====");
                    System.out.println("STEP 4 OF 4");

                    printDetails();

                    System.out.println("\n[1] SAVE");
                    System.out.println("[2] EDIT DETAILS");
                    System.out.println("[0] CANCEL");

                    System.out.print("Select: ");

                    int reviewChoice = inputHelper.handleNonInt(sc);

                    if (reviewChoice == 1) {

                        
                         if (RegisteredTaxpayer.taxpayerCount < RegisteredTaxpayer.taxpayers.length) {

                            RegisteredTaxpayer.taxpayers[RegisteredTaxpayer.taxpayerCount] = this;
                            RegisteredTaxpayer.taxpayerCount++;
                            completed = true;
                            step = 5;

                            System.out.println("Taxpayer registered successfully!");

                        } else {
                            System.out.println("Taxpayer storage is full!");
                        }

                    } else if (reviewChoice == 2) {

                        System.out.println("\nWhich step would you like to edit?");
                        System.out.print("Enter step (1-3): ");

                        int editStep = inputHelper.handleNonInt(sc);

                        if (editStep >= 1 && editStep <= 3) {
                            step = editStep;
                        } else {
                            System.out.println("Invalid step number.");
                        }

                    } else if (reviewChoice == 0) {

                        step = 0;

                    } else {

                        System.out.println("Invalid choice.");
                    }

                    break;
            }

            if (step >= 1 && step <= 3) {

                String choice = inputHelper.handleStepNavigation(sc, step);

                if (choice.equalsIgnoreCase("NEXT")) {
                    step++;
                }

                else if (choice.equalsIgnoreCase("BACK")) {
                    step--;
                }

                else if (choice.equalsIgnoreCase("CANCEL")) {
                    step = 0;
                }
            }
        }

        if (completed) {
            System.out.println("\nIndividual taxpayer details captured successfully!");
        } else {
            System.out.println("\nRegistration cancelled.");
        }
    }

    @Override
    public String toString() {
       
        return "\nParty ID: " + getPartyId()
            + "\nFirst Name: " + getFirstName()
            + "\nLast Name: " + getLastName()
            + "\nEmail: " + getEmail()
            + "\nTelephone: " + getPhoneNumber()
            + "\nDate of Birth: " + getDateOfBirth()
            + "\nTIN number: " + getTin()
            + "\nRegistration Date: " + getRegistrationDate()
            + "\nTaxpayer Status: " + getTaxpayerStatus()
            + "\nDistrict: " + getDistrict()
            + "\nSector: " + getSector()
            + "\nTax Office: " + getTaxOffice()
            + "\nNational ID: " + getNationalId()
            + "\nOccupation: " + getOccupation()
            + "\nBusiness Name: " + getBusinessName()
            + "\nBusiness Start Date: " + getBusinessStartDate()
            + "\nAnnual Turnover: " + getAnnualTurnover()
            + "\nResident: " + getIsResident()
            +"\nJava Type: "+this.getClass().getSimpleName()
            +"\nAllowed range: "+ taxpayers.length
            +"";



        
        
    }   
}
