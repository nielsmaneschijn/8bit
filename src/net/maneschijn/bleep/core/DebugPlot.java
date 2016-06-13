package net.maneschijn.bleep.core;

import java.applet.Applet;
import java.awt.Graphics;

public class DebugPlot extends Applet {

	private static final long serialVersionUID = 1L;

	public DebugPlot() {
		this.setSize(1900, 256);
	}

	@Override
	public void paint(Graphics g) {
		Osc lfo2 = new SineOsc(0.1, 1, null, null);
		Osc lfo = new SineOsc(40, 1, lfo2, null);
//		Osc osc = new SquareOsc(440, 1, lfo, null);
		Osc osc = new SawOsc(440, 1, lfo, null);

		Source noise = new Noise();
		int oldy = 0;
		for (int x = 0; x < 2000; x++) {
			 int y = (byte) osc.getSample()+128;
//			int y = (byte) noise.getSample() + 128;
			g.drawLine(x - 1, oldy, x, y);
			oldy = y;

		}
		// super.paint(g);
	}
}
