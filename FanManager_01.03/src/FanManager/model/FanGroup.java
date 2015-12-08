/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FanManager.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class FanGroup implements Serializable {

    /**
     * For serialization/deserialization to check class version.
     */
    private static final long serialVersionUID = 8674918854563762484L;

    private ArrayList<Fan> fans;

    private double temperature;
    private double humidity;
    private double barometricPressure;

    // Constructor
    //
    public FanGroup(double temperature, double humidity, double barometricPressure) {
        super();
        this.fans = new ArrayList<Fan>();
    }

    public FanGroup(ArrayList<Fan> fans, double temperature, double humidity, double barometricPressure) {
        super();
        this.fans = fans;
        this.temperature = temperature;
        this.humidity = humidity;
        this.barometricPressure = barometricPressure;
    }

    // Takes array of fans
    public FanGroup(Fan[] fans, double temperature, double humidity, double barometricPressure) {
        super();
        this.fans = new ArrayList<Fan>(Arrays.asList(fans));
        this.temperature = temperature;
        this.humidity = humidity;
        this.barometricPressure = barometricPressure;
    }

    public void update(FanGroup fg) {
        this.fans = fg.fans;
        this.temperature = fg.temperature;
        this.humidity = fg.humidity;
        this.barometricPressure = fg.barometricPressure;
    }

    // Getters & Setters
    //
    // Fans
    public ArrayList<Fan> getFans() {
        return fans;
    }

    public synchronized void setFans(ArrayList<Fan> fans) {
        this.fans = fans;
    }

    // Takes array of fans
    public synchronized void setFans(Fan[] fans) {
        this.fans = new ArrayList<Fan>(Arrays.asList(fans));
    }

    // Temperature
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    // Humidity
    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    // Barometric Pressure
    public double getBarometricPressure() {
        return barometricPressure;
    }

    public void setBarometricPressure(double barometricPressure) {
        this.barometricPressure = barometricPressure;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FanGroup) {
            FanGroup temp = (FanGroup) obj;
            if (this.temperature == temp.temperature
                    && this.humidity == temp.humidity
                    && this.barometricPressure == temp.barometricPressure
                    && this.fans.equals(temp.fans)) {
                return true;
            }
        }

        return false;
    }

    public String getManagerString() {
        String result = "Manager;";

        for (int i = 0; i < fans.size(); i++) {
            result = result
                    + String.format("f%d[%d,%.2f];", (i + 1),
                            fans.get(i).getSpeed(),
                            fans.get(i).getFreq());
        }

        result = result + "end\n";

        return result;
    }

    public String getPrototypeString() {
        String result = "Proto;"
                + String.format("t[%.1f];h[%.1f];p[%.2f];",
                        temperature,
                        humidity,
                        barometricPressure);

        for (int i = 0; i < fans.size(); i++) {
            result = result
                    + String.format("f%d[%.1f];", (i + 1),
                            fans.get(i).getTemperature());
        }

        result = result + "end\n";

        return result;
    }

    
}
