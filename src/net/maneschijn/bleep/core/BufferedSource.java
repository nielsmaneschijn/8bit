package net.maneschijn.bleep.core;

public class BufferedSource extends Source {

	private Source source;
	
	public BufferedSource(Source source) {
		this.source = source;
	}
	
	@Override
	public byte getSample() {
		return this.source.getLastSample();
	}

}
