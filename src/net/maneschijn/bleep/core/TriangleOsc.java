package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class TriangleOsc extends Osc {

	public TriangleOsc(Control freq, Control gain, Source lfo, Source envelope, Control detune) {
		super(freq, gain, lfo, envelope, detune);
	}

	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope() * gain.getValue() *  ((int) ((clock++ * getShiftedFreq() * 256 / SAMPLERATE ) % 256 -(((int)((clock/(SAMPLERATE/2/getShiftedFreq()))+1)%2)*2*(clock * getShiftedFreq() * 256 / SAMPLERATE ) % 256)) - 128));
		resetClock((byte) (retval-127));
		lastSample = retval;
		return retval;
	}

}
