package seventeenth.group.gameobjects;

public abstract class GameObject {
    //lớp cơ bản của các Object
    //Các lớp đối tượng khác đều kế thừa lớp GameObject
    private float posX;
    private float posY;
    //tọa độ của vật
    private GameWorldState gameWorld; //để các đối tượng có biến tham chiếu về GameObject
    //khởi tạo constructor
    public GameObject(float x, float y, GameWorldState gameWorld){
        posX = x;
        posY = y;
        this.gameWorld = gameWorld;
    }
    //tạo các phương thức getter setter
    public void setPosX(float x){
        posX = x;
    }

    public float getPosX(){
        return posX;
    }

    public void setPosY(float y){
        posY = y;
    }

    public float getPosY(){
        return posY;
    }

    public GameWorldState getGameWorld(){
        return gameWorld;
    }

    public abstract void Update();
    //phương thức abstract để các class kế thừa phải định nghĩa lại hàm Update()
}
