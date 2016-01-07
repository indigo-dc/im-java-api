/**
 * Copyright (C) GRyCAP - I3M - UPV 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        return getValue();
    }

    public String getValue() {
        return value;
    }

    public static VmProperties getEnumFromValue(String value) {
        if (value != null) {
            for (VmProperties property : VmProperties.values()) {
                if (value.equalsIgnoreCase(property.getValue())) {
                    return property;
                }
            }
        }
        return null;
    }
}
