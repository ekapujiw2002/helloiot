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

package com.adr.helloiot.device;

import com.adr.helloiot.device.format.StringFormat;
import com.adr.helloiot.device.format.StringFormatDecimal;

/**
 *
 * @author adrian
 */
public abstract class DeviceNumber extends DeviceSimple {

    public static final StringFormat NUMBERFORMAT = new StringFormatDecimal("0.000");    
    
    private double increment = 1.0;
    private double levelmax = 100.0;
    private double levelmin = 0.0;
    
    @Override
    public StringFormat getFormat() {
        return NUMBERFORMAT;
    }        
    
    public String getUnit() {
        return "";
    }
    
    public final void setIncrement(double increment) {
        this.increment = increment;
    }
    
    public final double getIncrement() {
        return increment;
    }
    
    public final void setLevelMax(double levelmax) {
        this.levelmax = levelmax;
    }   
    
    public final double getLevelMax() {
        return levelmax;
    }
    
    public final void setLevelMin(double levelmin) {
        this.levelmin = levelmin;
    }
    
    public final double getLevelMin() {
        return levelmin;
    }
    
    public double adjustLevel(double newlevel) {
        
        newlevel = Math.rint(newlevel / increment) * increment;
        
        if (newlevel > levelmax) {
            return levelmax;
        }
        if (newlevel < levelmin) {
            return levelmin;
        }  
        return newlevel; 
    }
    
    @Override
    public String prevStatus() {
        return StatusNumber.getFromValue(adjustLevel(StatusNumber.getFromString(readStatus()) - increment)); 
    }
    
    @Override
    public String nextStatus() {
        return StatusNumber.getFromValue(adjustLevel(StatusNumber.getFromString(readStatus()) + increment)); 
    } 

    @Override
    public boolean hasPrevStatus() {
        return StatusNumber.getFromString(readStatus()) > levelmin;
    }

    @Override
    public boolean hasNextStatus() {
        return StatusNumber.getFromString(readStatus()) < levelmax;
    }    
}