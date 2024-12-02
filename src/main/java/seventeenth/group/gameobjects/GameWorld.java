package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;

import java.awt.Graphics2D;

public class GameWorld {

    public ParticularObjectManager particularObjectManager;
    //public GameObject camera;
    public Hero hero;
    protected PhysicalMap physicalMap;
    public Camera camera;



    public GameWorld() {
        //hero = new Hero(300, 300, 100, 100, 0.1f);
        hero = new Hero(300, 150, this);
        physicalMap = new PhysicalMap(0, 0, this);
        camera = new Camera(0, 50, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
        particularObjectManager = new ParticularObjectManager(this);

    }

    public void Update() {
        //Cập nhật vị trí hero theo tốc độ
        hero.setPosX(hero.getPosX() + hero.getSpeedX());
        hero.setPosY(hero.getPosY() + hero.getSpeedY());
        particularObjectManager.UpdateObjects();
        physicalMap.Update();
        camera.Update();
        hero.Update();
    }

    public void Render(Graphics2D g2) {
        hero.draw(g2);
        physicalMap.draw(g2);
    }
}
