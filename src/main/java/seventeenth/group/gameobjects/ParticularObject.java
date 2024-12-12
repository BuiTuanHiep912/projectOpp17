package seventeenth.group.gameobjects;

//import seventeenth.group.state.GameWorldState;
import seventeenth.group.effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject{
    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;

    public static final int STAY_DIR = 0;
    public static final int LEFT_DIR = 1;
    public static final int RIGHT_DIR = 2;
    public static final int UP_DIR = 3;
    public static final int DOWN_DIR = 4;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2; // sap chet
    public static final int DEATH = 3; // chet
    public static final int NOBEHURT = 4;
    private int state = ALIVE;

    protected float width;
    protected float height;
    private float speedX;
    private float speedY;
    private int blood;

    private int damage;

    private int direction;

    protected Animation behurtForwardAnim, behurtBackAnim;

    private int teamType;

    private long startTimeNoBeHurt;
    private long timeForNoBeHurt;

    ParticularObject(float x, float y, float width, float height, int blood, GameWorld gameWorld) {
        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);
        setBlood(blood);
        direction = STAY_DIR;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damge) {
        this.damage = damge;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public long getTimeForNoBeHurt() {
        return timeForNoBeHurt;
    }

    public void setTimeForNoBeHurt(long timeForNoBeHurt) {
        this.timeForNoBeHurt = timeForNoBeHurt;
    }

    public abstract void attack();

    // hinh chu nhat bao quanh hero
    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.y = (int) (getPosY() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void beHurt(int damageEat) {
        setBlood(getBlood() - damageEat);
        state = BEHURT;
        hurtingCallback();
    }

    public boolean isObjectOutOfCameraView(){
        if ((getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView()) ||
                (getPosX() - getGameWorld().camera.getPosX() < -50) ||
                (getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView())
                || (getPosY() - getGameWorld().camera.getPosY() < -50)) {
            return true;
        }
        else {
            return false;
        }
    }

    public void Update() {
        switch(state) {
            case ALIVE:
                // hehe
                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if(object!=null){
                    
                    
                    if(object.getDamage() > 0){

                        // switch state to fey if object die
                        
                        
                        System.out.println("eat damage.... from collision with enemy........ "+object.getDamage());
                        beHurt(object.getDamage());
                    }
                    
                }
                
                
                
                break;
                
                break;
            case BEHURT:
                if(behurtBackAnim == null) {
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if(getBlood() == 0) {
                        state = FEY;
                    }
                }
                else {
                    behurtForwardAnim.Update(System.nanoTime());
                    if(behurtForwardAnim.isLastFrame()) {
                        behurtForwardAnim.reset();
                        state = NOBEHURT;
                        if(getBlood() == 0) {
                            state = FEY;
                        }
                        startTimeNoBeHurt = System.nanoTime();
                    }
                }
                break;
            case FEY:
                state = DEATH;
                break;
            case DEATH:

                break;
            case NOBEHURT:
                System.out.println("state = NoBeHurt");
                if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt) {
                    state = ALIVE;
                }
                break;

        }
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
        //g2.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
        //g2.drawRect(rect.x, rect.y, rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);

    public void hurtingCallback(){};
}
