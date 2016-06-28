package net.maneschijn.bleep.core;

public class Bitcrusher extends Filter {

	private Control bits;

	public Bitcrusher(Control bits, Source source) {
		super(source);
		this.bits = bits;
	}

	@Override
	public byte getSample() {
		lastSample = (byte) ((source.getSample()>>(int)(8-bits.getValue()))<<(int)(8-bits.getValue()));
		return lastSample;
	}

}
