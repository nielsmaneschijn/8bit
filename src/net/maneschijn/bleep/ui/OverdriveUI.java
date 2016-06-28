package net.maneschijn.bleep.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Overdrive;
import net.maneschijn.bleep.core.Source;
import net.maneschijn.bleep.core.SourceUI;

public class OverdriveUI extends GridPane implements SourceUI {

	private Overdrive overdrive;
	private boolean clipMode = false;
	
	public OverdriveUI( Source source) {
		
		ControlUI gain = new ControlUI("Overdrive", 0, 32, 1);
		overdrive = new Overdrive(gain.getControl(), clipMode, source);
		Button clip = new Button("clip");
		clip.setOnAction(e -> {
			clipMode = !clipMode;
			overdrive.setClipMode(clipMode);
			clip.setStyle(clipMode ? "-fx-background-color: #33FF33;" : "");
		});
		this.add(gain,0,0);
		this.add(clip,1,1);
		this.setPadding(new Insets(15));
		this.setStyle("-fx-border-color: lightgray");
		VU vu = new VU(overdrive, false);
		this.add(vu, 0, 1);
	}

	@Override
	public Source getSource() {
		return overdrive;
	}

}
