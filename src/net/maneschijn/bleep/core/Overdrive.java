package net.maneschijn.bleep.core;

public class Overdrive extends Filter {

	private boolean clipMode;

	public Overdrive(Control gain, boolean clipMode, Source source) {
		super(source);
		this.gain = gain;
		this.clipMode = clipMode;
	}

	public boolean isClipMode() {
		return clipMode;
	}

	public void setClipMode(boolean clipMode) {
		this.clipMode = clipMode;
	}

	@Override
	public byte getSample() {
		if (clipMode) {
			lastSample =  (byte) Math.max(Byte.MIN_VALUE, Math.min(Byte.MAX_VALUE, gain.getValue()*source.getSample()));
		} else {
			lastSample = (byte) (Math.atan(gain.getValue()*source.getSample()/127)/Math.PI*2*127);
		}
		return lastSample;
	}

}
