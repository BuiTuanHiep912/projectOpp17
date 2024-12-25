package seventeenth.group.gameobjects;

import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.effect.Animation;
import seventeenth.group.effect.CacheDataLoader;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class BoulderDrop extends BulletNext {
	
    private Animation boulder;
    
    public BoulderDrop(float x, float y, GameWorld gameWorld) {
        
            super(x, y, 75, 75, 10, gameWorld);
            
            boulder = CacheDataLoader.getInstance().getAnimation("boulder");

    }
  
    @Override
    public Rectangle getBoundForCollisionWithEnemy(){
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedY() > 0) {
            if(! boulder.isIgnoreFrame(0) && boulder.getCurrentFrame() == 2){
                boulder.setIgnoreFrame(0);
                boulder.setIgnoreFrame(1);
            }
            boulder.Update(System.nanoTime());
            boulder.draw((int) (getPosX() - getGameWorld().camera.getPosX()), (int) (getPosY() - getGameWorld().camera.getPosY()), g2);
        }
    }

    @Override
    public void Update(){
        super.Update();
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if(object!=null && object.getState() == ALIVE){
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
            System.out.println("Bullet set behurt for hero");
            System.out.println(getState());
        }
    }

    @Override
    public void attack() {
    }

}