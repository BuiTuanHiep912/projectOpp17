package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;

import java.awt.Graphics2D;

public class GameWorld {

    public Hero hero;
    public PhysicalMap physicalMap;
    public Camera camera;
    public RedEyeDevil redEyeDevil;

    public GameWorld() {
        hero = new Hero(70, 470,this);
        physicalMap = new PhysicalMap(0, 0, this);
        camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH,  GameFrame.SCREEN_HEIGHT, this);
        redEyeDevil = new RedEyeDevil(1000, 470, this);
    }

    public void Update() {
        hero.update();
        camera.Update();
        redEyeDevil.Update();
    }

    public void Render(Graphics2D g2) {
        hero.draw(g2);
        physicalMap.draw(g2);
        redEyeDevil.draw(g2);
    }

}
