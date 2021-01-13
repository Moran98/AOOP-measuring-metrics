package ie.gmit.sw;

/**
 * @author Aaron Moran
 */

/**
 * This Class handles all of the getters and setters
 * to be initialized and requested when a .jar file is chosen 
 * to be processed.
 */

public class JarFile {
	
	private int constructors;
	private boolean ifaces;
	private String pack;
	private String type;
	
	/**
	 * Default constructor
	 * @param constructors
	 * @param ifaces
	 * @param pack
	 */
	public JarFile(int constructors, boolean ifaces, String pack, String type) {
		super();
		this.constructors = constructors;
		this.ifaces = ifaces;
		this.pack = pack;
		this.type = type;
	}
	
	/**
	 * Setters
	 * @param cons
	 */
	public void setConstructors(int cons) {
		this.constructors = cons;
	}
	
	public void setInterfaces(boolean ifaces) {
		this.ifaces = ifaces;
	}
	
	public void setPackagers(String pack) {
		this.pack = pack;
	}
	
	public void setTypes(String type) {
		this.type = type;
	}

	/**
	 * Getters
	 * @return
	 */
	public int getConstructors() {
		return constructors;
	}
	
	public boolean getInterfaces() {
		return ifaces;
	}
	
	public String getPackages() {
		return pack;
	}
	
	public String getTypes() {
		return type;
	}


}
