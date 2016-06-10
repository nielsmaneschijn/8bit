package net.maneschijn.bleep;

import static net.maneschijn.bleep.Util.SAMPLERATE;
import static net.maneschijn.bleep.Util.getFreq;

//import java.applet.Applet;
//import java.awt.Graphics;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonoSynthFX extends Application implements Runnable {

	private static final String ZWART = "-fx-background-color: #000000;";
	private static final String WIT = "-fx-background-color: #FFFFFF;";

	private static final long serialVersionUID = 1L;

	private ZeroCrossingDetector zerocross = new ZeroCrossingDetector();

	private int transpose = 0;
	
	private Control gain = new Control(1.0D);

	private Control freq = new Control(440D);
	
	// onderstaande verpakken in een new Patch=...
	// patches moeten controls hebben waar noteon/off. mods en instellingen heen
	// kunnen
	// of een knopje square/sine?
//	protected Source lesley = new SineOsc(0.5, 0.5, null, null);
//	protected Source lfo = new SineOsc(4, 1, lesley, null);
//	 protected Osc osc1 = new SineOsc(getFreq(69), 0, lfo, lesley.buffered());
//	protected Osc osc1 = new SawOsc(getFreq(69), 0, null, null);
//	protected Osc osc2 = new SawOsc(getFreq(71), 0, null, null);
//	protected Source noise = new Noise();
	// einde patch



	private HashMap<Integer, Integer> keymap = new HashMap<>();
	private HashMap<KeyCode, Integer> keymap2 = new HashMap<>();

	private void fillKeyMap() {
		keymap.put(90, 72); // getver
		keymap.put(88, 74);
		keymap.put(67, 76);
		keymap.put(86, 77);
		keymap.put(66, 79);
		keymap.put(78, 81);
		keymap.put(77, 83);
		keymap.put(44, 84);
		keymap.put(46, 86);
		keymap.put(47, 88);
		keymap.put(83, 73);
		keymap.put(68, 75);
		keymap.put(71, 78);
		keymap.put(72, 80);
		keymap.put(74, 82);
		keymap.put(76, 85);
		keymap.put(59, 87);

	}

	private void fillKeyMap2() {
		keymap2.put(KeyCode.Z, 72); // getver
		keymap2.put(KeyCode.S, 73);
		keymap2.put(KeyCode.X, 74);
		keymap2.put(KeyCode.D, 75);
		keymap2.put(KeyCode.C, 76);
		keymap2.put(KeyCode.V, 77);
		keymap2.put(KeyCode.G, 78);
		keymap2.put(KeyCode.B, 79);
		keymap2.put(KeyCode.H, 80);
		keymap2.put(KeyCode.N, 81);
		keymap2.put(KeyCode.J, 82);
		keymap2.put(KeyCode.M, 83);
		keymap2.put(KeyCode.COMMA, 84);
		keymap2.put(KeyCode.L, 85);
		keymap2.put(KeyCode.PERIOD, 86);
		keymap2.put(KeyCode.SEMICOLON, 87);
		keymap2.put(KeyCode.SLASH, 88);

	}

	private int getNote(KeyCode scancode) {
		Integer retval = keymap2.get(scancode);
		if (retval != null) {
			return retval;
		} else {
			return 0;
		}
	}

	private void transposeUp() {
		transpose += 12;
	}

	private void transposeDown() {
		transpose -= 12;
	}

	Label label;

	private void addKey(int note, GridPane pane, int col, int row, String style) {
		Button key = new Button();
		key.setStyle(style);
		key.setMaxWidth(Double.MAX_VALUE);
		key.setOnMousePressed(e -> noteOn(note));
		key.setOnMouseReleased(e -> noteOff(note));
		pane.add(key, col, row);
	}

	public void start(Stage stage) {
		// gave Java FX shit gebeurt hier!
		label = new Label("push me!");
		GridPane root = new GridPane();

		// toetsen keyboard afvangen
		addKeyHandlers(root);

		root.add(label, 0, 0);

		drawKeys(root);
		
		drawVolumeSlider(root);
		
		OscUI osc1 = new OscUI("Osc1", freq, gain);
		OscUI osc2 = new OscUI("Osc2", freq, gain);
		
		root.add(osc1, 0, 2);
		root.add(osc2, 1, 2);
		
		Scene scene = new Scene(root, 300, 250);
		stage.setTitle("Borkotron");
		stage.setScene(scene);
		stage.show();

		//controller en engine aanslingeren
//		Controller controller = new Controller(osc1.getOsc());
		Engine eng = new Engine(osc1.getOsc(), osc2.getOsc());
		eng.start();
//		controller.connectToMidiDevice();
	}

	private void addKeyHandlers(GridPane pane) {
		pane.setOnKeyPressed(e -> keyPressed(e));
		pane.setOnKeyReleased(e -> noteOff(0));
	}

	private void drawVolumeSlider(GridPane pane) {
		Slider volume = new Slider(0,1,1);
		volume.setShowTickMarks(true);
	    volume.setMajorTickUnit(10);
		volume.setOrientation(Orientation.VERTICAL);
		volume.valueProperty().addListener((observable, oldval, newval) -> { gain.setValue(newval.doubleValue()); } ); 
		pane.add(volume, 5, 2);
	}

	private void drawKeys(GridPane pane) {
		GridPane keys = new GridPane();
		ColumnConstraints wit = new ColumnConstraints();
		ColumnConstraints zwart = new ColumnConstraints();
		zwart.setHalignment(HPos.RIGHT);
		pane.getColumnConstraints().add(zwart);
		pane.getColumnConstraints().add(wit);

		// toetsen op scherm
		addKey(72, keys, 1, 1, WIT);
		addKey(74, keys, 3, 1, WIT);
		addKey(76, keys, 5, 1, WIT);
		addKey(77, keys, 6, 1, WIT);
		addKey(79, keys, 8, 1, WIT);
		addKey(81, keys, 10, 1, WIT);
		addKey(83, keys, 12, 1, WIT);
		addKey(84, keys, 13, 1, WIT);

		addKey(73, keys, 2, 0, ZWART);
		addKey(75, keys, 4, 0, ZWART);
		addKey(78, keys, 7, 0, ZWART);
		addKey(80, keys, 9, 0, ZWART);
		addKey(82, keys, 11, 0, ZWART);

		pane.add(keys, 0, 1);
	}

	public MonoSynthFX() {
		fillKeyMap();
		fillKeyMap2();

		Thread visuals = new Thread(this);
		visuals.start();

		// keyboard(this);

	}

	public void keyPressed(KeyEvent e) {
		KeyCode scancode = e.getCode();
		System.out.println("key pressed: " + scancode);
		if (scancode == KeyCode.PLUS) {
			transposeUp();
		} else if (scancode == KeyCode.MINUS) {
			transposeDown();
		} else {
			int note = getNote(scancode);
			if (note != 0) {
				// note on/off versturen naar controller iets
				noteOn(note);
			}
		}
	}

	private void noteOn(int note) {
		System.out.println("Note on: " + note + " " + gain.getValue());
		freq.setValue(getFreq(transpose + note));	
	}

	private void noteOff(int note) {
		System.out.println("Note off: " + note);
		gain.setValue(0D);
	}

	// public void paint(Graphics g) {
	// // dit suckt ass, beter overpompen via een soort buffered stream
	// int prevx = 0, prevy = 0;
	//
	// for (int x = 0; x < 500; x++) {
	//
	// int y = eng.getLastSample() + 128;
	//
	// g.drawLine(prevx, prevy, x, y);
	// prevy = y;
	// prevx = x;
	//
	// Thread.yield();
	// ;
	// }

	@Override
	public void run() {

	}

	public static void main(String[] args) {
		launch(args);
	}
}