package net.maneschijn.bleep;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import static net.maneschijn.bleep.Util.*;

public class Engine extends Thread {

	private Source[] sources;
	private byte lastSample;

	public Engine(Source... sources) {
		this.sources = sources;
	}

	public byte getLastSample() {
		return lastSample;
	}

	boolean running = true;

	public void run() {
		this.setPriority(10);
		AudioFormat audioFormat = new AudioFormat(SAMPLERATE, 8, 1, true, false);

		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

		try {
			line = (SourceDataLine) AudioSystem.getLine(info);
			line.open(audioFormat, BUFFER_SIZE);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		line.start();
		System.out.println(line.getBufferSize());

		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

		Mixer mix = new Mixer(1.0D,  sources);

		while (running) {
			for (int i = 0; i < EXTERNAL_BUFFER_SIZE; i++) {
				lastSample = mix.getSample();
				abData[i] = lastSample;
			}
			line.write(abData, 0, EXTERNAL_BUFFER_SIZE);

		}

		line.drain();

		line.close();

	}

}