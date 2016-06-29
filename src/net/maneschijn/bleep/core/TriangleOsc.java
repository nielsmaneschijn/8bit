package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.SAMPLERATE;

public class TriangleOsc extends Osc {

	public TriangleOsc(Controller controller, Source lfo, Source envelope, Control detune) {
		super(controller, lfo, envelope, detune);
//		zerocross = new ZeroCrossingDetector(-127);
	}

	@Override
	public byte getSample() {
		// read 'em and weep: saw - (2*saw-1)*square
//		byte retval = (byte) (getEnvelope() * gain.getValue()
//				* ((int) ((2*clock++ * getShiftedFreq() * 256 / SAMPLERATE) % 512
//						- (((int) (clock / (SAMPLERATE / 2 / getShiftedFreq())) % 2)
//								* ((4 *(clock * getShiftedFreq() * 256 / SAMPLERATE) % 1024) - 512)))
//						- 128));
		double shiftedFreq = getShiftedFreq(); //anders gaat de lfo 3x te hard!
		byte retval = (byte) (getEnvelope() * gain.getValue()
				* (byte) ((int) ((2*++clock * shiftedFreq * 256 / SAMPLERATE) 
						- (((int) (clock / (SAMPLERATE / 2 / shiftedFreq)+0.5) % 2)
								* ((4 *(clock * shiftedFreq * 256 / SAMPLERATE) )-255 )))
						));
		resetClock((byte) (retval-0));//why god why
		lastSample = retval;
		return retval;
	}

}
