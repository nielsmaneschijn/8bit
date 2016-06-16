package net.maneschijn.bleep.ui;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.Control;

public class ControlUI extends GridPane {

	private Control control = new Control(0D);

	public ControlUI(String title) {
		this(title, 0, 1, 0);
	}

	public ControlUI(String title, double min, double max, double value) {
		control.setValue(value);
		Label label = new Label(title);
		Slider slider = new Slider(min, max, value);
		slider.setShowTickMarks(true);
		slider.setMajorTickUnit(0.5F);
		slider.setShowTickLabels(true);
		slider.setOrientation(Orientation.VERTICAL);
		slider.valueProperty().addListener((observable, oldval, newval) -> {
			control.setValue(newval.doubleValue());
		});
		this.add(label, 0, 0);
		this.add(slider, 0, 1);

	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}

}
