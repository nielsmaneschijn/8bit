package net.maneschijn.bleep;

abstract public class Osc extends Source implements Mod {
	protected double freq = 440;
	protected Source lfo;
	protected Source envelope;
	private ZeroCrossingDetector zerocross;

	public Osc() {
	}

	public Osc(double freq, double gain, Source lfo, Source envelope) {
		this.freq = freq;
		this.gain = gain;
		this.lfo = lfo;
		this.envelope = envelope;
		this.zerocross = new ZeroCrossingDetector();
	}

	public double getShiftedFreq() {
		return (lfo != null) ? freq * Math.pow(2D, ((double) lfo.getSample()) / 128D) : freq;
	}

	protected void resetClock(byte curr) {
		if (this.zerocross.isZeroCrossed(curr)) {
			clock = 1;
		}
	}

	public double getEnvelope() {
		return (envelope != null) ? (128D + envelope.getSample()) / 255D : 1.0D;
	}

	public final double getFreq() {
		return freq;
	}

	public final void setFreq(double freq) {
		this.freq = freq;
	}

}
