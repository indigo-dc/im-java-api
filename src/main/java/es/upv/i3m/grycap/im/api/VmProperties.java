package es.upv.i3m.grycap.im.api;

/**
 * Enumerates the most common properties of the VM.<br>
 */
public enum VmProperties {

	// Hardware
	CPU_ARCH("cpu.arch"),
	CPU_COUNT("cpu.count"),
	MEMORY_SIZE("memory.size"),
	// VM state -> see VmStates.java for set of results
	STATE("state"),
	// Provider
	PROVIDER_TYPE("provider.type"),
	// Net
	NET_INTERFACE_0_IP("net_interface.0.ip"),
	NET_INTERFACE_0_CONNECTION("net_interface.0.connection"),
	NET_INTERFACE_0_DNS_NAME("net_interface.0.dns_name"),
	// OS and disk properties
	DISK_0_IMAGE_URL("disk.0.image.url"),
	DISK_0_OS_NAME("disk.0.os.name"),
	DISK_0_OS_CREDENTIALS_USERNAME("disk.0.os.credentials.username"),
	DISK_0_OS_CREDENTIALS_PASSWORD("disk.0.os.credentials.password"),
	DISK_0_OS_CREDENTIALS_NEW_PASSWORD("disk.0.os.credentials.new.password");
			
	private final String value;

	VmProperties(String value) {
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
	public boolean equals(String value){
		return this.value.equals(value);
	}
}
