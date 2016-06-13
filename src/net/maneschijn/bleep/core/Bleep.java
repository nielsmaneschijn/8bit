package net.maneschijn.bleep.core;
import static net.maneschijn.bleep.core.Util.*;

public class Bleep {

	public static void main(String[] args) throws InterruptedException {
		Engine eng = new Engine();
		eng.start();
		
		while (true) {
			Thread.sleep(250);
			Thread.sleep(250);
			Thread.sleep(250);
		}
	}
}
