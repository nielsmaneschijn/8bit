package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;

public class ADSR extends Source {

	private Control A, D, S, R;
	
//	private long noteOffClock = (long) (0.03*SAMPLERATE);

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
			return (byte) (gain.getValue() * 127 * clock / SAMPLERATE / A.getValue());
		} else if (clock / SAMPLERATE < A.getValue() + D.getValue()) {
			return (byte) (gain.getValue() * 127
					* (1 - ((1 - S.getValue()) / D.getValue()) * (clock / SAMPLERATE - A.getValue())));
		} else if (noteOffClock == -1 || noteOffClock > clock){
			return (byte) (gain.getValue() * 127 * S.getValue());
		} else if (clock - noteOffClock < R.getValue()*SAMPLERATE) {
			return (byte) (gain.getValue() * 127 * (S.getValue()-(S.getValue()/R.getValue()*(clock-noteOffClock)/SAMPLERATE)));
		} else {
			return 0;
		}
	}
	

}
