package seventeenth.group.gameobjects;

//import seventeenth.group.state.GameWorldState;
import seventeenth.group.effect.Animation;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class ParticularObject extends GameObject{
    public static final int LEAGUE_TEAM = 1;
    //đối tượng cùng phe nhân vật chính
    //khi chạm vào thì kh mất máu
    public static final int ENEMY_TEAM = 2;
    //ngược lại phía trên

    // Các hằng số cho hướng di chuyển
    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;
    public static final int UP_DIR = 2;    // Thêm hằng số cho hướng lên
    public static final int DOWN_DIR = 3;  // Thêm hằng số cho hướng xuống


    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2; //trạng thái sắp chết
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;
    private int state = ALIVE;
    //biến xác định xem nhân vật thuộc trạng thái nào trong 5 trạng thái trên

    //thông số chung của các đối tượng trong game
    private float width;
    private float height;
    private float mass;
    private float speedX;
    private float speedY;
    private int blood;

    private int damage;

    private int direction; //nhân giá trị LEFT_DIR hoặc RIGHT_DIR

    protected Animation behurtForwardAnim, behurtBackAnim;
    //các hiệu ứng mà mọi đối tượng đều có -> gộp để xử lý

    private int teamType; //kiểu team ->thuộc team nào team chính hay phản diện

    private long startTimeNoBeHurt; //thời gian bắt đầu miễn nhiễm sát thương
    private long timeForNoBeHurt;  //khoảng thời gian miễn nhiễm sát thương

    //public GameWorld gameWorld;

    public ParticularObject(float x, float y, float width, float height, int blood, GameWorld gameWorld){
        super(x, y, gameWorld); //kế thừa GameObject
        setWidth(width);
        setHeight(height);
        setBlood(blood);

        setSpeedX(0); // Tốc độ theo trục X (có thể thay đổi khi di chuyển)
        setSpeedY(0); // Tốc độ theo trục Y (có thể thay đổi khi di chuyển)

        direction = RIGHT_DIR;

    }
    //các hàm setter getter


    public void setTimeForNoBehurt(long time){
        timeForNoBeHurt = time;
    }

    public long getTimeForNoBeHurt(){
        return timeForNoBeHurt;
    }

    public void setState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return damage;
    }


    public void setTeamType(int team){
        teamType = team;
    }

    public int getTeamType(){
        return teamType;
    }

    public void setSpeedX(float speedX){
        this.speedX = speedX;
    }

    public float getSpeedX(){
        return speedX;
    }

    public void setSpeedY(float speedY){
        this.speedY = speedY;
    }

    public float getSpeedY(){
        return speedY;
    }

    public void setBlood(int blood){
        if(blood>=0)
            this.blood = blood;
        else this.blood = 0;
    }

    public int getBlood(){
        return blood;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getWidth(){
        return width;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public float getHeight(){
        return height;
    }

    public void setDirection(int dir){
        direction = dir;
    }

    public int getDirection(){
        return direction;
    }

    public abstract void attack();
    //chưa biết đối tượng tấn công như nào nên để hàm abstract

    public boolean isObjectOutOfCameraView(){

        if(getPosX() - getGameWorld().camera.getPosX() > getGameWorld().camera.getWidthView() ||
                getPosX() - getGameWorld().camera.getPosX() < -50
                ||getPosY() - getGameWorld().camera.getPosY() > getGameWorld().camera.getHeightView()
                ||getPosY() - getGameWorld().camera.getPosY() < -50)
            return true;
        else return false;


    }

    public Rectangle getBoundForCollisionWithMap(){
        //vùng va chạm dùng để kiểm tra va chạm giữa các đối tượng trong không gian
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth()/2));
        bound.y = (int) (getPosY() - (getHeight()/2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();

        return bound;
    }

    public void beHurt(int damgeEat){
        //đối tượng bị mất máu dự theo số dam mà đối tượng nhận
        setBlood(getBlood() - damgeEat);
        state = BEHURT; //trạng thái chuyển sang bị đau
        hurtingCallback(); //overwrite cho từng đối tượng cụ thể sau
    }

    @Override
    public void Update(){
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        //các case trạng thái
        switch(state){
            case ALIVE:

                ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
                if(object!=null){

                    if(object.getDamage() > 0){
                        System.out.println("eat damage.... from collision with enemy........ "+object.getDamage());
                        beHurt(object.getDamage());
                    }

                }

                break;

            case BEHURT:
                if(behurtBackAnim == null){ //trường hợp kh có animation cho đối tượng
                    state = NOBEHURT;
                    startTimeNoBeHurt = System.nanoTime();
                    if(getBlood() == 0)
                        state = FEY;

                } else {
                    behurtForwardAnim.Update(System.nanoTime());
                    if(behurtForwardAnim.isLastFrame()){ //frame cuối cùng của animation bị đau
                        behurtForwardAnim.reset(); //reset animation
                        state = NOBEHURT; //chuyển sang trạng thái miễn nhiễm
                        if(getBlood() == 0) //nếu máu về 0 thì chuyển sang trạng thái sắp chết
                            state = FEY;
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
                System.out.println("state = nobehurt");
                if(System.nanoTime() - startTimeNoBeHurt > timeForNoBeHurt) //hết trạng thái miễn nhiễm
                    state = ALIVE; //cập nhật trạng thái bình thường
                break;
        }

    }

    public void drawBoundForCollisionWithMap(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithMap(); //va chạm với map
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2){
        Rectangle rect = getBoundForCollisionWithEnemy(); //va chạm với enemy
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPosX(), rect.y - (int) getGameWorld().camera.getPosY(), rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);

    public void hurtingCallback(){};




}
