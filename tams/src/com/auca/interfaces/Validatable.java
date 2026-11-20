package com.auca.interfaces;
import com.auca.exceptions.ValidationException;

public interface Validatable {
    public void validate() throws ValidationException;
    public void printDetails();
    public String getSummary();

    
} 