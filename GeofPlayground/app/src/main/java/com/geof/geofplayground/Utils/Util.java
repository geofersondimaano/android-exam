package com.geof.geofplayground.Utils;

public class Util {

    public static String ResponseFormatter(String keyData){

        if(keyData.equals("address")){
            keyData = "Address";
        }
        if(keyData.equals("age")){
            keyData = "Age";
        }
        if(keyData.equals("birthday")){
            keyData = "Birthday";
        }
        if(keyData.equals("contactPerson")){
            keyData = "Contact Person";
        }
        if(keyData.equals("cpNumber")){
            keyData = "Contact Person Number";
        }
        if(keyData.equals("email")){
            keyData = "Email";
        }
        if(keyData.equals("fName")){
            keyData = "First Name";
        }
        if(keyData.equals("lName")){
            keyData = "Last Name";
        }
        if(keyData.equals("number")){
            keyData = "Number";
        }
        return keyData;
    }
}
