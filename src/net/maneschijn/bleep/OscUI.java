package net.maneschijn.bleep;

import static net.maneschijn.bleep.Util.getFreq;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class OscUI extends GridPane {
	private Source lesley;
	private Source lfo;
	private Osc osc;
	
	public Osc getOsc() {
		return osc;
	}

	public void setOsc(Osc osc) {
		this.osc = osc;
	}

	public OscUI(String title, Control freq, Control gain) {
		Label label = new Label(title);
		this.add(label, 0, 0);
		
		lesley = new SineOsc(new Control(0.5), new Control(0.5), null, null);
		lfo = new SineOsc(new Control(4), new Control(1), lesley, null);
		osc = new SineOsc(freq, gain, lfo, lesley.buffered());
		
	}
	
}
