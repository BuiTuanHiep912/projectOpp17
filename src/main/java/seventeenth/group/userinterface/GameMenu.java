package seventeenth.group.userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Tạo khung chính
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);

        // Tạo panel với ảnh nền
        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null); // Tắt layout mặc định

        // Tạo tên game và đặt nó ở tọa độ tự do
        JLabel gameTitleLabel = new JLabel("LIGHTUP", JLabel.CENTER);
        gameTitleLabel.setFont(new Font("Algerian", Font.BOLD, 80));
        gameTitleLabel.setForeground(Color.WHITE);
        gameTitleLabel.setBounds(300, 50, 400, 80);
        backgroundPanel.add(gameTitleLabel);

        // Tạo panel để chứa các nút
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1, 10, 0)); // Chia thành 4 hàng
        mainPanel.setBounds(300, 150, 400, 300);

        // Tạo các nút với cỡ chữ và màu sắc tùy chỉnh
        JButton newGameButton = new JButton("Bắt đầu");
        newGameButton.setFont(new Font("Bernard MT", Font.PLAIN, 30));
        newGameButton.setBackground(Color.BLACK);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JButton creditButton = new JButton("Credit");
        creditButton.setFont(new Font("Bernard MT", Font.PLAIN, 30));
        creditButton.setBackground(Color.BLACK);
        creditButton.setForeground(Color.WHITE);
        creditButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JButton guideButton = new JButton("Hướng dẫn");
        guideButton.setFont(new Font("Bernard MT", Font.PLAIN, 30));
        guideButton.setBackground(Color.BLACK);
        guideButton.setForeground(Color.WHITE);
        guideButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        JButton settingsButton = new JButton("Cài đặt");
        settingsButton.setFont(new Font("Bernard MT", Font.PLAIN, 30));
        settingsButton.setBackground(Color.BLACK);
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        newGameButton.addActionListener(e -> startNewGame());
        creditButton.addActionListener(e -> showCredit());
        guideButton.addActionListener(e -> showGuide());
        settingsButton.addActionListener(e -> showSettings());

        mainPanel.add(newGameButton);
        mainPanel.add(creditButton);
        mainPanel.add(guideButton);
        mainPanel.add(settingsButton);

        // Đặt mainPanel vào backgroundPanel
        backgroundPanel.add(mainPanel);

        // Thêm backgroundPanel vào frame
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }




    private static void startNewGame() {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);
            gameFrame.startGame();
        });
    }

    private static void showCredit() {
        JOptionPane.showMessageDialog(null,
                "Danh sách nhà sáng tạo:\n" +
                        "1. Bùi Tuấn Hiệp - 20235074.\n" +
                        "2. Dương Thái Hiệp - 20235075.\n" +
                        "3. Đỗ Hồng Đức - 20235040.\n" +
                        "4. Hoàng Đức Khánh - 20235117.\n" +
                        "Tài trợ cho nhà phát triển qua stk 0912420180 - MB");
    }

    private static void showGuide() {
        JOptionPane.showMessageDialog(null,
                "Hướng dẫn tân thủ:\n" +
                        "Nhiệm vụ của bạn là giải cứu mỹ diệu và tìm đường ra khỏi mê cung.\n" +
                        "1. Sử dụng phím mũi tên để di chuyển.\n" +
                        "2. Dùng nút Spacebar để tấn công.\n" +
                        "3. Dùng phím C để nhặt đồ.\n" +
                        "4. Dùng phím F để thắp sáng.\n");
    }

    private static void showSettings() {
        JFrame settingsFrame = new JFrame("Cài đặt");
        settingsFrame.setSize(300, 150);
        settingsFrame.setLocationRelativeTo(null);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());

        JLabel volumeLabel = new JLabel("Volume: ");
        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        settingsPanel.add(volumeLabel, BorderLayout.WEST);
        settingsPanel.add(volumeSlider, BorderLayout.CENTER);

        settingsFrame.add(settingsPanel);
        settingsFrame.setVisible(true);
    }
}
