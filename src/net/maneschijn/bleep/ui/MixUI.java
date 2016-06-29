package net.maneschijn.bleep.ui;

import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Mixer;
import net.maneschijn.bleep.core.Source;

public class MixUI extends GridPane implements SourceUI {

	private Mixer mixer;

	public MixUI( Source source) {
		
		ControlUI gain = new ControlUI("Gain", 0, 4, 1);
		mixer = new Mixer(gain.getControl(), source);

		this.add(gain,0,0);
	}

	@Override
	public Source getSource() {
		return mixer;
	}

}
