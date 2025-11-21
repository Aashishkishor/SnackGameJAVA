import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameModel model;
    
    public GamePanel(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT));
        setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw snake (green)
        g2d.setColor(Color.GREEN);
        for (java.awt.Point segment : model.getSnakeBody()) {
            g2d.fillRect(segment.x * GameConfig.TILE_SIZE, segment.y * GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE - 1, GameConfig.TILE_SIZE - 1);
        }
        
        // Draw food (red)
        g2d.setColor(Color.RED);
        java.awt.Point food = model.getFood();
        if (food != null) {
            g2d.fillRect(food.x * GameConfig.TILE_SIZE, food.y * GameConfig.TILE_SIZE,
                    GameConfig.TILE_SIZE - 1, GameConfig.TILE_SIZE - 1);
        }
        
        // Draw score
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Score: " + model.getScore(), 10, GameConfig.WINDOW_HEIGHT - 10);
        g2d.drawString("Length: " + model.getSnakeLength(), 150, GameConfig.WINDOW_HEIGHT - 10);
        
        // Draw game over message
        if (model.getState() == GameState.GAME_OVER) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
            
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString("GAME OVER", GameConfig.WINDOW_WIDTH / 2 - 150, GameConfig.WINDOW_HEIGHT / 2);
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.PLAIN, 14));
            g2d.drawString("Final Score: " + model.getScore(), GameConfig.WINDOW_WIDTH / 2 - 100, GameConfig.WINDOW_HEIGHT / 2 + 40);
        }
        
        // Draw pause message
        if (model.getState() == GameState.PAUSED) {
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
            
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString("PAUSED", GameConfig.WINDOW_WIDTH / 2 - 120, GameConfig.WINDOW_HEIGHT / 2);
        }
    }
}
