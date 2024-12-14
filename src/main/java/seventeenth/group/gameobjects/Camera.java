package seventeenth.group.gameobjects;

import seventeenth.group.userinterface.GameFrame;

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

    public void lock() {
        this.isLocked = true;
    }

    public void unLock() {
        this.isLocked = false;
    }

    @Override
    public void Update() {
        if(!isLocked) {
            Hero mainCharacter = getGameWorld().hero;

            if(mainCharacter.getPosX() - getPosX() > 350) {
                    setPosX(mainCharacter.getPosX() - 350);
            }
            else if(mainCharacter.getPosX() - getPosX() < 350) {
                if(mainCharacter.getPosX() - 350 >= 0)
                    setPosX(mainCharacter.getPosX() - 350);
            }

            if(mainCharacter.getPosY() - getPosY() > 400){
                setPosY(mainCharacter.getPosY() - 400);
            }
            else if(mainCharacter.getPosY() - getPosY() < 250) {
                if(mainCharacter.getPosY() - 250 >= 0)
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
