package net.maneschijn.bleep;
import static net.maneschijn.bleep.Util.*;

public class SquareOsc extends Osc {

	public SquareOsc(double freq, double gain, Source lfo, Source envelope){
		super( freq,  gain,  lfo, envelope);
	}
	
	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope()*gain*255*((int)((clock++/(SAMPLERATE/2/getShiftedFreq()))+1)%2)-128);
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}
