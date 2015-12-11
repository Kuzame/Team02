/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.model;

import java.io.Serializable;

public class Fan implements Serializable {

    /**
     * For serialization/deserialization to check class version.
     */
    private static final long serialVersionUID = -7621164129293024859L;

    private boolean isOn;
    private boolean power;

    private double freq;
    private double speed;

    private double minStartingTemp;
    private double minRunningTemp;

    private double minSpeed;
    private double maxSpeed;

    private double temperature;

    // Constructor
    //
    public Fan() {
        this(false, 0.0, 0.0, 70, 60, 0.0, 100.0, 70.0);
    }
    
    public Fan(double speed, double freq,boolean power, Fan f){
        this.isOn = power; //here is also issue with the confusion of "power" and "isOn". Also we want the power passed through boolean power, not Fan f's (f.power).
        this.freq = f.freq;
        this.minStartingTemp = f.minStartingTemp;
        this.minRunningTemp = f.minRunningTemp;
        this.minSpeed = f.minSpeed;
        this.maxSpeed = f.maxSpeed;
        this.temperature = f.temperature;
        
        setSpeed(speed);        
    }

    public Fan(boolean power, double freq, double speed,
            double minStartingTemp, double minRunningTemp,
            double minSpeed, double maxSpeed, double temperature) {
        super();

        this.power = power;
        this.freq = freq;
        this.minStartingTemp = minStartingTemp;
        this.minRunningTemp = minRunningTemp;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.temperature = temperature;

        setSpeed(speed);
    }

    // Turn Off
    //
    public void turnOff() {
        isOn = false;
        power = false;
        freq = 40;
        setSpeed(0);
    }

    // Turn On
    //
    public void turnOn(boolean power, double freq, double speed) {
        isOn = true;

        this.power = true;
        this.freq = freq;
        setSpeed(speed);
    }

    public void turnOn(boolean power, double freq) {
        isOn = true;

        this.power = true;
        this.freq = freq;
        setSpeed(minSpeed);
    }

    // Getters & Setters
    //
    //  Is On?
    public boolean isOn() {
        return isOn;
    }

    // Power
    public boolean getPower() {
        return isOn;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    // Freq
    public double getFreq() {
        return freq;
    }

    public void setFreq(double freq) {
        this.freq = freq;
    }

    // Speed
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        if (isOn && this.speed + speed > this.maxSpeed) {
            this.speed = this.maxSpeed;
        } else if (isOn && this.speed + speed < this.minSpeed) {
            this.speed = this.minSpeed;
        } else {
            this.speed = speed;
        }
    }

    // Temperature
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // Minimum Starting Temperature
    public double getMinStartingTemp() {
        return minStartingTemp;
    }

    public void setMinStartingTemp(double minStartingTemp) {
        this.minStartingTemp = minStartingTemp;
    }

    // Minimum Running Temperature
    public double getMinRunningTemp() {
        return minRunningTemp;
    }

    public void setMinRunningTemp(double minRunningTemp) {
        this.minRunningTemp = minRunningTemp;
    }

    // Minimum Speed
    public double getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(double minSpeed) {
        this.minSpeed = minSpeed;
    }

    // Maximum Speed
    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Fan) {
            Fan temp = (Fan) obj;
            if (this.isOn == temp.isOn
                    && this.power == temp.power
                    && this.freq == temp.freq
                    && this.speed == temp.speed
                    && this.minStartingTemp == temp.minStartingTemp
                    && this.minRunningTemp == temp.minRunningTemp
                    && this.minSpeed == temp.minSpeed
                    && this.maxSpeed == temp.maxSpeed
                    && this.temperature == temp.temperature) {
                return true;
            }
        }

        return false;
    }

}
