package seventeenth.group.gameobjects;

import java.awt.Graphics2D;

public abstract class BulletNext extends ParticularObject {
    public BulletNext (float x, float y, float width, float height, int damage, GameWorld gameWorld) {
        super(x, y, width, height, 1, gameWorld);
        setDamage(damage);
    }
    public abstract void draw(Graphics2D g2d);
    public void Update(){
        super.Update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
    }
}
