package net.maneschijn.bleep;

import static net.maneschijn.bleep.Util.SAMPLERATE;
import static net.maneschijn.bleep.Util.getFreq;

//import java.applet.Applet;
//import java.awt.Graphics;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MonoSynthFX extends Application implements Runnable {

	private static final long serialVersionUID = 1L;

	private ZeroCrossingDetector zerocross = new ZeroCrossingDetector();

	private int transpose = 0;

	// onderstaande verpakken in een new Patch=...
	// patches moeten controls hebben waar noteon/off. mods en instellingen heen
	// kunnen
	// of een knopje square/sine?
	protected Source lesley = new SineOsc(0.5, 0.5, null, null);
	protected Source lfo = new SineOsc(4, 1, lesley, null);
	// protected Osc osc1 = new SineOsc(getFreq(69), 0, lfo, lesley.buffered());
	protected Osc osc1 = new SawOsc(getFreq(69), 0, null, null);
	protected Osc osc2 = new SawOsc(getFreq(71), 0, null, null);
	protected Source noise = new Noise();
	// einde patch

	public Engine eng = new Engine(osc1, osc2);

	private Controller controller = new Controller(osc1);

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
		keymap2.put(KeyCode.S, 74);
		keymap2.put(KeyCode.X, 76);
		keymap2.put(KeyCode.D, 77);
		keymap2.put(KeyCode.C, 79);
		keymap2.put(KeyCode.V, 81);
		keymap2.put(KeyCode.G, 83);
		keymap2.put(KeyCode.B, 84);
		keymap2.put(KeyCode.H, 86);
		keymap2.put(KeyCode.N, 88);
		keymap2.put(KeyCode.J, 73);
		keymap2.put(KeyCode.M, 75);
		keymap2.put(KeyCode.COMMA, 78);
		keymap2.put(KeyCode.L, 80);
		keymap2.put(KeyCode.PERIOD, 82);
		keymap2.put(KeyCode.SEMICOLON, 85);
		keymap2.put(KeyCode.SLASH, 87);

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

	Button bliep;
	Label label;

	public void start(Stage stage) {
		// gave Java FX shit gebeurt hier!
		bliep = new Button("A");
		bliep.setOnMousePressed(e -> osc1.setGain(1.0D));
		label = new Label("push me!");
		GridPane root = new GridPane();
		root.setOnKeyPressed(e -> e.getCode());
		root.add(label, 0, 0);
		root.add(bliep, 0, 1);
		Scene scene = new Scene(root, 300, 250);
		stage.setTitle("Borkotron");
		stage.setScene(scene);
		stage.show();
	}

	public MonoSynthFX() {
		fillKeyMap();
		fillKeyMap2();
		eng.start();
		controller.connectToMidiDevice();

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
				System.out.println("Note on: " + note);
				osc1.setFreq(getFreq(transpose + note));
				osc1.setGain(1.0D);
			}
		}
	}

	// private void keyboard(Applet app) {
	// app.addKeyListener(new KeyListener() {
	//
	// @Override
	// public void keyReleased(KeyEvent e) {
	// System.out.println("key released: " + e.getKeyCode());
	// osc1.setGain(0.0D);
	// osc2.setGain(0.0D);
	//
	// }
	//
	// @Override
	// public void keyPressed(KeyEvent e) {
	// int scancode = e.getKeyCode();
	// System.out.println("key pressed: " + scancode);
	// if (scancode == 107) {
	// transposeUp();
	// } else if (scancode == 109) {
	// transposeDown();
	// } else {
	// int note = getNote(scancode);
	// if (note != 0) {
	// //note on/off versturen naar controller iets
	// System.out.println("Note on: " + note);
	// osc1.setFreq(getFreq(transpose + note));
	// osc1.setGain(1.0D);
	//// osc2.setFreq(getFreq(transpose + note + 5) );
	//// osc2.setGain(1.0D);
	// }
	// }
	// repaint();
	// }
	//
	// @Override
	// public void keyTyped(KeyEvent e) {
	//
	// }
	// });
	// }
	//
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
	//
	// }

	@Override
	public void run() {
		// while (true) {
		// this.repaint();
		// }

	}

	public static void main(String[] args) {
		launch(args);
	}
}