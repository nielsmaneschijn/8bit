package net.maneschijn.bleep.ui;

import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Bitcrusher;
import net.maneschijn.bleep.core.Source;
import net.maneschijn.bleep.core.SourceUI;

public class BitcrusherUI extends GridPane implements SourceUI {

	private Bitcrusher bitcrusher;

	public BitcrusherUI( Source source) {
		
		ControlUI bits = new ControlUI("Bits", 1, 8, 4);
		bitcrusher = new Bitcrusher(bits.getControl(), source);

		this.add(bits,0,0);
	}

	@Override
	public Source getSource() {
		return bitcrusher;
	}

}
