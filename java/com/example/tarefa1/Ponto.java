package com.example.tarefa1;

public class Ponto {
    private double latitude, longitude, altitude;

    Ponto(){
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.altitude = 0.0;
    }

    Ponto(double latitude, double longitude, double altitude){
        this();
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public String imprimir(){
        String aux = "Latitude:"+this.latitude+"\n"+
                    "Longitude:"+this.longitude+"\n"+
                    "Altitude:"+this.altitude;
        return aux;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getAltitude(){
        return this.altitude;
    }
}
