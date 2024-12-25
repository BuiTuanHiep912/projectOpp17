package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.BackgroundPanel;
import seventeenth.group.userinterface.GameFrame;
import seventeenth.group.userinterface.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameWorld {

    private BufferedImage bufferedImage;
    private BufferedImage bufferedImage1;
    public Hero hero;
    public PhysicalMap physicalMap;
    public BulletManager bulletManager;
    public Camera camera;
    public RedEyeDevil redEyeDevil1, redEyeDevil2, redEyeDevil3,  redEyeDevil4,  redEyeDevil5,  redEyeDevil6,  redEyeDevil7,  redEyeDevil8,  redEyeDevil9;
    public ParticularObjectManager particularObjectManager;
    public FinalBoss finalBoss;
    public BackgroundMap backgroundMap;

    public static final int finalBossX = 2400;
    public static final int finalBossY = 2040;


    public static final int INIT_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int GAMEWIN = 4;

    public static final int INTROGAME = 0;
    public static final int MEETFINALBOSS = 1;

    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;

    public int storyTutorial = 0;
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;

    private boolean finalbossTrigger = true;
    // ParticularObject boss;

    private int numberOfLife = 3;

    public int commandNum = 0;
    public int titleScreenState = 0;


    public GameWorld() {

        particularObjectManager = new ParticularObjectManager(this);

        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);

        bulletManager = new BulletManager(this);
 //2380 2030
        hero = new Hero(70, 470,this);
        particularObjectManager.addObject(hero);

        physicalMap = new PhysicalMap(0, 0, this);
        backgroundMap = new BackgroundMap(0,0,this);

        camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH,  GameFrame.SCREEN_HEIGHT, this);


    }

    public void Update() {
        hero.Update();
        camera.Update();
        bulletManager.UpdateObjects();
    }

    public void Render() {
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        backgroundMap.draw(g2);
        hero.draw(g2);
        bulletManager.draw(g2);
    }

    public int getXforCenteredText(String text, float size) {
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, size));
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = GameFrame.SCREEN_WIDTH / 2 - length / 2;
        return x;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;   
     }

}