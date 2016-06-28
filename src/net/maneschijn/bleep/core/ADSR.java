package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class ADSR extends Source implements Mod {
// van -128 tot 127!
// TODO afvangen dat de R te kort wordt als de noteoff voor de S komt
	
	private Control A, D, S, R;

	// private long noteOffClock = (long) (0.03*SAMPLERATE);

	public ADSR(Control A, Control D, Control S, Control R, Controller controller) {
		this.A = A;
		this.D = D;
		this.S = S;
		this.R = R;
		this.gain = new Control(1);
		controller.subscribe(this);
	}

	@Override
	public byte getSample() {
		if (++clock / SAMPLERATE < A.getValue()) {
			lastSample = (byte) (gain.getValue() * 255 * clock / SAMPLERATE / A.getValue() - 128);
		} else if (clock / SAMPLERATE < A.getValue() + D.getValue()) {
			lastSample = (byte) (gain.getValue() * 255
					* (1 - ((1 - S.getValue()) / D.getValue()) * (clock / SAMPLERATE - A.getValue())) - 128);
		} else if (noteOffClock == -1) {// || noteOffClock > clock){
//			System.out.println("S");
			lastSample = (byte) (gain.getValue() * 255 * S.getValue() - 128);
		} else if (clock - noteOffClock < R.getValue() * SAMPLERATE) {
//			System.out.println("R");
			lastSample = (byte) (gain.getValue() * 255
					* (S.getValue() - (S.getValue() / R.getValue() * (clock - noteOffClock) / SAMPLERATE)) - 128);
		} else {
//			System.out.println("adsr klaar");
			lastSample = -128;
		}
		return lastSample;
	}

}
