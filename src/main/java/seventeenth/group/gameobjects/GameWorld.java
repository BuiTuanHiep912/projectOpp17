package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;

import java.awt.Graphics2D;

public class GameWorld {

    public Hero hero;
    public PhysicalMap physicalMap;
    public Camera camera;
    public RedEyeDevil redEyeDevil;
    public FinalBoss finalBoss;

    public GameWorld() {
        hero = new Hero(70, 470,this);
        physicalMap = new PhysicalMap(0, 0, this);
        camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH,  GameFrame.SCREEN_HEIGHT, this);
        redEyeDevil = new RedEyeDevil(500, 470, this);
        finalBoss = new FinalBoss(600, 460, this);
    }

    public void Update() {
        hero.update();
        camera.Update();
        redEyeDevil.Update();
        finalBoss.Update();
    }

    public void Render(Graphics2D g2) {
        hero.draw(g2);
        physicalMap.draw(g2);
        redEyeDevil.draw(g2);
        finalBoss.draw(g2);
    }

}
