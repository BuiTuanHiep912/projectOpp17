package seventeenth.group.gameobjects;

import seventeenth.group.effect.CacheDataLoader;

import java.awt.*;

public class PhysicalMap extends GameObject {
    public int[][] phys_map;
    private int tileSize;
    //private GameWorld gameWorld;
    public float x, y;

    public PhysicalMap(float x, float y, GameWorld gameWorld) {
        //this.x = x;
        //this.y = y;
        super(x, y, gameWorld); // Gọi constructor của lớp cha
        //this.gameWorld = gameWorld;
        this.tileSize = 32;
        phys_map = CacheDataLoader.getInstance().getPhysicalMap();
    }
    public int getTileSize() {
        return tileSize;
    }
    @Override
    public void Update() {
    }

    // Kiểm tra va chạm với tường trên
    public Rectangle haveCollisionWithTopWall(Rectangle rect) {
        int posX1 = rect.x / tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;

        int posY = rect.y / tileSize;

        if (posX1 < 0) posX1 = 0;
        if (posX2 >= phys_map[0].length) posX2 = phys_map[0].length - 1;

        for (int y = posY; y >= 0; y--) {
            for (int x = posX1; x <= posX2; x++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) x + (int) this.x + x * tileSize,
                            (int) y + (int) this.y + y * tileSize, tileSize, tileSize);
                    if (rect.intersects(r)) return r;
                }
            }
        }
        return null;
    }


    // Kiểm tra va chạm với tường dưới
    public Rectangle haveCollisionWithBottomWall(Rectangle rect) {
        int posX1 = rect.x / tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / tileSize;
        posX2 += 2;

        int posY = (rect.y + rect.height) / tileSize;

        if (posX1 < 0) posX1 = 0;
        if (posX2 >= phys_map[0].length) posX2 = phys_map[0].length - 1;

        for (int y = posY; y < phys_map.length; y++) {
            for (int x = posX1; x <= posX2; x++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) x + (int) this.x + x * tileSize,
                            (int) y + (int) this.y + y * tileSize, tileSize, tileSize);
                    if (rect.intersects(r)) return r;
                }
            }
        }
        return null;
    }

    // Kiểm tra va chạm với tường trái
    public Rectangle haveCollisionWithLeftWall(Rectangle rect) {
        int posY1 = rect.y / tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / tileSize;
        posY2 += 2;

        int posX1 = rect.x / tileSize;
        int posX2 = posX1 - 3;
        if (posX2 < 0) posX2 = 0;

        if (posY1 < 0) posY1 = 0;
        if (posY2 >= phys_map.length) posY2 = phys_map.length - 1;

        for (int x = posX1; x >= posX2; x--) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) x + (int) this.x + x * tileSize,
                            (int) y + (int) this.y + y * tileSize, tileSize, tileSize);
                    if (rect.intersects(r)) return r;
                }
            }
        }
        return null;
    }

    // Kiểm tra va chạm với tường phải
    public Rectangle haveCollisionWithRightWall(Rectangle rect) {
        int posY1 = rect.y / tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / tileSize;
        posY2 += 2;

        int posX1 = (rect.x + rect.width) / tileSize;
        int posX2 = posX1 + 3;
        if (posX2 >= phys_map[0].length) posX2 = phys_map[0].length - 1;

        if (posY1 < 0) posY1 = 0;
        if (posY2 >= phys_map.length) posY2 = phys_map.length - 1;

        for (int x = posX1; x <= posX2; x++) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle((int) x + (int) this.x + x * tileSize,
                            (int) y + (int) this.y + y * tileSize, tileSize, tileSize);
                    if (r.y < rect.y + rect.height - 1 && rect.intersects(r)) return r;
                }
            }
        }
        return null;
    }


    /*
    public void draw(Graphics2D g2) {
        g2.setColor(Color.gray);
        Camera camera = getGameWorld().camera;

        for(int i = 0; i < phys_map.length; i++) {
            for(int j = 0; j < phys_map[0].length; j++) {
                if(phys_map[i][j] != 0) {
                   g2.fillRect((int) x + j * tileSize, (int) y + i * tileSize, tileSize, tileSize);
                }
            }
        }
    }*/
    public void draw(Graphics2D g2){

        Camera camera = getGameWorld().camera;

        g2.setColor(Color.GRAY);
        for(int i = 0;i< phys_map.length;i++)
            for(int j = 0;j<phys_map[0].length;j++)
                if(phys_map[i][j]!=0)
                    g2.fillRect((int) getPosX() + j*tileSize - (int) camera.getPosX(),
                            (int) getPosY() + i*tileSize - (int) camera.getPosY(), tileSize, tileSize);

    }




}