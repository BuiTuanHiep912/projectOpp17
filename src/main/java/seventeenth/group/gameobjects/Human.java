package seventeenth.group.gameobjects;

import seventeenth.group.gameobjects.ParticularObject;

import java.awt.*;

public abstract class Human extends ParticularObject {

    public Human(float x, float y, float width, float height, float mass, int blood, GameWorldState gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
    }

    public abstract void run();

    public abstract void stopRun();

    @Override
    public void Update() {
        super.Update();

        if (getState() == ALIVE || getState() == NOBEHURT) {
            // Kiểm tra va chạm với các bức tường
            checkWallCollision();
        }
    }

    private void checkWallCollision() {
        Rectangle futureBounds = getBoundForCollisionWithMap();

        // Kiểm tra va chạm với tường bên trái
        if (getDirection() == LEFT_DIR &&
                getGameWorld().physicalMap.haveCollisionWithLeftWall(futureBounds) != null) {
            Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(futureBounds);
            setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);
        }

        // Kiểm tra va chạm với tường bên phải
        if (getDirection() == RIGHT_DIR &&
                getGameWorld().physicalMap.haveCollisionWithRightWall(futureBounds) != null) {
            Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(futureBounds);
            setPosX(rectRightWall.x - getWidth() / 2);
        }

        // Kiểm tra va chạm với tường phía trên
        if (getDirection() == UP_DIR &&
                getGameWorld().physicalMap.haveCollisionWithTopWall(futureBounds) != null) {
            Rectangle rectTopWall = getGameWorld().physicalMap.haveCollisionWithTopWall(futureBounds);
            setPosY(rectTopWall.y + rectTopWall.height + getHeight() / 2);
        }

        // Kiểm tra va chạm với tường phía dưới
        if (getDirection() == DOWN_DIR &&
                getGameWorld().physicalMap.haveCollisionWithBottomWall(futureBounds) != null) {
            Rectangle rectBottomWall = getGameWorld().physicalMap.haveCollisionWithBottomWall(futureBounds);
            setPosY(rectBottomWall.y - getHeight() / 2);
        }
    }

    // Phương thức để di chuyển lên
    public void moveUp() {
        setDirection(UP_DIR);
        setPosY(getPosY() - getSpeed()); // Cập nhật vị trí Y
    }

    // Phương thức để di chuyển xuống
    public void moveDown() {
        setDirection(DOWN_DIR);
        setPosY(getPosY() + getSpeed()); // Cập nhật vị trí Y
    }

    // Phương thức để di chuyển trái
    public void moveLeft() {
        setDirection(LEFT_DIR);
        setPosX(getPosX() - getSpeed()); // Cập nhật vị trí X
    }

    // Phương thức để di chuyển phải
    public void moveRight() {
        setDirection(RIGHT_DIR);
        setPosX(getPosX() + getSpeed()); // Cập nhật vị trí X
    }
}