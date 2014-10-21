package net.maneschijn.bleep;
import static net.maneschijn.bleep.Util.*;

public class SineOsc extends Osc {

	public SineOsc(double freq, double gain, Source lfo, Source envelope){
		super( freq,  gain,  lfo, envelope);
	}
	
	@Override
	public byte getSample() {
		byte retval = (byte) (getEnvelope()*gain*127*Math.sin(Math.PI*2*(clock++/(SAMPLERATE/getShiftedFreq()))));
		resetClock(retval);
		lastSample = retval;
		return retval;
	}

}