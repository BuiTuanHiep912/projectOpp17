package seventeenth.group.userinterface;

import seventeenth.group.gameobjects.GameWorld;
//import sun.java2d.loops.ProcessPath;


import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.awt.event.KeyEvent;

public class InputManager {

    private GameWorld gameWorld;
    private int defaultspeed = 1;
    //private ProcessPath.Point physicalMap;

    public InputManager(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void processKeyPressed(int keyCode) {

        if(keyCode == KeyEvent.VK_UP) {
            //gamePanel.gameWorld.hero.setDirection(Hero.DIR_LEFT);
            gameWorld.hero.setSpeedY(-defaultspeed);
            updateHeroPosition();
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            //gameWorld.physicalMap.setSy += 5;
            gameWorld.hero.setSpeedY(defaultspeed);
            updateHeroPosition();
        }
        else if(keyCode == KeyEvent.VK_LEFT) {
            //gamePanel.physicalMap.x -= 5;
            gameWorld.hero.setSpeedX(-defaultspeed);
            updateHeroPosition();
        }
        else if(keyCode == KeyEvent.VK_RIGHT) {
            //gamePanel.physicalMap.x += 5;
            gameWorld.hero.setSpeedX(defaultspeed);
            updateHeroPosition();
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

        // Dừng di chuyển khi phím được thả
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            gameWorld.hero.setSpeedY(0);  // Dừng di chuyển theo chiều Y
            System.out.println("You released");
        }
        else if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            gameWorld.hero.setSpeedX(0);  // Dừng di chuyển theo chiều X
            System.out.println("You released");
        }
        //System.out.println("You released " + KeyEvent.getKeyText(keyCode));
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
    private void updateHeroPosition() {
        float heroX = gameWorld.hero.getPosX();
        float heroY = gameWorld.hero.getPosY();
        System.out.println("Hero position: X = " + heroX + ", Y = " + heroY);
    }
}
