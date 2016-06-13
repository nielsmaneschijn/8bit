package net.maneschijn.bleep.core;

public class Noise extends Source {

	@Override
	public byte getSample() {
		byte retval = (byte)(Math.random()*255-128);
		lastSample = retval;
		return retval;
	}

}
