package net.maneschijn.bleep.core;

public abstract class Source {

	protected Control gain;
	protected double clock = 0;
	protected byte lastSample = 0;

	public Source() {
		super();
	}

	public final Control getGain() {
		return gain;
	}

	//pass by reference!
	public final void setGain(Control gain) {
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