package net.maneschijn.bleep.core;

public abstract class Filter extends Source {

	protected Source source;

	public Filter(Source source){
		this.source=source;
	}
	
	public Source getSource() {
		return source;
	}
	public void setSource(Source source) {
		this.source = source;
	}
	
	

}
