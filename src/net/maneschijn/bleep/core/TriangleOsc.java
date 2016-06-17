package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class TriangleOsc extends Osc {

	public TriangleOsc(Control freq, Control gain, Source lfo, Source envelope, Control detune) {
		super(freq, gain, lfo, envelope, detune);
		zerocross = new ZeroCrossingDetector(-127);
	}

	@Override
	public byte getSample() {
		// read 'em and weep: saw - (2*saw-1)*square
//		byte retval = (byte) (getEnvelope() * gain.getValue()
//				* ((int) ((2*clock++ * getShiftedFreq() * 256 / SAMPLERATE) % 512
//						- (((int) (clock / (SAMPLERATE / 2 / getShiftedFreq())) % 2)
//								* ((4 *(clock * getShiftedFreq() * 256 / SAMPLERATE) % 1024) - 512)))
//						- 128));
		byte retval = (byte) (getEnvelope() * gain.getValue()
				* (byte) ((int) ((2*clock++ * getShiftedFreq() * 256 / SAMPLERATE) 
						- (((int) (clock / (SAMPLERATE / 2 / getShiftedFreq())+0.5) % 2)
								* ((4 *(clock * getShiftedFreq() * 256 / SAMPLERATE) )-256 )))
						));
		resetClock((byte) (retval-0));//why god why
		lastSample = retval;
		return retval;
	}

}
