package seventeenth.group.gameobjects;
import seventeenth.group.effect.CacheDataLoader;
import seventeenth.group.gameobjects.GameWorld;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
public class BackgroundMap extends GameObject {
    public int[][] map = CacheDataLoader.getInstance().getBackgroundMap();
    private int tileSize = 32;

    public BackgroundMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
    }

    public void Update() {
    }

    public void draw(Graphics2D g2) {
        Camera camera = this.getGameWorld().camera;
        g2.setColor(Color.RED);

        for(int i = 0; i < this.map.length; ++i) {
            for(int j = 0; j < this.map[0].length; ++j) {
                if (this.map[i][j] != 0 && (float)(j * this.tileSize) - camera.getPosX() > -30.0F && (float)(j * this.tileSize) - camera.getPosX() < 1000.0F && (float)(i * this.tileSize) - camera.getPosY() > -30.0F && (float)(i * this.tileSize) - camera.getPosY() < 600.0F) {
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Tile" + this.map[i][j]).getImage(), (int)this.getPosX() + j * this.tileSize - (int)camera.getPosX(), (int)this.getPosY() + i * this.tileSize - (int)camera.getPosY(), (ImageObserver)null);
                }
            }
        }

    }
}
