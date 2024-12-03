package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;
import seventeenth.group.effect.CacheDataLoader;
import seventeenth.group.effect.Animation;


import org.w3c.dom.css.Rect;
import java.awt.*;

public class Hero extends Human {

    GameWorld gameWorld;

    private Animation runForwardAnim, runBackAinm, runShootingForwardAim, runShootingBackAnim;
    private Animation idleStay;
    private Animation jump;

    public Hero(float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 75, 100,  gameWorld);
        setTeamType(LEAGUE_TEAM);
        setTimeForNoBeHurt(2000000000);
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAinm = CacheDataLoader.getInstance().getAnimation("run");
        runBackAinm.flipAllImage();
        idleStay = CacheDataLoader.getInstance().getAnimation("idle");
        jump = CacheDataLoader.getInstance().getAnimation("idle_2");
    }

    public void update() {
        super.Update();
        idleStay.Update(System.nanoTime());
        runForwardAnim.Update(System.nanoTime());
        runBackAinm.Update(System.nanoTime());
        jump.Update(System.nanoTime());

    }

    public void draw(Graphics2D g2) {
        //drawBoundForCollisionWithMap(g2);
        //drawBoundForCollisionWithEnemy(g2);
        switch(getState()) {
            case ALIVE:
            case NOBEHURT:

                if(getState() == NOBEHURT && (System.nanoTime() / 1000000000) % 2 == 1) {
                    System.out.println("Plash...");
                }
                else {
                    //System.out.println(getState());
                    if(getDirection() == STAY_DIR) {
                        idleStay.setCurrentFrame(idleStay.getCurrentFrame());
                        idleStay.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                      (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                    }
                    else if(getDirection() == RIGHT_DIR) {
                        runForwardAnim.setCurrentFrame(runForwardAnim.getCurrentFrame());
                        runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                            (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                    }
                    else if(getDirection() == LEFT_DIR) {
                        runBackAinm.setCurrentFrame(runBackAinm.getCurrentFrame());
                        runBackAinm.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                         (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                    }
                    else {
                        jump.setCurrentFrame(jump.getCurrentFrame());
                        jump.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                         (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                    }
                }
                break;
        }
    }

    @Override
    public void attack() {}

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.width -= 10;
        rect.x += 5;
        return rect;
    }

    public void hurtingCallback() {};

    @Override
    public void run() {
        System.out.println(getDirection());
        if(getDirection() == LEFT_DIR) {
            setSpeedX(-3);
            setSpeedY(0);
        }
        else if(getDirection() == RIGHT_DIR) {
            setSpeedX(3);
            setSpeedY(0);
        }
        else if(getDirection() == UP_DIR) {
            setSpeedX(0);
            setSpeedY(-3);
        }
        else if(getDirection() == DOWN_DIR) {
            setSpeedX(0);
            setSpeedY(3);
        }
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        setSpeedY(0);
        setDirection(STAY_DIR);
        /*runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);*/
    }
}
