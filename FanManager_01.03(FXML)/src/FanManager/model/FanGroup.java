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
	public FanGroup(Fan [] fans, double temperature, double humidity, double barometricPressure) {
		super();
		this.fans = new ArrayList<Fan>(Arrays.asList(fans));
		this.temperature = temperature;
		this.humidity = humidity;
		this.barometricPressure = barometricPressure;
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
	public synchronized void setFans(Fan [] fans) {
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

}
