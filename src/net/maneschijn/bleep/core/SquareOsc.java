package net.maneschijn.bleep.core;
import static net.maneschijn.bleep.core.Util.SAMPLERATE;

public class SquareOsc extends Osc {

	public SquareOsc(Controller controller, Source lfo, Source envelope, Control detune){
		super( controller,  lfo, envelope, detune);
	}
	
	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope()*gain.getValue()*(255*((int)((clock++/(SAMPLERATE/2/getShiftedFreq()))+1)%2)-128));
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}
