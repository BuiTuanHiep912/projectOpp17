package seventeenth.group.userinterface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.*;
import javax.imageio.ImageIO;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel() {
        try {
            // Tải ảnh từ file
            File imageFile = new File("data/background.jpg");

            if (imageFile.exists()) {
                backgroundImage = ImageIO.read(imageFile);
            } else {
                System.out.println("Image not found!");
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ ảnh nền lên toàn bộ panel
        if (backgroundImage != null) {
            // Vẽ ảnh nền lên toàn bộ panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("Background image is null.");
        }
    }
}