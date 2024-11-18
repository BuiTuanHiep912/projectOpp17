package seventeenth.group.userinterface;

import seventeenth.group.effect.*;
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

    /*BufferedImage image;
    BufferedImage supImage;

    FrameImage frame1, frame2, frame3;

    Animation animation;*/

    /*FrameImage frame1;
    Animation animation1;*/

    BufferedImage bufferedImage;
    Graphics2D bufferedGraphics2d;

    public GamePanel() {
        inputManager = new InputManager();
        /*try {
            BufferedImage image = ImageIO.read(new File("data/human/Dead.png"));
            BufferedImage image1 = image.getSubimage(25, 52, 74, 76);
            frame1 = new FrameImage("frame1", image1);
            BufferedImage image2 = image.getSubimage(153, 52, 74, 76);
            frame2 = new FrameImage("frame2", image2);
            BufferedImage image3 = image.getSubimage(279, 52, 91, 76);
            frame3 = new FrameImage("frame3", image3);
            animation = new Animation();
            animation.addFrame(frame1, 200*1000000);
            animation.addFrame(frame2, 200*1000000);
            animation.addFrame(frame3, 200*1000000);
        } catch (IOException ex) {}*/
        /*frame1 = CacheDataLoader.getInstance().getFrameImage("attack2");
        animation1 = CacheDataLoader.getInstance().getAnimation("hurt");*/
        /*animation1.fillAllImage();*/
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, this);

        /*Graphics2D g2 = (Graphics2D) g;
        animation.Update(System.nanoTime());
        animation.draw(100, 130, g2);*/

        /*Graphics2D g2 = (Graphics2D) g;
        frame1.draw(g2, 130, 130);

        animation1.draw(300, 300, g2);*/
    }

    public void RenderGame() { // ve lai tren 1 image
        if(bufferedImage == null) {
            bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }

        bufferedGraphics2d = (Graphics2D) bufferedImage.getGraphics();

        if(bufferedGraphics2d != null) {
            bufferedGraphics2d.setColor(Color.white);
            bufferedGraphics2d.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);

            /*bufferedGraphics2d.setColor(Color.red);
            bufferedGraphics2d.fillRect(40, 50, 100, 100);*/
            //animation1.draw(300, 300, bufferedGraphics2d);

        }
    }

    public void startGame() {
        if(thread == null) {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void run() {

        long FPS = 80;
        long period = 1000 * 1000000 / FPS; // nano second
        long beginTime;
        long sleepTime;

        beginTime = System.nanoTime();
        while(isRunning) {
            //System.out.println(a++);
            // Update


            RenderGame();
            //animation1.Update(System.nanoTime());
            repaint();
            /*animation1.Update(System.nanoTime());
            repaint();*/

            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;

            try {
                if(sleepTime > 0)
                    Thread.sleep(sleepTime / 1000000);
                else
                    Thread.sleep(sleepTime / 2000000);
            } catch (InterruptedException ex) {}

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
