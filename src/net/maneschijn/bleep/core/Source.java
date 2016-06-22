package net.maneschijn.bleep.core;

public abstract class Source {

	protected Control gain;
	protected long clock = 0;
	protected byte lastSample = 0;
	protected long noteOffClock;

	public Source() {
	};

	public Source(Controller controller) {
		controller.subscribe(this);
		gain = controller.getGainControl();
	}

	public final Control getGain() {
		return gain;
	}

	// pass by reference!
	public final void setGain(Control gain) {
		this.gain = gain;
	}

	public final Source buffered() {
		return new BufferedSource(this);
	}

	// haha stack overflow
	// maar hoe loos ik die haakjes dan?
	// public final BufferedSource buffered = new BufferedSource(this);

	// byte = tussen -128 en 127
	public abstract byte getSample();

	public byte getLastSample() {
		return lastSample;
	}

	public void noteOn() {
		System.out.println("note on " + this);
		noteOffClock = -1;
		clock = 0;
	}

	public void noteOff() {
		System.out.println("note off " + this);
		noteOffClock = clock;
	}

}