package net.maneschijn.bleep.core;

import static net.maneschijn.bleep.core.Util.getFreq;

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

	private Control freq;
	private Control gain;

	public Controller(Control freq, Control gain){
		this.freq=freq;
		this.gain=gain;
	}
	
	@Override
	public void send(MidiMessage message, long timeStamp) {

		switch ((int) ((ShortMessage) message).getStatus()) {
		case 144:
			System.out.println("note on");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			freq.setValue(getFreq(((ShortMessage) message).getData1()));
			gain.setValue((((ShortMessage) message).getData2()) / 128D);
			break;
		case 128:
			System.out.println("note off");
			gain.setValue(0);
			break;
		case 176:
			System.out.println("mod");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			break;
		case 224:
			System.out.println("mod");
			System.out.println(((ShortMessage) message).getData1());
			System.out.println(((ShortMessage) message).getData2());
			break;
		case 254:
			break;
		default:
			System.out.println("wut?");
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
