package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.SAMPLERATE;

public class SineOsc extends Osc {

	public SineOsc(Controller controller, Source lfo, Source envelope, Control detune) {
		super(controller, lfo, envelope, detune);
	}

	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope() * gain.getValue() * 127
				* Math.sin(Math.PI * 2 * (clock++ / (SAMPLERATE / getShiftedFreq()))));
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}