package com.example.android.executive;

/**
  Created by RoshanJoy on 17-03-2017.
 */

class LocationDetails {

    private Double latitude;
    private Double longitude;

    LocationDetails(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    Double getLatitude(){return  latitude;}
    Double getLongitude(){return longitude;}
}

