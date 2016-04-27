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

package com.adr.helloiot.unit;

import com.adr.hellocommon.utils.AbstractController;
import com.adr.helloiot.device.DeviceNumber;
import com.adr.helloiot.EventMessage;
import com.adr.helloiot.HelloIoTAppPublic;
import com.adr.helloiot.device.StatusNumber;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

/**
 *
 * @author adrian
 */
public class SliderSimple extends StackPane implements Unit, AbstractController {
    
    @FXML private Slider slider;
    @FXML private Label level;
    @FXML private Label label;
    
    private boolean levelupdating = false;
    private DeviceNumber device = null;

    public SliderSimple() {   

        this.load("/com/adr/helloiot/fxml/slidersimple.fxml");  
    }

    @FXML public void initialize() {
        slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (!levelupdating) {
                device.sendStatus(StatusNumber.getFromValue(device.adjustLevel(new_val.doubleValue())));
            }
        });
        label.setText(null);
        level.setText(null);        
    }
    
    @Subscribe
    public void receivedStatus(EventMessage message) {
        Platform.runLater(() -> updateStatus(message.getMessage()));  
    }

    private void updateStatus(String status) {
        levelupdating = true;
        level.setText(device.getFormat().format(status));
        slider.setValue(StatusNumber.getFromString(status));
        levelupdating = false;
    }  
    
    @Override
    public void construct(HelloIoTAppPublic app) {
        Unit.super.construct(app);
        device.subscribeStatus(this);
        updateStatus(null);        
    }

    @Override
    public void destroy() {
        Unit.super.destroy();
        device.unsubscribeStatus(this);    
    }
    
    @Override
    public void start() {
        setDisable(false);
    }

    @Override
    public void stop() {
        setDisable(true);
    }

    @Override
    public Node getNode() {
        return this;
    }
    
    public void setDevice(DeviceNumber device) {
        this.device = device;
        if (getLabel() == null) {
            setLabel(device.getProperties().getProperty("label"));
        }  
        levelupdating = true;
        slider.setBlockIncrement(device.getIncrement());
        slider.setMax(device.getLevelMax());
        slider.setMin(device.getLevelMin());   
        levelupdating = false;
    }
    
    public DeviceNumber getDevice() {
        return device;
    }
    
    public void setLabel(String value) {
        label.setText(value);
    }
    
    public String getLabel() {
        return label.getText();
    }
}