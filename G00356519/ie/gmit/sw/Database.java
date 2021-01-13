package ie.gmit.sw;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * @author Aaron Moran
 */

/**
 * 
 * This class carries out all of the database queries.
 * The method go() is called within the AppWindow when a button 
 * to query is pressed.
 *
 */
public class Database {
	
	private EmbeddedStorageManager db = null; // The storage manager is the database...
	private List<JarFile> root = new ArrayList<>(); //The "root" of our database can be any type of object
	
	/**
	 * Runner method to start and shutdown the Database.
	 */
	public void go() {
		root = JarFactory.getInstance().getInfo(); //Initialise the database. Comment out after first use
		db = EmbeddedStorage.start(root.toArray(), Paths.get("./data")); 
		db.storeRoot(); //Save the object graph to the database, i.e. the root and all dependencies
		query(); //Execute some read-only queries (SELECT equivalents)
		db.shutdown(); //Shutdown the db properly
	}
	
	/**
	 * Standard query to display all of the object addresses.
	 */
	public void query() { 
		//Query 1: Show all object addresses within the jar file.
		System.out.println("\n[Query] Display all Object Addresses");
		root.stream()
		   .forEach(System.out::println);
		

	}
}