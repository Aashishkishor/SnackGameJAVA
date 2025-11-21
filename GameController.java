import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameController extends KeyAdapter {
    private GameModel model;
    
    public GameController(GameModel model) {
        this.model = model;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            // Arrow keys for movement
            case KeyEvent.VK_UP:
                model.setDirection("UP");
                break;
            case KeyEvent.VK_DOWN:
                model.setDirection("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                model.setDirection("LEFT");
                break;
            case KeyEvent.VK_RIGHT:
                model.setDirection("RIGHT");
                break;
            
            // Spacebar for play/pause
            case KeyEvent.VK_SPACE:
                if (model.getState() == GameState.MENU) {
                    model.startGame();
                } else if (model.getState() == GameState.RUNNING || model.getState() == GameState.PAUSED) {
                    model.togglePause();
                } else if (model.getState() == GameState.GAME_OVER) {
                    model.startGame();
                }
                break;
            
            // Q to quit
            case KeyEvent.VK_Q:
                System.exit(0);
                break;
            
            // Difficulty selection (1, 2, 3, 4)
            case KeyEvent.VK_1:
                model.setDifficulty(GameConfig.Difficulty.EASY);
                break;
            case KeyEvent.VK_2:
                model.setDifficulty(GameConfig.Difficulty.MEDIUM);
                break;
            case KeyEvent.VK_3:
                model.setDifficulty(GameConfig.Difficulty.HARD);
                break;
            case KeyEvent.VK_4:
                model.setDifficulty(GameConfig.Difficulty.INSANE);
                break;
        }
    }
}
