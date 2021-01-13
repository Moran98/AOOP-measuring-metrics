package ie.gmit.sw;

import javafx.collections.*;
/**
 * 
 * @author Aaron Moran
 *
 */

/**
 * This class allows us to create an object without
 * making it visible to the client and handle the information being
 * passed from the JAR file.
 */
public class JarFactory {
	private static final JarFactory jf = new JarFactory();
	private ObservableList<JarFile> model;
	
	private JarFactory() {
		model = FXCollections.observableArrayList ();
	}
	
	public static JarFactory getInstance() {
		return jf;
	}
	
	public ObservableList<JarFile> getInfo() {
		return model;
	}

}