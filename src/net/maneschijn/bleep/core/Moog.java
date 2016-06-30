package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.FilterMode.*;

//Paul Kellet:
//stolen from https://www.kvraudio.com/forum/viewtopic.php?t=144625
public class Moog extends Filter {

	Control frequency;// = 0.1;
	Control resonance; // = 0.5;
	double q, p, f;
	

	double t1, t2, t3, t4, b0 = 1, b1 = 1, b2 = 1, b3 = 1, b4 = 1;
	double in;
	FilterMode mode;

	public Moog(Control frequency, Control resonance, FilterMode mode, Source source) {
		super(source);
		this.frequency = frequency;
		this.resonance = resonance;
		this.mode = mode;
	}

	public FilterMode getMode() {
		return mode;
	}
	
	public void setMode(FilterMode mode) {
		this.mode = mode;
	}

	@Override
	public byte getSample() {
		// return source.getSample();
		in = (double) source.getSample() / 128;

		q = 1.0f - frequency.getValue();
		p = frequency.getValue() + 0.8f * frequency.getValue() * q;
		f = p + p - 1.0f;
		q = resonance.getValue() * (1.0f + 0.5f * q * (1.0f - q + 5.6f * q * q));

		// Filter (in [-1.0...+1.0])

		in -= q * b4; // feedback
		t1 = b1;
		b1 = (in + b0) * p - b1 * f;
		t2 = b2;
		b2 = (b1 + t1) * p - b2 * f;
		t1 = b3;
		b3 = (b2 + t2) * p - b3 * f;
		b4 = (b3 + t1) * p - b4 * f;
		b4 = b4 - b4 * b4 * b4 * 0.166667f; // clipping
		b0 = in;

		// Lowpass output: b4
		// Highpass output: in - b4;
		// Bandpass output: 3.0f * (b3 - b4);

		if (mode == HP) {
			return (byte) ((in - b4) * 127);
		} else if (mode == LP) {
			return (byte) ((b4) * 127);
		} else if (mode == BP) {
			return (byte) ((3.0f * (b3 - b4)) * 127);
		} else {
			return 0;
		}
	}

}
