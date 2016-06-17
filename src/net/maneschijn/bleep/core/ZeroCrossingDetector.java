package net.maneschijn.bleep.core;

public class ZeroCrossingDetector {
	private int prev = 127;
	private int zeroPoint = 0;

	public ZeroCrossingDetector() {
		this(0);
	}
	public ZeroCrossingDetector(int zeroPoint) {
		this.zeroPoint = zeroPoint;
	}

	public boolean isZeroCrossed(byte curr) {
		boolean retval = (prev < zeroPoint) && (curr >= zeroPoint);
		if (retval){System.out.println(""+prev+" "+curr);}
		prev = curr;
		return retval;
	}
}
