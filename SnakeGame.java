import javax.swing.*;

public class SnakeGame extends JFrame {
    private GameModel model;
    private GamePanel gamePanel;
    private GameController controller;
    private Timer gameTimer;
    
    public SnakeGame() {
        setTitle("Snake Game - Professional Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        model = new GameModel();
        gamePanel = new GamePanel(model);
        controller = new GameController(model);
        
        add(gamePanel);
        gamePanel.addKeyListener(controller);
        gamePanel.setFocusable(true);
        
        pack();
        setVisible(true);
        
        startGameLoop();
    }
    
    private void startGameLoop() {
        gameTimer = new Timer(model.getDifficulty().getSpeed(), e -> {
            model.update();
            gamePanel.repaint();
        });
        gameTimer.start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame());
    }
}
