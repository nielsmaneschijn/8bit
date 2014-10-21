package net.maneschijn.bleep;

public abstract class Source {

	protected double gain = 1;
	protected double clock = 0;
	protected byte lastSample = 0;

	public Source() {
		super();
	}

	public final double getGain() {
		return gain;
	}

	public final void setGain(double gain) {
		this.gain = gain;
	}

	public final Source buffered() {
		return new BufferedSource(this);
	}

	// haha stack overflow
	// maar hoe loos ik die haakjes dan?
	// public final BufferedSource buffered = new BufferedSource(this);

	public abstract byte getSample();

	public byte getLastSample() {
		return lastSample;
	}

}