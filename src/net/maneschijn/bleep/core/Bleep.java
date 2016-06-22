package net.maneschijn.bleep.core;

public class Bleep {

	public static void main(String[] args) throws InterruptedException {
		Engine eng = new Engine(new Control(1),new SineOsc(new Controller(new Control(440), new Control(1)),null,null,null),null);
		eng.start();
		
		while (true) {
			Thread.sleep(250);
			Thread.sleep(250);
			Thread.sleep(250);
		}
	}
}
