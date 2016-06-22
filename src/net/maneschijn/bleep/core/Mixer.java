package net.maneschijn.bleep.core;
public class Mixer extends Source{

	private Source[] inputs;

	public Mixer(Control gain, Source... inputs) {
		this.gain = gain;
		this.inputs = inputs;
	}

	@Override
	public byte getSample() {
		int total = 0;
		for (Source input : inputs) {
			total += input.getSample();
		}
		return (byte) Math.max(-128,Math.min(127,total/inputs.length*gain.getValue()));
	}
}
