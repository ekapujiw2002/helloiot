//    This file is part of HelloIot.
//
//    HelloIot is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    HelloIot is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with HelloIot.  If not, see <http://www.gnu.org/licenses/>.

package com.adr.helloiot;

import com.adr.helloiot.unit.Unit;
import com.adr.helloiot.device.Device;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author adrian
 */
public abstract class AbstractApplicationDevicesUnits implements ApplicationDevicesUnits {
    
    private List<Device> devices = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();
    
    protected final void createDevices(Device... devices) {
        this.devices = Arrays.asList(devices);
    }
    
    protected final void createUnits(Unit... units) {
        this.units = Arrays.asList(units);
    }
    
    @Override
    public final List<Device> getDevices() {
        return devices;
    } 
    
    @Override
    public final List<Unit> getUnits() {
        return units;
    } 
}