package seventeenth.group.userinterface;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.event.KeyEvent;

public class InputManager {
    public void processKeyPressed(int keyCode) {

        if(keyCode == KeyEvent.VK_UP) {
            System.out.println("You press UP");
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            System.out.println("You press DOWN");
        }
        else if(keyCode == KeyEvent.VK_LEFT) {
            System.out.println("You press LEFT");
        }
        else if(keyCode == KeyEvent.VK_RIGHT) {
            System.out.println("You press RIGHT");
        }
        else if(keyCode == KeyEvent.VK_ENTER) {
            System.out.println("You press ENTER");
        }
        else if(keyCode == KeyEvent.VK_SPACE) {
            System.out.println("You press SPACE");
        }
        else if(keyCode == KeyEvent.VK_C) {
            System.out.println("You press C");
        }
    }
    public void processKeyReleased(int keyCode) {

        if(keyCode == KeyEvent.VK_UP) {
            System.out.println("You released UP");
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            System.out.println("You released DOWN");
        }
        else if(keyCode == KeyEvent.VK_LEFT) {
            System.out.println("You released LEFT");
        }
        else if(keyCode == KeyEvent.VK_RIGHT) {
            System.out.println("You released RIGHT");
        }
        else if(keyCode == KeyEvent.VK_ENTER) {
            System.out.println("You released ENTER");
        }
        else if(keyCode == KeyEvent.VK_SPACE) {
            System.out.println("You released SPACE");
        }
        else if(keyCode == KeyEvent.VK_C) {
            System.out.println("You released C");
        }
    }
}