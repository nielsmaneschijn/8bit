package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class SawOsc extends Osc {

	public SawOsc(Control freq, Control gain, Source lfo, Source envelope, Control detune) {
		super(freq, gain, lfo, envelope, detune);
	}

	@Override
	public byte getSample() {
//		byte retval = (byte) (getEnvelope() * gain.getValue() *  ((int) ((clock++ * getShiftedFreq() * 256 / SAMPLERATE ) % 256) - 128));
		//fuck de modulo, overflow ook prima
		byte retval = (byte) (getEnvelope() * gain.getValue() * (byte) ((int) ((clock++ * getShiftedFreq() * 256 / SAMPLERATE ) )));
		resetClock((byte) (retval-0));
		lastSample = retval;
		return retval;
	}

}
