package net.maneschijn.bleep.core;

/*
 * Source * Source
 * Source * Control
 */

public class Multiplier extends Source {

	private Source source1, source2;
	private Control control;

	public Multiplier(Source source1, Source source2) {
		this.source1 = source1;
		this.source2 = source2;
	}

	public Multiplier(Source source1, Control control) {
		this.source1 = source1;
		this.control = control;
	}

	@Override
	public byte getSample() {
		lastSample = (byte) (control == null ? (source1.getSample() + 128) * (source2.getSample() + 128) / 255 - 128
				: (source1.getSample() + 128) * control.getValue() - 128);
		return lastSample;
	}

}
