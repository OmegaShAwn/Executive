package com.example.android.executive;

/**
  Created by RoshanJoy on 16-03-2017.
 */

class EmergencyDetails {

    private String si;
    private String ti;
    public String no;
    public String username;
    String getSi(){
        if(si==null)
            si="0";
        return si;
    }
    String getno(){return no;}
    String getTi(){return ti;}
    public void setUsername(String username){this.username=username;}
    public String getUsername(){return username;}
}
