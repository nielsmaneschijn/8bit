package net.maneschijn.bleep;

	public class SineOscVoluit  {
		protected double freq=440D;
		protected double gain = 1D;
		protected double samplerate = 8000D;
		protected double sample = 0;
		
		public SineOscVoluit(double freq, double gain, float samplerate){
			this.freq=freq;
			this.gain=gain;	
			this.samplerate=samplerate;
			System.out.println("hallo ik ben een oscillator etc"+this);
		}
		
		
		public byte getSample() {
			return (byte) (gain*255*Math.sin(Math.PI*2*(sample++/(samplerate/freq)))-128);
		}
		
		public final double getGain() {
			return gain;
		}

		public final void setGain(double gain) {
			this.gain = gain;
		}

		public final double getFreq() {
			return freq;
		}

		public final void setFreq(double freq) {
			this.freq = freq;
		}
	}