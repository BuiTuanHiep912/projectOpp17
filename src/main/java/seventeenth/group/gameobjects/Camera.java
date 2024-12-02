package seventeenth.group.gameobjects;

import java.awt.*;

public class Camera extends GameObject {

    private float widthView;
    private float heightView;


    private boolean isLocked = false;

    public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
        super(x, y, gameWorld);
        this.widthView = widthView;
        this.heightView = heightView;
    }

    public void lock(){
        isLocked = true;
    }

    public void unlock(){
        isLocked = false;
    }

    @Override
    public void Update() {
        if (!isLocked) {
            Hero mainCharacter = getGameWorld().hero;

            // Điều chỉnh vị trí của Camera sao cho Hero luôn ở trong khu vực nhất định
            float offsetX = mainCharacter.getPosX() - getPosX();
            float offsetY = mainCharacter.getPosY() - getPosY();

            // Giới hạn độ di chuyển của camera
            if (offsetX > 400) {
                setPosX(mainCharacter.getPosX() - 400);
            } else if (offsetX < 200) {
                setPosX(mainCharacter.getPosX() - 200);
            }

            if (offsetY > 400) {
                setPosY(mainCharacter.getPosY() - 400);
            } else if (offsetY < 250) {
                setPosY(mainCharacter.getPosY() - 250);
            }
        }
    }


    public float getWidthView() {
        return widthView;
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }

    public float getHeightView() {
        return heightView;
    }

    public void setHeightView(float heightView) {
        this.heightView = heightView;
    }

}
