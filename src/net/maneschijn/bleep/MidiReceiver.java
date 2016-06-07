package net.maneschijn.bleep;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

public class MidiReceiver implements Receiver {

	@Override
	public void send(MidiMessage message, long timeStamp) {
		System.out.println(message);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
	MidiReceiver() {
		
	}

}
