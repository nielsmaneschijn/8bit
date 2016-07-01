package net.maneschijn.bleep.ui;

import static net.maneschijn.bleep.core.FilterMode.*;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.FilterMode;
import net.maneschijn.bleep.core.Moog;
import net.maneschijn.bleep.core.Source;

public class MoogUI extends GridPane implements SourceUI {

	private Moog moog;

	public MoogUI( Source source) {
		
		ControlUI freq = new ControlUI("Freq", 0, 1, 1);
		ControlUI reso = new ControlUI("Reso", 0, 1, 0);
		moog = new Moog(freq.getControl(), reso.getControl(), FilterMode.LP,source);

		ToggleGroup tg = new ToggleGroup();
		
		addButton(tg,0,1,"LP", LP, true);
		addButton(tg,1,1,"HP", HP, false);
		addButton(tg,2,1,"BP", BP, false);

		this.add(freq,0,0);
		this.add(reso,1,0);
		
		this.setPadding(new Insets(15));
		this.setStyle("-fx-border-color: lightgray");
	}

	private void addButton(ToggleGroup tg, int row, int col, String label, FilterMode filterMode, boolean initstate) {
		RadioButton button = new RadioButton(label);
		button.setToggleGroup(tg);
		button.getStyleClass().remove("radio-button");
		button.getStyleClass().add("toggle-button");
		button.setOnAction(e-> {moog.setMode(filterMode); });
		button.setSelected(initstate);
		this.add(button, row, col);
	}

	@Override
	public Source getSource() {
		return moog;
	}

}
