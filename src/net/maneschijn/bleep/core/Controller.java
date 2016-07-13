package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.getFreq;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class Controller implements Receiver {

	protected Control freq;
	protected Control gain;
	protected List<Source> subscribers = new ArrayList<Source>();

	public Controller(Control freq, Control gain) {
		this.freq = freq;
		this.gain = gain;
	}

	public void subscribe(Source subscriber) {
		subscribers.add(subscriber);
		System.out.println("nieuwe subscriber! " + subscriber);
	}

	public void unsubscribe(Source subscriber) {
		subscribers.remove(subscriber);
	}

	public Control getFreqControl() {
		return freq;
	}

	public Control getGainControl() {
		return gain;
	}

	@Override
	public void send(MidiMessage message, long timeStamp) {

		switch ((int) ((ShortMessage) message).getStatus()) {
		case ShortMessage.NOTE_ON:
			System.out.println("note on");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			noteOn(message);
			break;
		case ShortMessage.NOTE_OFF:
			System.out.println("note off");
			noteOff(message);
			break;
		case ShortMessage.CONTROL_CHANGE:
			System.out.println("control");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			break;
		case ShortMessage.PITCH_BEND:
			System.out.println("pitch");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			break;
		case ShortMessage.ACTIVE_SENSING:
			break;
		default:
			//CHANNEL_PRESSURE en POLY_PRESSURE zijn ook leuk (aftertouch)
			System.out.println("wut? "+ (int) ((ShortMessage) message).getStatus());
		}
	}

	public void noteOff(MidiMessage message) {
		// gain.setValue(0);
		for (Source subscriber : subscribers) {
			System.out.println("subscriber " + subscriber + " note off");
			subscriber.noteOff();
		}
	}

	// public void noteOn() {
	// for (Source subscriber : subscribers) {
	// System.out.println("subscriber " + subscriber + " note on");
	// subscriber.noteOn();
	// }
	// }

	public void noteOn(MidiMessage message) {
		if (message != null) {
			freq.setValue(getFreq(((ShortMessage) message).getData1()));
			gain.setValue((((ShortMessage) message).getData2()) / 128D);
		}
		for (Source subscriber : subscribers) {
			System.out.println("subscriber " + subscriber + " note on");
			subscriber.noteOn();
		}
	}

	@Override
	public void close() {

	}

	public void connectToMidiDevice() {
		Transmitter t;

		MidiDevice device = null;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			try {
				device = MidiSystem.getMidiDevice(infos[i]);
			} catch (MidiUnavailableException e) {
				System.out.println("Ga eens van die snoertje af!");
			}
			if (!(device instanceof Sequencer) && !(device instanceof Synthesizer) && i == 2) {

				try {
					device.open();
					System.out.println("device: " + device);
					System.out.println(i);
					System.out.println("info: " + infos[i].getName() + ":");
					System.out.println("info: " + infos[i].getVendor() + ":");
					System.out.println("info: " + infos[i].getDescription() + ":");
					System.out.println("info: " + infos[i].getVersion() + ":");
					t = device.getTransmitter();
					t.setReceiver(this);
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
