package seventeenth.group.gameobjects;

import seventeenth.group.effect.CacheDataLoader;

import java.awt.*;

public class PhysicalMap {
    public int[][] phys_map;
    private int tileSize;

    public float posX, posY;

    public PhysicalMap(float x, float y) {
        this.posX = x;
        this.posY = y;
        this.tileSize = 32;
        phys_map = CacheDataLoader.getInstance().getPhysicalMap();
    }

    public int getTileSize() {
        return tileSize;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.gray);
        for(int i = 0; i < phys_map.length; i++) {
            for(int j = 0; j < phys_map[0].length; j++) {
                if(phys_map[i][j] != 0) g2.fillRect((int) posX + j * tileSize,
                                                    (int) posY + i * tileSize, tileSize, tileSize);
            }
        }
    }

    public Rectangle haveCollisionWithLand(Rectangle rect) {
        int posX1 = rect.x/tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width)/tileSize;
        posX2 += 2;

        //int posY = (rect.y + rect.height)/tileSize;
        int posY1 = rect.y/tileSize;

        if(posX1 < 0) posX1 = 0;

        if(posX2 >= phys_map[0].length) posX2 = phys_map[0].length - 1;

        for(int y = posY1; y >= 0; y--){
            for(int x = posX1; x <= posX2; x++){

                if(phys_map[y][x] == 1){
                    Rectangle r = new Rectangle((int) posX + x * tileSize, (int) posY + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
}
