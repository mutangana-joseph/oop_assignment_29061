package com.auca;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import com.auca.abstracts.*;
import com.auca.exceptions.InputHelper;
import com.auca.exceptions.ValidationException;

public class RevenueOfficer extends TaxParty {

    private String staffNumber;
    private String department;
    private String role ;
    private LocalDate hireDate ;
    private BigDecimal approvalLimit;
    private boolean active;

    public static RevenueOfficer[] officers = new RevenueOfficer[100];

    public static int officerCount =0;
    
    public RevenueOfficer() {
    }


    public RevenueOfficer(String partyId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String staffNumber, String department, String role, LocalDate hireDate,
            BigDecimal approvalLimit, boolean active) {
        super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.staffNumber = staffNumber;
        this.department = department;
        this.role = role;
        this.hireDate = hireDate;
        this.approvalLimit = approvalLimit;
        this.active = active;
    }


    


    public String getStaffNumber() {
        return staffNumber;
    }


    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }


    public String getDepartment() {
        return department;
    }


    public void setDepartment(String department) {
        this.department = department;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public LocalDate getHireDate() {
        return hireDate;
    }


    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }


    public BigDecimal getApprovalLimit() {
        return approvalLimit;
    }


    public void setApprovalLimit(BigDecimal approvalLimit) {
        this.approvalLimit = approvalLimit;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String getDisplayName() {
        return getFirstName() + " " + getLastName();
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return staffNumber;
    }


    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return  active;
    }


    @Override
    public String getSummary() {
        return getDisplayName() +
                " | Staff No: " + staffNumber +
                " | Department: " + department +
                " | Role: " + role +
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

    while (step >= 1 && step <= 3) {

        switch (step) {

            case 1:
                System.out.println("\n========== REGISTER REVENUE OFFICER ==========");
                System.out.println("STEP 1 OF 3 - PERSONAL DETAILS");

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
                System.out.println("\nSTEP 2 OF 3 - EMPLOYMENT DETAILS");

                System.out.print("Staff number: ");
                setStaffNumber(inputHelper.handleExistingStaffNumber(sc));

                System.out.print("Department: ");
                setDepartment(inputHelper.handleBlank(sc));

                System.out.print("Role: ");
                setRole(inputHelper.handleBlank(sc));

                System.out.print("Hire date (YYYY-MM-DD): ");
                setHireDate(inputHelper.handleDateBefore(sc, getDateOfBirth()));

                System.out.print("Approval limit: ");
                setApprovalLimit(inputHelper.handleBigDecimal(sc));

                System.out.print("Active (true/false): ");
                setActive(inputHelper.handleBoolean(sc));

                break;

            case 3:
                System.out.println("\n========== REVIEW REVENUE OFFICER ==========");
                System.out.println(this);

                System.out.println("\n[1] SAVE");
                System.out.println("[2] EDIT DETAILS");
                System.out.println("[0] CANCEL");

                System.out.print("Select: ");
                int choice = inputHelper.handleNonInt(sc);

                if (choice == 1) {

                    if (officerCount < officers.length) {

                        officers[officerCount] = this;
                        officerCount++;

                        completed = true;
                        step = 4;

                        System.out.println("Revenue officer registered successfully!");

                    } else {
                        System.out.println("Officer storage is full!");
                    }

                } else if (choice == 2) {

                    System.out.println("\nSELECT STEP TO EDIT");
                    System.out.println("[1] Personal details");
                    System.out.println("[2] Employment details");

                    System.out.print("Select: ");
                    int editChoice = inputHelper.handleNonInt(sc);

                    if (editChoice >= 1 && editChoice <= 2) {
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

        if (step >= 1 && step <= 2) {

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
        System.out.println("Revenue officer registration cancelled.");
    }
}

@Override
public String toString() {
    return "RevenueOfficer {" +
            "\nParty ID: " + getPartyId() +
            "\nFirst Name: " + getFirstName() +
            "\nLast Name: " + getLastName() +
            "\nEmail: " + getEmail() +
            "\nPhone Number: " + getPhoneNumber() +
            "\nDate of Birth: " + getDateOfBirth() +
            "\nStaff Number: " + staffNumber +
            "\nDepartment: " + department +
            "\nRole: " + role +
            "\nHire Date: " + hireDate +
            "\nApproval Limit: " + approvalLimit +
            "\nActive: " + active +
            "\nJava Type: "+this.getClass().getSimpleName()+
            "\nAllowed range: "+ officers.length+
            "\n}";
}

    
    

    
}
