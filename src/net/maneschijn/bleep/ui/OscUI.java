package net.maneschijn.bleep.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Controller;
import net.maneschijn.bleep.core.Multiplier;
import net.maneschijn.bleep.core.Router;
import net.maneschijn.bleep.core.SawOsc;
import net.maneschijn.bleep.core.SineOsc;
import net.maneschijn.bleep.core.Source;
import net.maneschijn.bleep.core.SquareOsc;
import net.maneschijn.bleep.core.TriangleOsc;

public class OscUI extends GridPane implements SourceUI {
	private Source leslie;
	private Source lfo;
	private Router router;
	private MixUI mixer;
	private BitcrusherUI bitcrusher;

	ControlUI lfoFreq = new ControlUI("LFO freq", 0, 30, 4);
	ControlUI lfoGain = new ControlUI("LFO gain", 0, 1, 0);
	ControlUI leslieFreq = new ControlUI("Leslie freq", 0, 20, 0.5);
	ControlUI leslieGain = new ControlUI("Leslie gain", 0, 1, 0);
	ControlUI detune = new ControlUI("Detune", -1, 1, 0);

	Controller lfoController = new Controller(lfoFreq.getControl(), lfoGain.getControl());
	Controller leslieController = new Controller(leslieFreq.getControl(), leslieGain.getControl());
	
	ADSRUI adsr;
	
	public OscUI(String title, Controller controller) {
		this.setPadding(new Insets(15));
		this.setStyle("-fx-border-color: lightgray");
		Label label = new Label(title);
		this.add(label, 0, 0);

		addOscSelector(controller);

		adsr = new ADSRUI(controller);
		init(controller);
		mixer = new MixUI(router);
		bitcrusher = new BitcrusherUI(mixer.getSource());
		VU vu = new VU(bitcrusher.getSource(), false);
		this.add(vu, 0, 2);
		this.add(lfoFreq, 1, 1);
		this.add(lfoGain, 2, 1);
		this.add(leslieFreq, 3, 1);
		this.add(leslieGain, 4, 1);
		this.add(detune, 5, 1);
		this.add(bitcrusher, 6, 1);
		this.add(adsr, 7,1);
		this.add(mixer, 10, 1);

	}

	private void addOscSelector(Controller controller) {

		GridPane buttons = new GridPane();
		Label shape = new Label("Shape");
		Button sine = new Button("~~~~");
		Button square = new Button("_-_-");
		Button saw = new Button("///");
		Button triangle = new Button("\\/\\/\\");

		sine.setOnAction(e -> router.setSrc(new SineOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		square.setOnAction(e -> router.setSrc(new SquareOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		saw.setOnAction(e -> router.setSrc(new SawOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()), detune.getControl())));
		triangle.setOnAction(e -> router.setSrc(new TriangleOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()), detune.getControl())));

		buttons.add(shape, 0, 0);
		buttons.add(sine, 0, 1);
		buttons.add(square, 0, 2);
		buttons.add(saw, 0, 3);
		buttons.add(triangle, 0, 4);
		this.add(buttons, 0, 1);
	}

	private void init(Controller controller) {
		leslie = new SineOsc(leslieController, null, null, null);
		lfo = new SineOsc(lfoController, leslie, null, null);
		router = new Router(new SineOsc(controller, lfo,new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl()));
	}

	public Source getSource() {
		return bitcrusher.getSource();
	}
}
