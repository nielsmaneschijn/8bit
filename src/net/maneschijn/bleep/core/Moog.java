package net.maneschijn.bleep.core;

//Paul Kellet:
//stolen from https://www.kvraudio.com/forum/viewtopic.php?t=144625
public class Moog extends Filter {
	
	double frequency = 0.1;
	double resonance = 0.5;
	double q, p, f;
	double t1, t2, t3, t4, b0 = 1, b1 = 1, b2 = 1, b3 = 1, b4 = 1;
	double in;

	public Moog(Source source) {
		super(source);
	}

	@Override
	public byte getSample() {
//		return source.getSample();
		in = (double)source.getSample()/128;

		q = 1.0f - frequency;
		p = frequency + 0.8f * frequency * q;
		f = p + p - 1.0f;
		q = resonance * (1.0f + 0.5f * q * (1.0f - q + 5.6f * q * q));

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

		return (byte) ((in-b4)*127);
	}

}
