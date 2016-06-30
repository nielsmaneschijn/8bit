package net.maneschijn.bleep.ui;

import static net.maneschijn.bleep.core.FilterMode.*;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.FilterMode;
import net.maneschijn.bleep.core.Moog;
import net.maneschijn.bleep.core.Source;

public class MoogUI extends GridPane implements SourceUI {

	private Moog moog;

	public MoogUI( Source source) {
		
		ControlUI freq = new ControlUI("Freq", 0, 1, 0.1);
		ControlUI reso = new ControlUI("Reso", 0, 1, 0.5);
		moog = new Moog(freq.getControl(), reso.getControl(), FilterMode.LP,source);

		Button lp = new Button("LP");
		Button hp = new Button("HP");
		Button bp = new Button("BP");
		
		lp.setStyle("-fx-background-color: #33FF33;");
		
		lp.setOnAction(e-> {moog.setMode(LP); lp.setStyle("-fx-background-color: #33FF33;"); hp.setStyle(""); bp.setStyle("");});
		hp.setOnAction(e-> {moog.setMode(HP); hp.setStyle("-fx-background-color: #33FF33;"); lp.setStyle(""); bp.setStyle("");});
		bp.setOnAction(e-> {moog.setMode(BP); bp.setStyle("-fx-background-color: #33FF33;"); hp.setStyle(""); lp.setStyle("");});
			
		this.add(freq,0,0);
		this.add(reso,1,0);
		
		this.add(lp, 0, 1);
		this.add(hp, 1, 1);
		this.add(bp, 2, 1);
		
		this.setPadding(new Insets(15));
		this.setStyle("-fx-border-color: lightgray");
	}

	@Override
	public Source getSource() {
		return moog;
	}

}
