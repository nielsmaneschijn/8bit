package net.maneschijn.bleep.ui;

import javafx.scene.layout.GridPane;
import net.maneschijn.bleep.core.ADSR;
import net.maneschijn.bleep.core.Controller;
import net.maneschijn.bleep.core.Source;

public class ADSRUI extends GridPane implements SourceUI {

	private ADSR adsr;

	public ADSRUI(Controller controller) {
//		this.setPadding(new Insets(15));
		this.setStyle("-fx-border-color: lightgray");
		
		ControlUI A = new ControlUI("A", 0, 1, 0.1);
		ControlUI D = new ControlUI("D", 0, 1, 0.1);
		ControlUI S = new ControlUI("S", 0, 1, 0.6);
		ControlUI R = new ControlUI("R", 0, 2, 0.5);
		
		adsr = new ADSR(A.getControl(), D.getControl(), S.getControl(), R.getControl(), controller);
		
		this.add(A, 0, 0);
		this.add(D, 1, 0);
		this.add(S, 2, 0);
		this.add(R, 3, 0);
	}

	@Override
	public Source getSource() {
			return adsr;
	}
	
	

}
