package net.maneschijn.bleep.ui;

import static net.maneschijn.bleep.core.Util.EXTERNAL_BUFFER_SIZE;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import net.maneschijn.bleep.core.Engine;

public class ScopeFFT extends GridPane {

	private Canvas canvas = new Canvas(256, 256);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

	public ScopeFFT(Engine eng) {
		this.add(canvas, 0, 0);
		this.setStyle(
				"-fx-background-color: black, linear-gradient(from 0.5px 0px to 10.5px 0px, repeat, darkgreen 5%, transparent 5%), linear-gradient(from 0px 0.5px to 0px 10.5px, repeat, darkgreen 5%, transparent 5%);");

		gc.setStroke(Color.LIGHTGREEN);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long so) {

				if (eng != null) {
					byte[] samples = eng.getLastSamples();
					if (samples != null) {
						double[] samplesd = new double[EXTERNAL_BUFFER_SIZE];
						for (int i = 0; i < EXTERNAL_BUFFER_SIZE; i++) {
							samplesd[i] = samples[i];
						}
						Complex[] trans = fft.transform(samplesd, TransformType.FORWARD);

						gc.clearRect(0, 0, 256, 256);
//						gc.beginPath();

						for (int x = 0; x < EXTERNAL_BUFFER_SIZE/2; x++) {
							gc.strokeLine((double) 2*x, (double) 256 - trans[x].abs(), (double) 2*x, 256);
						}
					}
//					gc.stroke();
				}
			}

		};

		timer.start();

	}
}
