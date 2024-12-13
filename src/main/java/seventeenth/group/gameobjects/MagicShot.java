package seventeenth.group.gameobjects;

import seventeenth.group.effect.Animation;
import seventeenth.group.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MagicShot extends BulletNext {

    private Animation forwardBulletAnim, backBulletAnim;

    public MagicShot (float x, float y, GameWorld gameWorld){
        super(x,y, 60, 30, 10, gameWorld);
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("magicshot");
        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("magicshot");
        backBulletAnim.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy(){
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            if(! forwardBulletAnim.isIgnoreFrame(0) && forwardBulletAnim.getCurrentFrame() == 2){
                forwardBulletAnim.setIgnoreFrame(0);
                forwardBulletAnim.setIgnoreFrame(1);
                //backBulletAnim.setIgnoreFrame(2);
            }
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
        else{
            if(! backBulletAnim.isIgnoreFrame(0) && backBulletAnim.getCurrentFrame() == 2){
                backBulletAnim.setIgnoreFrame(0);
                backBulletAnim.setIgnoreFrame(1);
                //backBulletAnim.setIgnoreFrame(2);
            }
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
    }

    @Override
    public void Update(){
        super.Update();
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet set behurt for hero");
        }
    }

    @Override
    public void attack() {
    }
}
