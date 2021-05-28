package com.geof.geofplayground.Model;

public class Contacts {
    public String fName;
    public String lName;
    public String birthday;
    public int age;
    public String email;
    public String number;
    public String address;
    public String contactPerson;
    public String cpNumber;
    public String key;

    public Contacts()
    {}

    public Contacts(String fName, String lName, String birthday, int age, String email, String number, String address, String contactPerson, String cpNumber, String key) {
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.age = age;
        this.email = email;
        this.number = number;
        this.address = address;
        this.contactPerson = contactPerson;
        this.cpNumber = cpNumber;
        this.key = key;
    }

    public Contacts(String fName, String lName, String birthday, int age, String email, String number, String address, String contactPerson, String cpNumber) {
        this.fName = fName;
        this.lName = lName;
        this.birthday = birthday;
        this.age = age;
        this.email = email;
        this.number = number;
        this.address = address;
        this.contactPerson = contactPerson;
        this.cpNumber = cpNumber;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCpNumber() {
        return cpNumber;
    }

    public void setCpNumber(String cpNumber) {
        this.cpNumber = cpNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
