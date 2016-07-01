package net.maneschijn.bleep.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Controller;
import net.maneschijn.bleep.core.FilterMode;
import net.maneschijn.bleep.core.Multiplier;
import net.maneschijn.bleep.core.PwmOsc;
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
	ControlUI dutycycle = new ControlUI("Dutycycle", 0, 1, 0.5);

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
		this.add(dutycycle, 1, 1);
		this.add(lfoFreq, 2, 1);
		this.add(lfoGain, 3, 1);
		this.add(leslieFreq, 4, 1);
		this.add(leslieGain, 5, 1);
		this.add(detune, 6, 1);
		this.add(bitcrusher, 7, 1);
		this.add(adsr, 8,1);
		this.add(mixer, 10, 1);

	}

	private void addOscSelector(Controller controller) {
		ToggleGroup tg = new ToggleGroup();
		GridPane buttons = new GridPane();
		Label shape = new Label("Shape");
		
//		Button sine = new Button("~~~~");
//		Button square = new Button("_-_-");
//		Button saw = new Button("///");
//		Button triangle = new Button("\\/\\/\\");
//		Button pwm= new Button("__-__-");
//
//		sine.setOnAction(e -> router.setSrc(new SineOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
//		square.setOnAction(e -> router.setSrc(new SquareOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
//		saw.setOnAction(e -> router.setSrc(new SawOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()), detune.getControl())));
//		triangle.setOnAction(e -> router.setSrc(new TriangleOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()), detune.getControl())));
//		pwm.setOnAction(e -> router.setSrc(new PwmOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()), detune.getControl(), dutycycle.getControl())));
//
		buttons.add(shape, 0, 0);
//		buttons.add(sine, 0, 1);
//		buttons.add(square, 0, 2);
//		buttons.add(saw, 0, 3);
//		buttons.add(triangle, 0, 4);
//		buttons.add(pwm, 0, 5);
		
		addButton(buttons,tg,0,1,"~~~~",true, e -> router.setSrc(new SineOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		addButton(buttons,tg,0,2,"_-_-",false, e -> router.setSrc(new SquareOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		addButton(buttons,tg,0,3,"///",false, e -> router.setSrc(new SawOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		addButton(buttons,tg,0,4,"\\/\\/\\",false, e -> router.setSrc(new TriangleOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl())));
		addButton(buttons,tg,0,5,"__-__-",false, e -> router.setSrc(new PwmOsc(controller, lfo, new Multiplier(leslie.buffered(), adsr.getSource()),detune.getControl(), dutycycle.getControl())));
		this.add(buttons, 0, 1);
	}

	private void addButton(GridPane buttons,ToggleGroup tg, int row, int col, String label, boolean initstate, EventHandler<ActionEvent> eventHandler) {
		RadioButton button = new RadioButton(label);
		button.setToggleGroup(tg);
		button.getStyleClass().remove("radio-button");
		button.getStyleClass().add("toggle-button");
		button.setOnAction(eventHandler);
		button.setSelected(initstate);
		buttons.add(button, row, col);
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
