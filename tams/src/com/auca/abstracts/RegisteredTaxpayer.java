package com.auca.abstracts;
import java.time.LocalDate;

public abstract class RegisteredTaxpayer extends TaxParty {
    private String tin;
    private LocalDate registrationDate;
    private String taxpayerStatus;
    private String district;
    private String sector;
    private String taxOffice;

    public static RegisteredTaxpayer[] taxpayers = new RegisteredTaxpayer[100];
    public static int taxpayerCount = 0;
    
    public RegisteredTaxpayer() {
    }


    


    public RegisteredTaxpayer(String partyId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String tin, LocalDate registrationDate, String taxpayerStatus, String district,
            String sector, String taxOffice) {
        super(partyId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.tin = tin;
        this.registrationDate = registrationDate;
        this.taxpayerStatus = taxpayerStatus;
        this.district = district;
        this.sector = sector;
        this.taxOffice = taxOffice;
    }





    public String getTin() {
        return tin;
    }


    public void setTin(String tin) {
        this.tin = tin;
    }


    public LocalDate getRegistrationDate() {
        return registrationDate;
    }


    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }


    public String getTaxpayerStatus() {
        return taxpayerStatus;
    }


    public void setTaxpayerStatus(String taxpayerStatus) {
        this.taxpayerStatus = taxpayerStatus;
    }


    public String getDistrict() {
        return district;
    }


    public void setDistrict(String district) {
        this.district = district;
    }


    public String getSector() {
        return sector;
    }


    public void setSector(String sector) {
        this.sector = sector;
    }


    public String getTaxOffice() {
        return taxOffice;
    }


    public void setTaxOffice(String taxOffice) {
        this.taxOffice = taxOffice;
    }

    



    

    
    
    
}
