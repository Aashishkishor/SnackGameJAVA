/**
 * GameConfig - Centralized game configuration and constants
 */
public class GameConfig {
    
    // Game Board Dimensions
    public static final int BOARD_WIDTH = 20;
    public static final int BOARD_HEIGHT = 20;
    public static final int TILE_SIZE = 20;
    
    // Window Dimensions
    public static final int WINDOW_WIDTH = BOARD_WIDTH * TILE_SIZE;
    public static final int WINDOW_HEIGHT = BOARD_HEIGHT * TILE_SIZE + 40;
    
    // Difficulty Levels
    public enum Difficulty {
        EASY(150),
        MEDIUM(100),
        HARD(70),
        INSANE(40);
        
        private final int speed;
        
        Difficulty(int speed) {
            this.speed = speed;
        }
        
        public int getSpeed() {
            return speed;
        }
    }
    
    // Color Scheme (RGB)
    public static final class Colors {
        public static final int BACKGROUND = 0x0F0F0F;
        public static final int SNAKE_HEAD = 0x00FF00;
        public static final int SNAKE_BODY = 0x00CC00;
        public static final int FOOD = 0xFF0000;
        public static final int TEXT = 0xFFFFFF;
        public static final int GRID = 0x333333;
    }
    
    // Scoring
    public static final int POINTS_PER_FOOD = 10;
    public static final int BONUS_PER_LEVEL = 50;
    
    // Game States
    public static final int INITIAL_SNAKE_LENGTH = 3;
    
    // Default difficulty
    public static final Difficulty DEFAULT_DIFFICULTY = Difficulty.MEDIUM;
}
