package net.maneschijn.bleep;


public class ZeroCrossingDetector {
	private int prev=127;

	public boolean isZeroCrossed(byte curr){
		boolean retval = (prev<0) && (curr>=0);
		prev = curr;
		return retval;
	}
}
