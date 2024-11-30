package seventeenth.group.gameobjects;

import seventeenth.group.state.GameWorldState;
import seventeenth.group.effect.Animation;
import seventeenth.group.effect.CacheDataLoader;
import java.applet.AudioClip;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Hero extends Human {
    public static final int RUNSPEED = 3;

    private Animation runForwardAnim, runBackAnim;
    private Animation idleForwardAnim, idleBackAnim, idleShootingForwardAnim, idleShootingBackAnim;
    private Animation deadForwardAnim, deadBackAnim;

    private long lastShootingTime;
    private boolean isShooting = false;

    private AudioClip hurtingSound;
    private AudioClip shooting1;

    public Hero(float x, float y, GameWorld gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);

        //shooting1 = CacheDataLoader.getInstance().getSound("");
        //hurtingSound = CacheDataLoader.getInstance().getSound("");

        setTeamType(LEAGUE_TEAM);

        setTimeForNoBehurt(2000*1000000); //thời gian miễn nhiễm sát thương (2s)


        //lấy cái animation từ lớp CacheDataLoader
        runForwardAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim = CacheDataLoader.getInstance().getAnimation("run");
        runBackAnim.flipAllImage();

        idleForwardAnim = CacheDataLoader.getInstance().getAnimation("idle_2");
        idleBackAnim = CacheDataLoader.getInstance().getAnimation("idle_2");
        idleBackAnim.flipAllImage();


        behurtForwardAnim = CacheDataLoader.getInstance().getAnimation("hurt");
        behurtBackAnim = CacheDataLoader.getInstance().getAnimation("hurt");
        behurtBackAnim.flipAllImage();

        idleShootingForwardAnim = CacheDataLoader.getInstance().getAnimation("shot_1");
        idleShootingBackAnim = CacheDataLoader.getInstance().getAnimation("shot_1");
        idleShootingBackAnim.flipAllImage();

        deadForwardAnim = CacheDataLoader.getInstance().getAnimation("dead");
        deadBackAnim = CacheDataLoader.getInstance().getAnimation("dead");
        deadBackAnim.flipAllImage();



    }

    @Override
    public void Update() {

        super.Update();

        if(isShooting){
            if(System.nanoTime() - lastShootingTime > 500*1000000){
                isShooting = false;
            }
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();

        rect.x = (int) getPosX() - 22;
        rect.y = (int) getPosY() - 40;
        rect.width = 44;
        rect.height = 80;


        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {

        switch(getState()){

            case ALIVE:
            case NOBEHURT:
                if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1) //trạng thái nhân vật nhấp nháy miễn nhiễm sát thương
                {
                    System.out.println("Plash...");
                }else{
                    if(getSpeedX() > 0){
                        runForwardAnim.Update(System.nanoTime());
                        if(isShooting){
                        }else
                            runForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        if(runForwardAnim.getCurrentFrame() == 1) runForwardAnim.setIgnoreFrame(0);
                    }else if(getSpeedX() < 0){
                        runBackAnim.Update(System.nanoTime());
                        if(isShooting){
                        }else
                            runBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                        if(runBackAnim.getCurrentFrame() == 1) runBackAnim.setIgnoreFrame(0);
                    }else{
                        if(getDirection() == RIGHT_DIR){
                            if(isShooting){
                                idleShootingForwardAnim.Update(System.nanoTime());
                                idleShootingForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else{
                                idleForwardAnim.Update(System.nanoTime());
                                idleForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        }else{
                            if(isShooting){
                                idleShootingBackAnim.Update(System.nanoTime());
                                idleShootingBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }else{
                                idleBackAnim.Update(System.nanoTime());
                                idleBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                            }
                        }
                    }
                }


                break;

            case BEHURT:
                if(getDirection() == RIGHT_DIR){
                    behurtForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    behurtBackAnim.setCurrentFrame(behurtForwardAnim.getCurrentFrame());
                    behurtBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;

            case FEY:

                break;

            case DEATH:
                if(getBlood()==0 && getDirection() == RIGHT_DIR){
                    deadForwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }else{
                    deadBackAnim.setCurrentFrame(deadForwardAnim.getCurrentFrame());
                    deadBackAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) getPosY() - (int) getGameWorld().camera.getPosY(), g2);
                }
                break;


        }

    }

    @Override
    public void run() {
        if(getDirection() == LEFT_DIR)
            setSpeedX(-3);
        else setSpeedX(3);
    }


    @Override
    public void stopRun() {
        setSpeedX(0);
        runForwardAnim.reset();
        runBackAnim.reset();
        runForwardAnim.unIgnoreFrame(0);
        runBackAnim.unIgnoreFrame(0);
    }

    @Override
    public void attack() {

        if(!isShooting){

            shooting1.play();

            Bullet bullet = new BlueFire(getPosX(), getPosY(), getGameWorld());
            if(getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-10);
                bullet.setPosX(bullet.getPosX() - 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() - 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }else {
                bullet.setSpeedX(10);
                bullet.setPosX(bullet.getPosX() + 40);
                if(getSpeedX() != 0 && getSpeedY() == 0){
                    bullet.setPosX(bullet.getPosX() + 10);
                    bullet.setPosY(bullet.getPosY() - 5);
                }
            }

            bullet.setTeamType(getTeamType());
            getGameWorld().bulletManager.addObject(bullet);

            lastShootingTime = System.nanoTime();
            isShooting = true;

        }

    }
    @Override
    public void hurtingCallback(){
        System.out.println("Call back hurting");
        hurtingSound.play();
    }
}
