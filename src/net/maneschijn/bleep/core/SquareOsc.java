package net.maneschijn.bleep.core;
import static net.maneschijn.bleep.core.Util.*;

public class SquareOsc extends Osc {

	public SquareOsc(Control freq, Control gain, Source lfo, Source envelope){
		super( freq,  gain,  lfo, envelope);
	}
	
	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope()*gain.getValue()*255*((int)((clock++/(SAMPLERATE/2/getShiftedFreq()))+1)%2)-128);
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}
