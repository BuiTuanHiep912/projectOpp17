package seventeenth.group.userinterface;

import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.gameobjects.Hero;
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

        //title state
        if(gameWorld.state == GameWorld.INIT_GAME) {
            if(gameWorld.getTitleScreenState() == 0) {
                if(keyCode == KeyEvent.VK_UP) {
                    gameWorld.addOrsub(-1);
                    if(gameWorld.getCommandNum() < 0){
                        gameWorld.setCommandNum(2);
                    }
                }
                else if(keyCode == KeyEvent.VK_DOWN) {
                    gameWorld.addOrsub(1);
                    if(gameWorld.getCommandNum() > 2){
                        gameWorld.setCommandNum(0);
                    }
                }

                if(keyCode == KeyEvent.VK_ENTER) {
                    if(gameWorld.getCommandNum() == 0) {
                        gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                    if(gameWorld.getCommandNum() == 1) {
                        gameWorld.setTitleScreenState(1);
                    }
                    if(gameWorld.getCommandNum() == 2) {
                        System.exit(0);
                    }
                }
            }
            else if(gameWorld.getTitleScreenState() == 1) {
                if(keyCode == KeyEvent.VK_ENTER) {
                    if(gameWorld.getCommandNum() == 0) {
                        gameWorld.setTitleScreenState(0);
                    }
                }
            }
        }
        // play state
        if(gameWorld.state != GameWorld.INIT_GAME){
            if(keyCode == KeyEvent.VK_UP) {
                //gamePanel.gameWorld.hero.setDirection(Hero.DIR_LEFT);
                gameWorld.hero.setDirection(gameWorld.hero.UP_DIR);
                gameWorld.hero.run();
            }
            else if(keyCode == KeyEvent.VK_DOWN) {
                //gameWorld.physicalMap.setSy += 5;
                gameWorld.hero.setDirection(gameWorld.hero.DOWN_DIR);
                gameWorld.hero.run();
            }
            else if(keyCode == KeyEvent.VK_LEFT) {
                //gamePanel.physicalMap.x -= 5;
                gameWorld.hero.setDirection(gameWorld.hero.LEFT_DIR);
                gameWorld.hero.run();
            }
            else if(keyCode == KeyEvent.VK_RIGHT) {
                //gamePanel.physicalMap.x += 5;
                gameWorld.hero.setDirection(gameWorld.hero.RIGHT_DIR);
                gameWorld.hero.run();
            }
            else if(keyCode == KeyEvent.VK_ENTER) {
                System.out.println("hehe" + gameWorld.state);
                if(gameWorld.state == GameWorld.GAMEPLAY) {
                    if(gameWorld.previousState == GameWorld.GAMEPLAY) {
                        gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                    else {
                        gameWorld.switchState(GameWorld.TUTORIAL);
                    }
                }
                if(gameWorld.state == GameWorld.TUTORIAL && gameWorld.storyTutorial >= 1) {
                    if(gameWorld.storyTutorial <= 3) {
                        gameWorld.storyTutorial++;
                        gameWorld.currentSize = 1;
                        gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial-1];
                    }
                    else {
                        gameWorld.switchState(GameWorld.GAMEPLAY);
                    }

                    if(gameWorld.tutorialState == GameWorld.MEETFINALBOSS) {
                        gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                }
                System.out.println("You press ENTER");
            }
            else if(keyCode == KeyEvent.VK_SPACE) {
                System.out.println("You press SPACE");
            }
            else if(keyCode == KeyEvent.VK_C) {
                gameWorld.hero.attack();
                System.out.println("You press C");
            }
        }

    }



    public void processKeyReleased(int keyCode) {

        // Dừng di chuyển khi phím được thả
        if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            gameWorld.hero.stopRun();  // Dừng di chuyển theo chiều Y
            //System.out.println("You released");
        }
        else if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
            gameWorld.hero.stopRun();  // Dừng di chuyển theo chiều X
            //System.out.println("You released");
        }
        else if(keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_D) {
            gameWorld.hero.stopRun();
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
}