package es.upv.i3m.grycap.im.api;

/**
 * Stores the possible VM states
 */
public enum VmStates {

	PENDING("pending"), 
	RUNNING("running"), 
	UNCONFIGURED("unconfigured"), 
	CONFIGURED("configured"), 
	STOPPED("stopped"), 
	OFF("off"), 
	FAILED("failed"), 
	UNKNOWN("unknown");

	private final String value;

	VmStates(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	/**
	 * Return True if the string passed matches the internal value of the
	 * instance created
	 * 
	 * @param value
	 *            : string to compare
	 * @return : True if equal, false otherwise
	 */
	public boolean equals(String value) {
		return this.value.equals(value);
	}

}
