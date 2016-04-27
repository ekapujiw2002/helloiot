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

import com.adr.helloiot.media.Clip;
import com.adr.helloiot.media.ClipFactory;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;

/**
 *
 * @author adrian
 */
public class Buzzer {

    private final Clip buzzer01;  
    private final Clip buzzer04;  
    private final Clip buzzer09;  
    private final Clip buzzer25;  
    private final Clip buzzer28;  
    private final Clip buzzerb04;  
    private final Clip buzzerb41;  
    private final Clip buzzerr03;  
    
    public Buzzer(ClipFactory factory) {
        // http://www.soundjay.com/tos.html
        buzzer01 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/beep-01a.wav").toExternalForm());
        buzzer04 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/beep-04.wav").toExternalForm());
        buzzer09 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/beep-09.wav").toExternalForm());
        buzzer25 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/beep-25.wav").toExternalForm());
        buzzer28 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/beep-28.wav").toExternalForm());
        buzzerb04 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/button-4.wav").toExternalForm());
        buzzerb41 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/button-41.wav").toExternalForm());
        buzzerr03 = factory.createClip(getClass().getResource("/com/adr/helloiot/sounds/telephone-ring-03a.wav").toExternalForm());
    }
    
    @Subscribe
    public void selectUnitPage(EventMessage message) {
        Platform.runLater(() -> updateStatus(message.getMessage()));
    } 
    
    private void updateStatus(String status) { 
        if ("ERROR".equals(status)) {
            buzzer01.play();
        } else if ("BEEP1".equals(status)) {
            buzzer04.play();
        } else if ("BEEP2".equals(status)) {
            buzzer09.play();
        } else if ("BEEP3".equals(status)) {
            buzzer25.play();
        } else if ("BEEP4".equals(status)) {
            buzzer28.play();
        } else if ("BUTTON1".equals(status)) {
            buzzerb04.play();
        } else if ("BUTTON2".equals(status)) {
            buzzerb41.play();
        } else if ("RING1".equals(status)) {
            buzzerr03.play();
        } else {
            buzzer01.play();
        }        
    }
}