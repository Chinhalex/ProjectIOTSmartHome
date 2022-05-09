package com.example.smarthomeapp.Module;

public class DeviceModel {
    public String name;
    public boolean status;

    public DeviceModel(String name, boolean status) {
        this.name = name;
        this.status = status;
    }
    public DeviceModel( )
    {
        this.name = " ";
        this.status =false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
