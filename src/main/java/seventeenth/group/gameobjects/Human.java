package seventeenth.group.gameobjects;

import java.awt.*;

public abstract class Human extends ParticularObject {

    Human(float x, float y, float width, float height, int blood, GameWorld gameWorld) {
        super(x, y, width, height, blood, gameWorld);
        setState(ALIVE);
    }

    public abstract void run();
    public abstract void stopRun();

    public void Update()  {
        super.Update();
        if(getState() == ALIVE || getState() == NOBEHURT) {
            setPosX(getPosX() + getSpeedX());
            setPosY(getPosY() + getSpeedY());
            Rectangle rect = getBoundForCollisionWithMap();
            if(getDirection() == LEFT_DIR) {
                Rectangle Land = getGameWorld().physicalMap.haveCollisionWithLeftWall(rect);
                if(Land != null) setPosX(Land.x +  getGameWorld().physicalMap.getTileSize() + getWidth() / 2);
            }
            else if(getDirection() == RIGHT_DIR) {
                Rectangle Land = getGameWorld().physicalMap.haveCollisionWithRightWall(rect);
                if(Land != null) setPosX(Land.x - getWidth() / 2);
            }
            else if(getDirection() == UP_DIR) {
                Rectangle Land = getGameWorld().physicalMap.haveCollisionWithTop(rect);
                if(Land != null) setPosY(Land.y + getGameWorld().physicalMap.getTileSize() + getHeight() / 2);
            }
            else if(getDirection() == DOWN_DIR) {
                Rectangle Land = getGameWorld().physicalMap.haveCollisionWithLand(rect);
                if(Land != null) setPosY(Land.y - getHeight() / 2);
            }
        }
    }
}
