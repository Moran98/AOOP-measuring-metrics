package ie.gmit.sw;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

/**
 * @author Aaron Moran
 */

/**
 * 
 * This class creates all of the User Interface
 * elements which then handles any processes being chosen.
 * Selecting a JAR file, Processing the JAR, Queries, Exiting.
 *
 */
public class AppWindow extends Application {
	private ObservableList<JarFile> metrics; //The Model - a list of observers.
	private TableView<JarFile> tv; //The View - a composite of GUI components
	private TextField txtFile; //A control, part of the View and a leaf node.
	
	@Override
	public void start(Stage stage) throws Exception { //This is a ***Template Method***
		JarFactory jf = JarFactory.getInstance(); //Get the singleton instance
		metrics = jf.getInfo(); //Get the Model 
		
		stage.setTitle("Aaron Moran - G00356519");
		stage.setWidth(800);
		stage.setHeight(600);
		
		stage.setOnCloseRequest((e) -> System.exit(0));
		
		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		//**Strategy Pattern**. Configure the Context with a Concrete Strategy
		Scene scene = new Scene(box); 
		stage.setScene(scene);

		ToolBar toolBar = new ToolBar(); //A ToolBar is a composite node for Buttons (leaf nodes)

		Button btnQuit = new Button("Quit"); //A Leaf node
		btnQuit.setOnAction(e -> System.exit(0)); //Plant an observer on the button
		toolBar.getItems().add(btnQuit); //Add to the parent node and build the tree
		
			
		/*
		 * Add all the sub trees of nodes to the parent node and build the tree
		 */
		box.getChildren().add(getFileChooserPane(stage)); //Add the sub tree to the main tree
		box.getChildren().add(getTableView()); //Add the sub tree to the main tree
		box.getChildren().add(toolBar); //Add the sub tree to the main tree
		// Display the window
		stage.show();
		stage.centerOnScreen();
	}

	/**
	 * This method provides us with the ability to
	 * create optional buttons to Process and Query the JAR file.
	 * 
	 * @param stage
	 * @return
	 */
	private TitledPane getFileChooserPane(Stage stage) {
		VBox panel = new VBox(); //** A concrete strategy ***

		txtFile = new TextField(); //A leaf node

		FileChooser fc = new FileChooser(); //A leaf node
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		Button btnOpen = new Button("Select File"); //A leaf node
		btnOpen.setOnAction(e -> { //Plant an observer on the button
			// Reuqests a JAR file from file system
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		Button btnProcess = new Button("Process"); //A leaf node
		btnProcess.setOnAction(e -> { //Plant an observer on the button
			// Processing the Jar File
			inputStream();
		});
		
		Button btnDatabase = new Button("Run Query - DB"); //A leaf node
		btnDatabase.setOnAction(e -> { //Plant an observer on the button
			// Create and launch the database
			Database d = new Database();
			d.go();
		});
		
		ToolBar tb = new ToolBar(); //A composite node
		tb.getItems().add(btnOpen); //Add to the parent node and build a sub tree
		tb.getItems().add(btnProcess); //Add to the parent node and build a sub tree
		tb.getItems().add(btnDatabase); //Add to the parent node and build a sub tree


		panel.getChildren().add(txtFile); //Add to the parent node and build a sub tree
		panel.getChildren().add(tb); //Add to the parent node and build a sub tree

		TitledPane tp = new TitledPane("Select File to Process", panel); //Add to the parent node and build a sub tree
		tp.setCollapsible(false);
		return tp;
	}
	
	/**
	 * This method initializes the columns displayed 
	 * on the User Interface. 
	 * 
	 * @return
	 */
	private TableView<JarFile> getTableView() {
		
		tv = new TableView<>(metrics); //A TableView is a composite node
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Stretch columns to fit the window
		
		TableColumn<JarFile, String> packages = new TableColumn<>("Package");
		packages.setCellValueFactory(new Callback<CellDataFeatures<JarFile, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<JarFile, String> p) {
				return new SimpleStringProperty(p.getValue().getPackages());
			}
		});
		
		TableColumn<JarFile, Number> constructors = new TableColumn<>("Constructors");
		constructors.setCellValueFactory(new Callback<CellDataFeatures<JarFile, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<JarFile, Number> p) {
				return new SimpleIntegerProperty(p.getValue().getConstructors());
			}
		});
		
		
		TableColumn<JarFile, Boolean> ifaces = new TableColumn<>("isInterface");
		ifaces.setCellValueFactory(new Callback<CellDataFeatures<JarFile, Boolean>, ObservableValue<Boolean>>() {
			public ObservableValue<Boolean> call(CellDataFeatures<JarFile, Boolean> p) {
				return new SimpleBooleanProperty(p.getValue().getInterfaces());
			}
		});
		
		TableColumn<JarFile, String> types = new TableColumn<>("Type");
		types.setCellValueFactory(new Callback<CellDataFeatures<JarFile, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<JarFile, String> p) {
				return new SimpleStringProperty(p.getValue().getTypes());
			}
		});
		
		
		tv.getColumns().add(packages);  //Add nodes to the tree
		tv.getColumns().add(constructors); //Add nodes to the tree
		tv.getColumns().add(ifaces);  //Add nodes to the tree
		tv.getColumns().add(types);  //Add nodes to the tree
		return tv;
	}
	
	/**
	 * This method handles the processing of the JAR file,
	 * when the JAR file is received it passes through a input stream
	 * and removes any class extensions. 
	 * 
	 * We can then access all of the JAR files by using the
	 * 'Class' import to represent the requested metrics.
	 */
	public void inputStream() {
		File f = new File(txtFile.getText());
		System.out.println("[INFO] Processing file " + f.getName());
		
		JarInputStream in = null;
		try {
			in = new JarInputStream(new FileInputStream(new File(f.getPath())));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		JarEntry next = null;
		try {
			next = in.getNextJarEntry();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (next != null) {
			 if (next.getName().endsWith(".class")) {
			 String name = next.getName().replaceAll("/", "\\.");
			 name = name.replaceAll(".class", "");
			 if (!name.contains("$")) name.substring(0, name.length() - ".class".length());				 	
			 	try {
			 		
			 		@SuppressWarnings("rawtypes")
					Class cls = Class.forName(name);
					@SuppressWarnings("rawtypes")
					Constructor[] cons = cls.getConstructors();
					String type = cls.getTypeName();
					String pack = cls.getPackageName();
					boolean ifaces = cls.isInterface();
					
					JarFile c = new JarFile(cons.length, ifaces, pack, type);
					
					metrics.add(cons.length, c);
											
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			 }
			 
			 try {
				next = in.getNextJarEntry();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}