package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.SAMPLERATE;

public class PwmOsc extends Osc {

	public PwmOsc(Controller controller, Source lfo, Source envelope, Control detune, Control dutycycle) {
		super(controller, lfo, envelope, detune, dutycycle);
	}

	@Override
	public byte getSample() {
		if (clock++ < SAMPLERATE / getShiftedFreq() * dutycycle.getValue()) {
			lastSample = (byte) (gain.getValue() * 127 * getEnvelope());
		} else {
			lastSample = (byte) (gain.getValue() * -127 * getEnvelope());
		}
		if (clock > SAMPLERATE / getShiftedFreq()) {
			clock = 0;
		}

		return lastSample;
	}

}
