package seventeenth.group.gameobjects;
import seventeenth.group.effect.CacheDataLoader;
import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.userinterface.GameFrame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
public class BackgroundMap extends GameObject {
    public int[][] map;
    private int tileSize;

    public BackgroundMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
        map = CacheDataLoader.getInstance().getBackgroundMap();
        tileSize = 32;
    }

    public void Update() {
    }

    public void draw(Graphics2D g2) {
        Camera camera = getGameWorld().camera;

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0 && j * tileSize - camera.getPosX() > -32 &&
                        j * tileSize - camera.getPosX() < GameFrame.SCREEN_WIDTH &&
                        i * tileSize - camera.getPosY() > -32 &&
                        i * tileSize - camera.getPosY() < GameFrame.SCREEN_HEIGHT) {
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Tile" + map[i][j]).getImage(), (int)getPosX() + j * tileSize - (int)camera.getPosX(), (int)getPosY() + i * tileSize - (int)camera.getPosY(), null);
                }
            }
        }

    }
}
