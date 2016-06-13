package net.maneschijn.bleep.core;

public class Router extends Source {

	private Source src;
	
	public Router(Source src) {
		this.setSrc(src);
	}
	
	
	@Override
	public byte getSample() {
		return src.getSample();
	}

	public byte getLastSample() {
		return src.getLastSample();
	}


	public Source getSrc() {
		return src;
	}


	public void setSrc(Source src) {
		this.src = src;
	}

}
