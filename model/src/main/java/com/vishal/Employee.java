package com.vishal;

public class Employee {
    int id;
    String firstName;
    String middleName;
    String lastNamw;
    String password;
    String email;
    int phoneNumber;
    String address;

    public int getId() {
        return id;
    }

    public Employee() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastNamw() {
        return lastNamw;
    }

    public void setLastNamw(String lastNamw) {
        this.lastNamw = lastNamw;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee(int id, String firstName, String middleName, String lastNamw, String password, String email, int phoneNumber, String address) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastNamw = lastNamw;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

}