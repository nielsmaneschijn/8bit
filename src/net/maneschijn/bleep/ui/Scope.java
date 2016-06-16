package net.maneschijn.bleep.ui;

import static net.maneschijn.bleep.core.Util.EXTERNAL_BUFFER_SIZE;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.maneschijn.bleep.core.Engine;

public class Scope extends GridPane {

	private Canvas canvas = new Canvas(256, 256);
	private GraphicsContext gc = canvas.getGraphicsContext2D();

	public Scope(Engine eng) {
		this.add(canvas, 0, 0);

		gc.setStroke(Color.BLACK);
		gc.setFill(Color.BLUE);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long so) {
				// try {
				// Thread.sleep(5000);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
//				while (true) {

					if (eng != null) {
						byte[] samples = eng.getLastSamples();
						if (samples != null) {
							gc.clearRect(0, 0, 256, 256);
							gc.beginPath();
							gc.moveTo(0, samples[0] + 128);
							for (int x = 1; x < EXTERNAL_BUFFER_SIZE; x++) {

								gc.lineTo((double) x, (double) samples[x] + 128);
							}
							gc.stroke();
						}
					}

//				}
			}
		};

		timer.start();

	}
}
