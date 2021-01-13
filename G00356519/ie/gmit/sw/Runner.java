package ie.gmit.sw;

import javafx.application.*;

/**
 * @author Aaron Moran
 */

/**
 * 
 * The Runner class launches the GUI.
 *
 */
public class Runner {
	public static void main(String[] args) {
		System.out.println("[INFO] Launching GUI...");
		Application.launch(AppWindow.class, args);
	}
}