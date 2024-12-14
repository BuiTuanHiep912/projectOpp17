package seventeenth.group.userinterface;

import seventeenth.group.effect.*;
import seventeenth.group.gameobjects.GameWorld;
import seventeenth.group.gameobjects.Hero;
import seventeenth.group.gameobjects.PhysicalMap;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    private Thread thread; // tien trinh

    private boolean isRunning;

    private InputManager inputManager;

    BufferedImage bufferedImage;
    Graphics2D bufferedGraphics2d;

    public GameWorld gameWorld;

    public GamePanel() {
        gameWorld = new GameWorld();
        inputManager = new InputManager(gameWorld);
        //bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public void paint(Graphics g) {
        g.drawImage(gameWorld.getBufferedImage(), 0, 0, this);
    }

    public void UpdateGame() {
        gameWorld.Update();
    }

    public void RenderGame() { // ve lai tren 1 image
        gameWorld.Render();
    }

    public void startGame() {
        if(thread == null) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {

        long FPS = 60;
        long period = 1000 * 1000000 / FPS; // nano second
        long beginTime;
        long sleepTime;

        beginTime = System.nanoTime();
        while(isRunning) {

            UpdateGame();
            RenderGame();
            repaint();

            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;

            try {
                if(sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else
                    Thread.sleep(sleepTime / 2000000);
            } catch (Exception ex) {}

            beginTime = System.nanoTime();
        }
    }

    public void keyTyped(KeyEvent e) { // go phim

    }

    public void keyReleased(KeyEvent e) { // tha phim
        inputManager.processKeyReleased(e.getKeyCode());
    }
    public void keyPressed(KeyEvent e) { // nhan phim
        inputManager.processKeyPressed(e.getKeyCode());
    }
}
