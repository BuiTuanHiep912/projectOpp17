package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;

import java.awt.Graphics2D;

public class GameWorld {

    public ParticularObjectManager particularObjectManager;
    public Hero hero;
    public PhysicalMap physicalMap;
    public BulletManager bulletManager;
    public Camera camera;
    public RedEyeDevil redEyeDevil;
    public FinalBoss finalBoss;

    public GameWorld() {
        particularObjectManager = new ParticularObjectManager(this);
        bulletManager = new BulletManager(this);
        // test FinalBoss : x=2400 y=2218
        hero = new Hero(70, 470,this);
        particularObjectManager.addObject(hero);

        physicalMap = new PhysicalMap(0, 0, this);

        camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH,  GameFrame.SCREEN_HEIGHT, this);

        redEyeDevil = new RedEyeDevil(300, 470, this);
        redEyeDevil.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(redEyeDevil);

        finalBoss = new FinalBoss(2874, 2218, this);
        finalBoss.setTeamType(ParticularObject.ENEMY_TEAM);
        particularObjectManager.addObject(finalBoss);
    }

    public void Update() {
        hero.update();
        camera.Update();
        bulletManager.UpdateObjects();
        particularObjectManager.UpdateObjects();
        redEyeDevil.Update();
        finalBoss.Update();
    }

    public void Render(Graphics2D g2) {
        hero.draw(g2);
        physicalMap.draw(g2);
        bulletManager.draw(g2);
        redEyeDevil.draw(g2);
        finalBoss.draw(g2);
    }

}