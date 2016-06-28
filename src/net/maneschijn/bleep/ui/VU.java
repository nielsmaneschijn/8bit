package net.maneschijn.bleep.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.maneschijn.bleep.core.Engine;
import net.maneschijn.bleep.core.Source;

public class VU extends GridPane {

	private Canvas canvas;

	public VU(Object source, boolean barmode) {
		if (barmode) {
			canvas = new Canvas(10, 127);
		} else {
			canvas = new Canvas(10, 10);
		}
		this.add(canvas, 0, 0);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long so) {
				double power=0;
				if (source instanceof Source) {
					power = Math.sqrt(Math.abs((double) ((Source) source).getLastSample() / 128));
				} else if (source instanceof Engine) {
					power = Math.sqrt(Math.abs((double) ((Engine) source).getLastSample() / 128));
				}
				// System.out.println(power);

				if (barmode) {
					gc.clearRect(0, 0, 10, 127);
					for (int y = 0; y < (int) (127 * power); y++) {
						gc.setFill(Color.hsb(135 - (y), 1, 1));
						gc.fillRect(0, 127 - y, 10, 1);
					}
				} else {
					gc.setFill(Color.hsb(135 - (135 * power), 1, power));
					gc.fillOval(0, 0, 10, 10);
				}
			}
		};

		timer.start();
	}

}
