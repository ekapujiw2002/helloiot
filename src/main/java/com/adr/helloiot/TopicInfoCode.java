//    HelloIoT is a dashboard creator for MQTT
//    Copyright (C) 2017 Adrián Romero Corchado.
//
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
//
package com.adr.helloiot;

import com.adr.helloiot.device.Device;
import com.adr.helloiot.unit.Unit;
import com.adr.helloiot.util.ExternalFonts;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 *
 * @author adrian
 */
public class TopicInfoCode implements TopicInfo {
    
    private String name;
    private String code;
    
    private final TopicInfoCodeNode editnode;
    
    public TopicInfoCode(TopicInfoCodeNode editnode) {
        this.editnode = editnode;
    }

    @Override
    public String getType() {
        return "Code";
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public Node getGraphic() {

        Text t = new Text();
        t.setFill(Color.WHITE);
        t.setFont(Font.font(ExternalFonts.ROBOTOBOLD, FontWeight.BOLD, 10.0));
        TextFlow tf = new TextFlow(t);
        tf.setPrefWidth(55);
        tf.setTextAlignment(TextAlignment.CENTER);
        tf.setPadding(new Insets(2, 5, 2, 5));

        t.setText("CODE");
        tf.setStyle("-fx-background-color: #001A80; -fx-background-radius: 12px;");

        return tf;
    }

    @Override
    public void load(SubProperties properties) {
        name = properties.getProperty(".name");
        code = properties.getProperty(".code");
    }

    @Override
    public void store(SubProperties properties) {
        properties.setProperty(".name", getName());
        properties.setProperty(".code", getCode());
    }

    @Override
    public TopicStatus getTopicStatus() {
        
        String fxml = 
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<?import java.lang.*?>\n" +
                "<?import java.util.*?>\n" +
                "<?import javafx.scene.layout.*?>\n" +
                "<?import com.adr.fonticon.*?>\n" +
                "<?import com.adr.helloiot.*?>\n" +
                "<?import com.adr.helloiot.unit.*?>\n" +
                "<?import com.adr.helloiot.unitsensor.*?>\n" +
                "<?import com.adr.helloiot.device.*?>\n" +
                "<?import com.adr.helloiot.graphic.*?>\n" +
                "<ArrayList xmlns=\"http://javafx.com/javafx/8.0.40\" xmlns:fx=\"http://javafx.com/fxml/1\">\n" +
                code +
                "</ArrayList>";
        
        try (InputStream in = new ByteArrayInputStream(fxml.getBytes(StandardCharsets.UTF_8))) {
            FXMLLoader fxmlloader;
            fxmlloader = new FXMLLoader(StandardCharsets.UTF_8);
            ArrayList list = fxmlloader.<ArrayList>load(in);
            List<Device> devices = new ArrayList<>();
            List<Unit> units = new ArrayList<>();
            
            for (Object o: list) {
                if (o instanceof Device) {
                    devices.add((Device) o);
                } else if (o instanceof Unit) {
                    units.add((Unit) o);
                }
            } 
            return new TopicStatus(devices, units);
        } catch (IOException ex) {
            Logger.getLogger(TopicInfoCode.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public TopicInfoNode getEditNode() {
        return editnode;
    }

    @Override
    public void writeToEditNode() {
        editnode.name.setText(getName());
        editnode.code.setText(code);
    }

    @Override
    public void readFromEditNode() {
        name = editnode.name.getText();
        code = editnode.code.getText();
    }  
    
    public String getName() {
        return name;
    }
    
    public String getCode() {
        return code;
    }

}
