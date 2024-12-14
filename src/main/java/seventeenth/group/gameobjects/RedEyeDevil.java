package seventeenth.group.gameobjects;

import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.effect.Animation;
import seventeenth.group.effect.CacheDataLoader;
import java.awt.*;

public class RedEyeDevil extends ParticularObject {

    private Animation forwardAnim, backAnim, deathAnim;
    
    private long startTimeToShoot;
    
    //private AudioClip shooting;
    
    private float x1, x2;
    
    public RedEyeDevil(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 10, gameWorld);
        backAnim = CacheDataLoader.getInstance().getAnimation("yureiwalk");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("yureiwalk");
        backAnim.flipAllImage();
        deathAnim = CacheDataLoader.getInstance().getAnimation("yureidead");
        startTimeToShoot = 0;
        setTimeForNoBeHurt(1000000000);   
        x1 = x - 150;
        x2 = x + 150;
        setSpeedX(1);
        setDamage(10);
    }

    @Override
    public void attack() {
    
    //     shooting.play();
    //     Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
    //     if(getDirection() == LEFT_DIR) bullet.setSpeedX(-8);
    //     else bullet.setSpeedX(8);
    //     bullet.setTeamType(getTeamType());
    //     getGameWorld().bulletManager.addObject(bullet);    
    
    }

    @Override
    public void Update(){
        super.Update();
        if(getPosX() < x1)
            setSpeedX(1);
        else if(getPosX() > x2)
            setSpeedX(-1);
        setPosX(getPosX() + getSpeedX());

        // if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
        //     attack();
        //     startTimeToShoot = System.nanoTime();
        // }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
    Rectangle rect = getBoundForCollisionWithMap();
    rect.x += 20;
    rect.width -= 40;
        
    return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getState()==ParticularObject.DEATH) {
            deathAnim.Update(System.nanoTime());
            deathAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
        }
        if(!isObjectOutOfCameraView() && getState()!=ParticularObject.DEATH){
        if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1 && getState()!=ParticularObject.DEATH){
            // plash...
        }else{
            if(getSpeedX()<0){
                backAnim.Update(System.nanoTime());
                backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
            }else{
                forwardAnim.Update(System.nanoTime());
                forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }
}