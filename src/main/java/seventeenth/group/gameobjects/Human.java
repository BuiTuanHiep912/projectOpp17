package seventeenth.group.gameobjects;

import java.awt.*;

public abstract class Human extends ParticularObject {

    public Human(float x, float y, float width, float height, int blood, GameWorld gameWorld) {
        super(x, y, width, height, blood, gameWorld);
        setState(ALIVE);
    }

    public abstract void run();

    public abstract void stopRun();

    @Override
    public void Update() {
        super.Update();
        //checkWallCollision();
        if (getState() == ALIVE || getState() == NOBEHURT) {


            setPosX(getPosX() + getSpeedX());


            if (getDirection() == LEFT_DIR &&
                    getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {

                Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);

            }
            if (getDirection() == RIGHT_DIR &&
                    getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {

                Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
                setPosX(rectRightWall.x - getWidth() / 2);

            }

            Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();
            boundForCollisionWithMapFuture.y += (getSpeedY() != 0 ? getSpeedY() : 2);
            Rectangle rectLand = getGameWorld().physicalMap.haveCollisionWithBottomWall(boundForCollisionWithMapFuture);

            Rectangle rectTop = getGameWorld().physicalMap.haveCollisionWithTopWall(boundForCollisionWithMapFuture);

            if (rectTop != null) {

                setSpeedY(0);
                setPosY(rectTop.y + getGameWorld().physicalMap.getTileSize() + getHeight() / 2);
            }

        }
    }



    }
