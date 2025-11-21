/**
 * GameState - Enumeration of possible game states
 */
public enum GameState {
    MENU("Menu"),
    RUNNING("Running"),
    PAUSED("Paused"),
    GAME_OVER("Game Over");
    
    private final String displayName;
    
    GameState(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
