package net.maneschijn.bleep.core;

public class Control {

	private double value; //doorgaans tussen 0 en 1

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Control(double value){
		this.value = value;
	}
}
