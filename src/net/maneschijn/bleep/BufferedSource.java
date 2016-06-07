package net.maneschijn.bleep;

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
