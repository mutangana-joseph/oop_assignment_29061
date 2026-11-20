package com.auca;

import com.auca.abstracts.*;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Scanner;
import com.auca.exceptions.InputHelper;

public class CorporateTaxpayer extends RegisteredTaxpayer{

    Scanner sc = new Scanner(System.in);
    private String companyRegistrationNo;
    private String legalName;
    private LocalDate incorporationDate;
    private String businessSector;
    private String authorizedRepresentative;
    private BigDecimal annualTurnover;

    
    public CorporateTaxpayer() {
    }


    public CorporateTaxpayer(String partyId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String tin, LocalDate registrationDate, String taxpayerStatus, String district,
            String sector, String taxOffice, String companyRegistrationNo, String legalName,
            LocalDate incorporationDate, String businessSector, String authorizedRepresentative,
            BigDecimal annualTurnover) {
        super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth, tin, registrationDate, taxpayerStatus,
                district, sector, taxOffice);
        this.companyRegistrationNo = companyRegistrationNo;
        this.legalName = legalName;
        this.incorporationDate = incorporationDate;
        this.businessSector = businessSector;
        this.authorizedRepresentative = authorizedRepresentative;
        this.annualTurnover = annualTurnover;
    }

    
    public String getCompanyRegistrationNo() {
        return companyRegistrationNo;
    }


    public void setCompanyRegistrationNo(String companyRegistrationNo) {
        this.companyRegistrationNo = companyRegistrationNo;
    }


    public String getLegalName() {
        return legalName;
    }


    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }


    public LocalDate getIncorporationDate() {
        return incorporationDate;
    }


    public void setIncorporationDate(LocalDate incorporationDate) {
        this.incorporationDate = incorporationDate;
    }


    public String getBusinessSector() {
        return businessSector;
    }


    public void setBusinessSector(String businessSector) {
        this.businessSector = businessSector;
    }


    public String getAuthorizedRepresentative() {
        return authorizedRepresentative;
    }


    public void setAuthorizedRepresentative(String authorizedRepresentative) {
        this.authorizedRepresentative = authorizedRepresentative;
    }


    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }


    public void setAnnualTurnover(BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }


    @Override
    public String getDisplayName() {
        // TODO Auto-generated method stub
        return getDisplayName();
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return companyRegistrationNo;
    }
    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public String getSummary() {
        return getDisplayName() +
                " | TIN: " + getTin() +
                " | Sector: " + businessSector +
                " | Status: " + getTaxpayerStatus();
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

        while (step >= 1 && step <= 4) {

            switch (step) {

                case 1:
                    System.out.println("\n========== REGISTER CORPORATE TAXPAYER ==========");
                    System.out.println("STEP 1 OF 4 - CONTACT DETAILS");

                    System.out.print("Party ID: ");
                    setPartyId(inputHelper.handleExistingPartyId(sc, inputHelper));

                    System.out.print("First name: ");
                    setFirstName(inputHelper.handleName(sc));

                    System.out.print("Last name: ");
                    setLastName(inputHelper.handleName(sc));

                    System.out.print("Email: ");
                    setEmail(inputHelper.handleEmail(sc));

                    System.out.print("Phone number: ");
                    setPhoneNumber(inputHelper.handlePhoneNumber(sc));

                    System.out.print("Date of birth (YYYY-MM-DD): ");
                    setDateOfBirth(inputHelper.handleLocalDate(sc));

                    break;

                case 2:
                    System.out.println("\nSTEP 2 OF 4 - TAX REGISTRATION DETAILS");

                    System.out.print("TIN: ");
                    setTin(inputHelper.handleExistingTin(sc, inputHelper));

                    System.out.print("Registration date (YYYY-MM-DD): ");
                    setRegistrationDate(inputHelper.handleDateBefore(sc, getDateOfBirth()));

                    System.out.print("Taxpayer status (ACTIVE/INACTIVE): ");
                    setTaxpayerStatus(inputHelper.handleTaxpayerStatus(sc));

                    System.out.print("District: ");
                    setDistrict(inputHelper.handleBlank(sc));

                    System.out.print("Sector: ");
                    setSector(inputHelper.handleBlank(sc));

                    System.out.print("Tax office: ");
                    setTaxOffice(inputHelper.handleBlank(sc));

                    break;

                case 3:
                    System.out.println("\nSTEP 3 OF 4 - CORPORATE DETAILS");

                    System.out.print("Company registration number: ");
                    setCompanyRegistrationNo(inputHelper.handleExistingCompanyRegistrationNumber(sc, inputHelper));

                    System.out.print("Legal name: ");
                    setLegalName(inputHelper.handleBlank(sc));

                    System.out.print("Incorporation date (YYYY-MM-DD): ");
                    setIncorporationDate(inputHelper.handleDateBefore(sc, getRegistrationDate()));

                    System.out.print("Business sector: ");
                    setBusinessSector(inputHelper.handleBlank(sc));

                    System.out.print("Authorized representative: ");
                    setAuthorizedRepresentative(inputHelper.handleBlank(sc));

                    System.out.print("Annual turnover: ");
                    setAnnualTurnover(inputHelper.handleBigDecimal(sc));

                    break;

                case 4:
                    System.out.println("\n========== REVIEW CORPORATE TAXPAYER ==========");
                    printDetails();

                    System.out.println("\n[1] SAVE");
                    System.out.println("[2] EDIT DETAILS");
                    System.out.println("[0] CANCEL");

                    System.out.print("Select: ");
                    int choice = inputHelper.handleNonInt(sc);

                    if (choice == 1) {
                        try {
                            validate();

                            if (RegisteredTaxpayer.taxpayerCount < RegisteredTaxpayer.taxpayers.length) {

                                RegisteredTaxpayer.taxpayers[RegisteredTaxpayer.taxpayerCount] = this;
                                RegisteredTaxpayer.taxpayerCount++;

                                completed = true;
                                step = 5;

                                System.out.println("Corporate taxpayer registered successfully!");

                            } else {
                                System.out.println("Taxpayer storage is full!");
                            }

                        } catch (ValidationException e) {
                            System.out.println("Registration failed: " + e.getMessage());
                        }

                    } else if (choice == 2) {

                        System.out.println("\nSELECT STEP TO EDIT");
                        System.out.println("[1] Contact details");
                        System.out.println("[2] Tax registration details");
                        System.out.println("[3] Corporate details");

                        System.out.print("Select: ");
                        int editChoice = inputHelper.handleNonInt(sc);

                        if (editChoice >= 1 && editChoice <= 3) {
                            step = editChoice;
                        } else {
                            System.out.println("Invalid selection.");
                        }

                    } else if (choice == 0) {
                        step = 0;

                    } else {
                        System.out.println("Invalid selection.");
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

        if (!completed) {
            System.out.println("Corporate taxpayer registration cancelled.");
        }
    }

    @Override
    public String toString() {
        return "CorporateTaxpayer {" +
                "\nParty ID: " + getPartyId() +
                "\nFirst Name: " + getFirstName() +
                "\nLast Name: " + getLastName() +
                "\nEmail: " + getEmail() +
                "\nPhone Number: " + getPhoneNumber() +
                "\nDate of Birth: " + getDateOfBirth() +
                "\nTIN: " + getTin() +
                "\nRegistration Date: " + getRegistrationDate() +
                "\nTaxpayer Status: " + getTaxpayerStatus() +
                "\nDistrict: " + getDistrict() +
                "\nSector: " + getSector() +
                "\nTax Office: " + getTaxOffice() +
                "\nCompany Registration No: " + companyRegistrationNo +
                "\nLegal Name: " + legalName +
                "\nIncorporation Date: " + incorporationDate +
                "\nBusiness Sector: " + businessSector +
                "\nAuthorized Representative: " + authorizedRepresentative +
                "\nAnnual Turnover: " + annualTurnover +
                "\nJava Type: " + this.getClass().getSimpleName() +
                "\nAllowed range: " + taxpayers.length +
                "\n}";
    }

    



}