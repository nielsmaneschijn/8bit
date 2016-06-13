package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class SawOsc extends Osc {

	public SawOsc(Control freq, Control gain, Source lfo, Source envelope) {
		super(freq, gain, lfo, envelope);
	}

	@Override
	public byte getSample() {
//		byte retval = (byte) (getEnvelope() * gain * 255 * ((int) ((clock++ % (SAMPLERATE / 2/  getShiftedFreq())) + 1)) - 128);
		byte retval = (byte) (getEnvelope() * gain.getValue() * 255 * ((int) ((clock++ % (SAMPLERATE / freq.getValue())) + 1)) - 128);
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}
