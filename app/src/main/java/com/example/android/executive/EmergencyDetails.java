package com.example.android.executive;

/**
  Created by RoshanJoy on 16-03-2017.
 */

public class EmergencyDetails {

    public String si;
    public String ti;
    public String no;
    public String username;

    public EmergencyDetails(){}

    public EmergencyDetails(String si,String ti,String username,String no){
        this.si=si;
        this.ti=ti;
        this.username=username;
    }

    public void setSi(String si){this.si=si;}
    public void setTi(String ti){this.ti=ti;}
    public String getSi(){
        if(si==null)
            si="0";
        return si;
    }
    public String getno(){return no;}
    public String getTi(){return ti;}
    public void setUsername(String username){this.username=username;}
    public String getUsername(){return username;}
}
