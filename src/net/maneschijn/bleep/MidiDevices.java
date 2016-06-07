package net.maneschijn.bleep;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import static net.maneschijn.bleep.Util.*;

public class MidiDevices {

	public static void main(String[] args) {
		
		Receiver r;
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
					t = device.getTransmitter();
					System.out.println("device: " + device);
					System.out.println(i);
					System.out.println("info: " + infos[i].getName() + ":");
					System.out.println("info: " + infos[i].getVendor() + ":");
					System.out.println("info: " + infos[i].getDescription() + ":");
					System.out.println("info: " + infos[i].getVersion() + ":");
					// Receiver r = device.getReceiver();
					r = new MidiReceiver();
					t.setReceiver(r);
				} catch (MidiUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// Now, display strings from synthInfos list in GUI.

		// bla
		while (true) {
		}
	}

}
