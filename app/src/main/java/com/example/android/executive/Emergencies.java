package com.example.android.executive;

/**
  Created by RoshanJoy on 20-03-2017.
 */

public class Emergencies {

    public EmergencyDetails emergencyDetails;
    public LocationDetails locationDetails;

    public Emergencies(){}

    public Emergencies(EmergencyDetails emergencyDetails,LocationDetails locationDetails){
        this.emergencyDetails=emergencyDetails;
        this.locationDetails=locationDetails;
    }

    public EmergencyDetails getEmergencyDetails(){return this.emergencyDetails;}
    public LocationDetails getLocationDetails(){return this.locationDetails;}

    public void setEmergencyDetails(EmergencyDetails emergencyDetails){this.emergencyDetails=emergencyDetails;}
    public void setLocationDetails(LocationDetails locationDetails){this.locationDetails=locationDetails;}

}
