package net.maneschijn.bleep;

public class Noise extends Source {

	@Override
	public byte getSample() {
		byte retval = (byte)(Math.random()*255-128);
		lastSample = retval;
		return retval;
	}

}
