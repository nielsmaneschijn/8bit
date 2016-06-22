package net.maneschijn.bleep.core;

abstract public class Osc extends Source implements Mod {
	protected Control freq;
	protected Source lfo;
	protected Source envelope;
	protected Control detune;
	protected ZeroCrossingDetector zerocross;

	// let op geen pass by value!!
	public Osc(Controller controller, Source lfo, Source envelope, Control detune) {
		super(controller);
		this.freq = controller.getFreqControl();
		this.lfo = lfo;
		this.envelope = envelope;
		this.detune = detune;
		this.zerocross = new ZeroCrossingDetector();
	}

	public double getShiftedFreq() {
		return ((lfo != null) ? freq.getValue() * Math.pow(2D, ((double) lfo.getSample()) / 128D) : freq.getValue())
				* (detune != null ? Math.pow(2D, detune.getValue()) : 1);
	}

	protected void resetClock(byte curr) {
		if (this.zerocross.isZeroCrossed(curr)) {
			clock = 1;
		}
	}

	public double getEnvelope() {
		return (envelope != null) ? (128D + envelope.getSample()) / 255D : 1.0D;
	}

	public final Control getFreq() {
		return freq;
	}

	public final void setFreq(Control freq) {
		this.freq = freq;
	}

	//ADSR subscribeert zelf dus niet meer nodig?
//	@Override
//	public void noteOn() {
////		super.noteOn();
//		System.out.println("note on " + this);
////		if (envelope != null) {envelope.noteOn();}
//		//lfo niet
//	}
//
//	@Override
//	public void noteOff() {
////		super.noteOff();
//		System.out.println("note off " + this);
////		if (envelope != null) {envelope.noteOff();}
//		//lfo niet
//	}

	
}
