package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.BackgroundPanel;
import seventeenth.group.userinterface.GameFrame;
import seventeenth.group.userinterface.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameWorld {

    private BufferedImage bufferedImage;
    public Hero hero;
    public PhysicalMap physicalMap;
    public BulletManager bulletManager;
    public Camera camera;
    public RedEyeDevil redEyeDevil;
    public ParticularObjectManager particularObjectManager;
    //BackgroundPanel backgroundPanel = new BackgroundPanel("data/background.jpg");


    public static final int finalBossX = 3600;

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
    ParticularObject boss;

    private int numberOfLife = 3;

    public int commandNum = 0;
    public int titleScreenState = 0;



    public GameWorld() {
        texts1[0] = "We are heros, and our mission is protecting our Home\nEarth....";
        texts1[1] = "There was a Monster from University on Earth in 10 years\n"
                + "and we lived in the scare in that 10 years....";
        texts1[2] = "Now is the time for us, kill it and get freedom!....";
        texts1[3] = "      LET'S GO!.....";
        textTutorial = texts1[0];

        particularObjectManager = new ParticularObjectManager(this);

        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        hero = new Hero(70, 470,this);
        physicalMap = new PhysicalMap(0, 0, this);
        camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH,  GameFrame.SCREEN_HEIGHT, this);
        bulletManager = new BulletManager(this);
        redEyeDevil = new RedEyeDevil(1000, 470, this);


    }



    public int getTitleScreenState() {
        return titleScreenState;
    }

    public void setTitleScreenState(int titleScreenState) {
        setCommandNum(0);
        this.titleScreenState = titleScreenState;
    }

    public int getCommandNum() {
        return commandNum;
    }

    public void setCommandNum(int commandNum) {
        this.commandNum = commandNum;
    }

    public void addOrsub(int x) {
        commandNum += x;
    }

    public void switchState(int state) {
        previousState = this.state;
        this.state = state;
    }

    public void TutorialUpdate() {
        switch(tutorialState) {
            case INTROGAME:
                if(storyTutorial == 0){
                    if(openIntroGameY < 450) {
                        openIntroGameY+=4;
                    }else storyTutorial ++;

                }else{

                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
            case MEETFINALBOSS:
                if(storyTutorial == 0) {
                    if(openIntroGameY >= 450) {
                        openIntroGameY -= 1;
                    }
                    if(camera.getPosX() < finalBossX) {
                        camera.setPosX(camera.getPosX() + 2);
                    }

                    if(hero.getPosX() < finalBossX + 150) {
                    }
                    else {
                    }

                    if(openIntroGameY < 450 && camera.getPosX() >= finalBossX && hero.getPosX() >= finalBossX + 150) {
                        camera.lock();
                        storyTutorial++;
                        hero.stopRun();
                    }

                }
                else {
                    if(currentSize < textTutorial.length()) currentSize++;
                }
                break;
        }
    }

    private void drawString(Graphics2D g2, String text, int x, int y){
        for(String str : text.split("\n"))
            g2.drawString(str, x, y+=g2.getFontMetrics().getHeight());
    }

    private void TutorialRender(Graphics2D g2) {
        switch(tutorialState) {
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                int y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);

                if(storyTutorial >= 1){
                    //g2.drawImage(avatar.getImage(), 600, 350, null);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 290, 480);
                }

                break;
            case MEETFINALBOSS:
                yMid = GameFrame.SCREEN_HEIGHT/2 - 15;
                y1 = yMid - GameFrame.SCREEN_HEIGHT/2 - openIntroGameY/2;
                y2 = yMid + openIntroGameY/2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT/2);
                break;
        }
    }

    public void Update() {
        hero.update();
        camera.Update();
        bulletManager.UpdateObjects();
        redEyeDevil.Update();
        switch (state) {
            case INIT_GAME:
                break;
            case TUTORIAL:
                TutorialUpdate();
                break;
            case GAMEPLAY:
                hero.update();
                camera.Update();
                bulletManager.UpdateObjects();
                redEyeDevil.Update();

                if(hero.getPosX() > finalBossX && finalbossTrigger){
                    finalbossTrigger = false;
                    switchState(TUTORIAL);
                    tutorialState = MEETFINALBOSS;
                    storyTutorial = 0;
                    openIntroGameY = 550;

                    boss = new FinalBoss(finalBossX + 700, 460, this);
                    boss.setTeamType(ParticularObject.ENEMY_TEAM);
                    boss.setDirection(ParticularObject.LEFT_DIR);
                    particularObjectManager.addObject(boss);

                }

                if(hero.getState() == ParticularObject.DEATH) {
                    numberOfLife--;
                    if(numberOfLife >= 0) {
                        hero.setBlood(100);
                        hero.setPosY(hero.getPosY() - 50);
                        hero.setState(ParticularObject.NOBEHURT);
                        particularObjectManager.addObject(hero);
                    }
                    else {
                        switchState(GAMEOVER);
                    }
                }
                if(!finalbossTrigger && boss.getState() == ParticularObject.DEATH) {
                    switchState(GAMEWIN);
                }
                break;
            case GAMEOVER:
                break;
            case GAMEWIN:
                break;
        }
    }

    public void Render() {
        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        if(g2 != null) {
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
            switch(state) {
                case INIT_GAME:
                    if(titleScreenState == 0) {

                        g2.setColor(Color.BLACK);
                        g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);

                        // ten game
                        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
                        String text = "LIGHT UP";
                        int x = getXforCenteredText(text, 80);
                        int y = 100;

                        // bong cua chu
                        g2.setColor(Color.gray);
                        g2.drawString(text, x + 5, y + 5);

                        // mau chinh
                        g2.setColor(Color.white);
                        g2.drawString(text, x, y);

                        // menu
                        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
                        text = "Bắt đầu";
                        x = getXforCenteredText(text, 48);
                        y = 300;
                        g2.drawString(text, x, y);

                        if(commandNum == 0) {
                            g2.drawString(">", x - 50, y);
                        }

                        text = "Hướng dẫn";
                        x = getXforCenteredText(text, 48);
                        y = 400;
                        g2.drawString(text, x, y);

                        if(commandNum == 1) {
                            g2.drawString(">", x - 50, y);
                        }

                        text = "Thoát";
                        x = getXforCenteredText(text, 48);
                        y = 500;
                        g2.drawString(text, x, y);

                        if(commandNum == 2) {
                            g2.drawString(">", x - 50, y);
                        }


                    }
                    else if(titleScreenState == 1) {
                        g2.setColor(Color.BLACK);
                        g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                        g2.setColor(Color.white);
                        g2.setFont(g2.getFont().deriveFont(42F));
                        String text = "Hướng dẫn tân thủ:\n" +
                                "Nhiệm vụ của bạn là: giải cứu mỹ diệu \n" +
                                " và tìm đường ra khỏi mê cung.\n" +
                                "1. Sử dụng phím mũi tên để di chuyển.\n" +
                                "2. Dùng nút C để tấn công.\n" +
                                "3. Enter để chọn các mục trên màn hình chính";
                        int x = getXforCenteredText(text, 42f);
                        int y = 50;
                        for(String str : text.split("\n")) {
                            x = getXforCenteredText(str, 42f);
                            y += 50;
                            g2.drawString(str, x, y);
                        }

                        text  = "Trở về";
                        x = getXforCenteredText(text, 42f);
                        y = 500;
                        g2.drawString(text, x, y);
                        if(commandNum == 0) {
                            g2.drawString(">", x - 50, y);
                        }
                    }


                    /*g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO START", 400, 300);*/
                    break;
                case TUTORIAL:
                    //backgroundMap.draw(g2);
                    if(tutorialState == MEETFINALBOSS) {
                        //particularObjectManager.draw(g2);
                    }
                    TutorialRender(g2);
                    break;
                case GAMEWIN:
                case GAMEPLAY:
                    // backgroundMap.draw(g2);
                    //particularObjectManager.draw(g2);
                    hero.draw(g2);
                    physicalMap.draw(g2);
                    bulletManager.draw(g2);
                    redEyeDevil.draw(g2);

                    g2.setColor(Color.gray);
                    g2.fillRect(19, 59, 102, 22);
                    g2.setColor(Color.red);
                    g2.fillRect(20, 60, hero.getBlood(), 20);

                    break;
                case GAMEOVER:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                    break;
            }
        }
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