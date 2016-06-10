package net.maneschijn.bleep;
public class Mixer extends Source{

	private Source[] inputs;

	Mixer(Control gain, Source... inputs) {
		this.gain = gain;
		this.inputs = inputs;
	}

	public byte getSample() {
		int total = 0;
		for (Source input : inputs) {
			total += input.getSample();
		}
		return (byte) (total/inputs.length);
	}
}
