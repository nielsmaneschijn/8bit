package net.maneschijn.bleep.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Control;
import net.maneschijn.bleep.core.Router;
import net.maneschijn.bleep.core.SawOsc;
import net.maneschijn.bleep.core.SineOsc;
import net.maneschijn.bleep.core.Source;
import net.maneschijn.bleep.core.SourceUI;
import net.maneschijn.bleep.core.SquareOsc;

public class OscUI extends GridPane implements SourceUI {
	private Source leslie;
	private Source lfo;
	private Router router;
	private MixUI mixer;

	ControlUI lfoFreq = new ControlUI("LFO freq", 0, 30, 4);
	ControlUI lfoGain = new ControlUI("LFO gain", 0, 1, 1);
	ControlUI leslieFreq = new ControlUI("Leslie freq", 0, 20, 0.5);
	ControlUI leslieGain = new ControlUI("Leslie gain", 0, 1, 0.5);

	public OscUI(String title, Control freq, Control gain) {
		Label label = new Label(title);
		this.add(label, 0, 0);
		
		init(freq, gain);
		
		addOscSelector(freq, gain);
		
		mixer = new MixUI(router);
		this.add(lfoFreq, 1, 1);
		this.add(lfoGain, 2, 1);
		this.add(leslieFreq, 3, 1);
		this.add(leslieGain, 4, 1);
		this.add(mixer,  10, 1);
	}

	private void addOscSelector(Control freq, Control gain) {
		
		GridPane buttons = new GridPane();
		Button sine = new Button("~~~~");
		Button square = new Button("_-_-");
		Button saw = new Button("///");
		Button triangle = new Button("\\/\\/\\");
		
		sine.setOnAction(e -> router.setSrc(new SineOsc(freq, gain, lfo, leslie.buffered())));
		square.setOnAction(e -> router.setSrc(new SquareOsc(freq, gain, lfo, leslie.buffered())));
		saw.setOnAction(e -> router.setSrc(new SawOsc(freq, gain, lfo, leslie.buffered())));
//		triangle.setOnAction(e -> router.setSrc(new TriangleOsc(freq, gain, lfo, lesley.buffered())));
		triangle.setOnAction(e -> router.setSrc(new SawOsc(freq, gain, null,null)));
		
		
		buttons.add(sine, 0, 1);
		buttons.add(square, 0, 2);
		buttons.add(saw, 0, 3);
		buttons.add(triangle, 0, 4);
		this.add(buttons,  0, 1);
	}

	private void init(Control freq, Control gain) {
		leslie = new SineOsc(leslieFreq.getControl(), leslieGain.getControl(), null, null);
		lfo = new SineOsc(lfoFreq.getControl(), lfoGain.getControl(), leslie, null);
		router = new Router(new SineOsc(freq, gain, lfo, leslie.buffered()));
	}
	
	public Source getSource() {
		return mixer.getSource();
	}
}
