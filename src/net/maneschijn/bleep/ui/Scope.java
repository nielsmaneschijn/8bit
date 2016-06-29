package net.maneschijn.bleep.ui;

import static net.maneschijn.bleep.core.Util.EXTERNAL_BUFFER_SIZE;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.maneschijn.bleep.core.Engine;

public class Scope extends GridPane {

	private Canvas canvas = new Canvas(256, 256);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private boolean zeroMode = false;

	public Scope(Engine eng) {
		Button toggleZero = new Button("0");
		toggleZero.setOnAction(e -> {
			zeroMode = !zeroMode;
			toggleZero.setStyle(zeroMode ? "-fx-background-color: #33FF33;" : "");
		});
		this.add(toggleZero, 0, 1);
		this.add(canvas, 0, 0);
		this.setStyle("-fx-background-color: black, linear-gradient(from 0.5px 0px to 10.5px 0px, repeat, darkgreen 5%, transparent 5%), linear-gradient(from 0px 0.5px to 0px 10.5px, repeat, darkgreen 5%, transparent 5%);");

		gc.setStroke(Color.LIGHTGREEN);

		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long so) {

				if (eng != null) {
					byte[] samples = eng.getLastSamples();
					if (samples != null) {
						gc.clearRect(0, 0, 256, 256);
						gc.beginPath();
						gc.moveTo(0, zeroMode ? 0 : samples[0] + 128);
						int zeroX = 0;
						for (int x = 1; x < EXTERNAL_BUFFER_SIZE; x++) {
							if (!zeroMode || zeroX != 0) {
								gc.lineTo((double) x - zeroX, (double) 128 - samples[x]);
							} else {
								if (samples[x - 1] < 0 && samples[x] >= 0) {
									zeroX = x;
								}
							}

						}
						gc.stroke();
					}
				}

			}
		};

		timer.start();

	}
}
