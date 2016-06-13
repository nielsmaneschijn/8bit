package net.maneschijn.bleep.core;

public class Util {
	
	public static final float SAMPLERATE = 44100F; 
	public static final int EXTERNAL_BUFFER_SIZE = 1;
	public static final int BUFFER_SIZE = 4096;

	public static double getFreq(int midinote) {
		return 440.0D * Math.pow(1.0594630943592952645, midinote - 69);
	}

}

// class package subclass world
// public x x x x
// protected x x x
// default x x
// private x
