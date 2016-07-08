package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;

public class Arp extends Controller {

	public Arp(Control freq, Control gain) {
		super(freq, gain);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (!it.hasNext()) {
					it = notes.listIterator();

				}
				if (it.hasNext()) {
					freq.setValue(getFreq(it.next()));
					gain.setValue(1);
					System.out.println("nuts");
				} else {
					System.out.println("nonuts");
					gain.setValue(0);

				}
			}
		}, 0, 100);

	}

	private ArrayList<Integer> notes = new ArrayList<Integer>();
	private ListIterator<Integer> it = notes.listIterator();

	@Override
	public void noteOn(MidiMessage message) {
		notes.add((Integer) ((ShortMessage) message).getData1());
		it = notes.listIterator();

		for (Source subscriber : subscribers) {
			System.out.println("subscriber " + subscriber + " note on");
			subscriber.noteOn();
		}
	}

	@Override
	public void noteOff(MidiMessage message) {
		notes.remove((Integer) ((ShortMessage) message).getData1());
		it = notes.listIterator(); //niet helemaal threadsafe...
	}

	// freq.setValue(getFreq(((ShortMessage) message).getData1()));
	// gain.setValue((((ShortMessage) message).getData2()) / 128D);
	// noteOn();
}
