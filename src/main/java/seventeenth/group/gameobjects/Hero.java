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
    private Animation shotAnim;
    private boolean isShooting = false;
    private long lastShootingTime;

    public Hero(float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 75, 100,  gameWorld);
        setTeamType(LEAGUE_TEAM);
        setTimeForNoBeHurt(2000000000);
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAinm = CacheDataLoader.getInstance().getAnimation("run");
        runBackAinm.flipAllImage();
        idleStay = CacheDataLoader.getInstance().getAnimation("idle");
        jump = CacheDataLoader.getInstance().getAnimation("idle_2");
        shotAnim = CacheDataLoader.getInstance().getAnimation("shot_1");

    }

    public void update() {
        super.Update();
        idleStay.Update(System.nanoTime());
        runForwardAnim.Update(System.nanoTime());
        runBackAinm.Update(System.nanoTime());
        jump.Update(System.nanoTime());
        shotAnim.Update(System.nanoTime());
        if (this.isShooting && System.nanoTime() - this.lastShootingTime > 500000000L) {
            this.isShooting = false;
        }

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
                        if (isShooting){
                            shotAnim.Update(System.nanoTime());





                            shotAnim.setCurrentFrame(shotAnim.getCurrentFrame());
                            shotAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                        }
                        else {
                            runForwardAnim.setCurrentFrame(runForwardAnim.getCurrentFrame());
                            runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                        }
                    }
                    else if(getDirection() == LEFT_DIR) {
                        if(isShooting){
                            shotAnim.Update(System.nanoTime());
                            shotAnim.setCurrentFrame(shotAnim.getCurrentFrame());
                            shotAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                        }
                        else {
                            runBackAinm.setCurrentFrame(runBackAinm.getCurrentFrame());
                            runBackAinm.draw((int) (getPosX() - getGameWorld().camera.getPosX()),
                                    (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
                        }
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
    public void attack() {
        if (!isShooting) {
            BulletNext bulletNext = new BlueFire(this.getPosX(), this.getPosY(), this.getGameWorld());
            if ((getDirection() == STAY_DIR) || (getDirection() == RIGHT_DIR) || (getDirection() == LEFT_DIR) ) {
                ((BulletNext)bulletNext).setSpeedX(10.0F);
                ((BulletNext)bulletNext).setPosX(((BulletNext)bulletNext).getPosX() + 40.0F);
                if (this.getSpeedX() != 0.0F && this.getSpeedY() == 0.0F) {
                    ((BulletNext)bulletNext).setPosX(((BulletNext)bulletNext).getPosX() + 10.0F);
                    ((BulletNext)bulletNext).setPosY(((BulletNext)bulletNext).getPosY() + 5.0F);
                }
            }
            ((BulletNext)bulletNext).setTeamType(getTeamType());
            this.getGameWorld().bulletManager.addObject(bulletNext);
            this.lastShootingTime = System.nanoTime();
            this.isShooting = true;
        }

    }

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
        //System.out.println(getDirection());
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