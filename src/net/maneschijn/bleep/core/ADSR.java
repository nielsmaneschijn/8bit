package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;
public class ADSR extends Source {

	private Control A,D,S,R;
	
	public ADSR(Control A, Control D, Control S, Control R){
		this.A=A;
		this.D=D;
		this.S=S;
		this.R=R;
		this.gain=new Control(1);
	}
	
	@Override
	public byte getSample() {
		if (++clock/SAMPLERATE<A.getValue()) {
			return (byte) (gain.getValue()*127*clock/SAMPLERATE/A.getValue());
		} else if (clock/SAMPLERATE<A.getValue()+D.getValue()) {
			return (byte) (gain.getValue()*127*(1-(clock/SAMPLERATE-A.getValue())/D.getValue()));
		} else {
			return 0;
		}
	}

}
