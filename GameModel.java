import java.awt.Point;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

public class GameModel {
    private LinkedList<Point> snakeBody;
    private Set<Point> snakeBodySet;
    private Point food;
    private int score;
    private int foodCount;
    private GameState state;
    private String currentDirection;
    private String nextDirection;
    private GameConfig.Difficulty difficulty;
    
    public GameModel() {
        this.snakeBody = new LinkedList<>();
        this.snakeBodySet = new HashSet<>();
        this.score = 0;
        this.foodCount = 0;
        this.state = GameState.MENU;
        this.difficulty = GameConfig.DEFAULT_DIFFICULTY;
        initializeGame();
    }
    
    private void initializeGame() {
        snakeBody.clear();
        snakeBodySet.clear();
        
        int centerX = GameConfig.BOARD_WIDTH / 2;
        int centerY = GameConfig.BOARD_HEIGHT / 2;
        
        snakeBody.add(new Point(centerX, centerY));
        snakeBody.add(new Point(centerX - 1, centerY));
        snakeBody.add(new Point(centerX - 2, centerY));
        
        snakeBodySet.addAll(snakeBody);
        
        currentDirection = "RIGHT";
        nextDirection = "RIGHT";
        generateFood();
    }
    
    public void startGame() {
        if (state == GameState.MENU || state == GameState.GAME_OVER) {
            initializeGame();
            score = 0;
            foodCount = 0;
            state = GameState.RUNNING;
        }
    }
    
    public void togglePause() {
        if (state == GameState.RUNNING) {
            state = GameState.PAUSED;
        } else if (state == GameState.PAUSED) {
            state = GameState.RUNNING;
        }
    }
    
    public boolean update() {
        if (state != GameState.RUNNING) {
            return state != GameState.GAME_OVER;
        }
        
        currentDirection = nextDirection;
        Point head = snakeBody.getFirst();
        Point newHead = calculateNewHeadPosition(head, currentDirection);
        
        if (isWallCollision(newHead)) {
            state = GameState.GAME_OVER;
            return false;
        }
        
        if (snakeBodySet.contains(newHead)) {
            state = GameState.GAME_OVER;
            return false;
        }
        
        snakeBody.addFirst(newHead);
        snakeBodySet.add(newHead);
        
        if (newHead.equals(food)) {
            score += GameConfig.POINTS_PER_FOOD;
            foodCount++;
            generateFood();
        } else {
            Point tail = snakeBody.removeLast();
            snakeBodySet.remove(tail);
        }
        
        return true;
    }
    
    private Point calculateNewHeadPosition(Point head, String direction) {
        switch (direction) {
            case "UP":
                return new Point(head.x, head.y - 1);
            case "DOWN":
                return new Point(head.x, head.y + 1);
            case "LEFT":
                return new Point(head.x - 1, head.y);
            case "RIGHT":
                return new Point(head.x + 1, head.y);
            default:
                return head;
        }
    }
    
    private boolean isWallCollision(Point point) {
        return point.x < 0 || point.x >= GameConfig.BOARD_WIDTH ||
               point.y < 0 || point.y >= GameConfig.BOARD_HEIGHT;
    }
    
    private void generateFood() {
        int x, y;
        do {
            x = (int) (Math.random() * GameConfig.BOARD_WIDTH);
            y = (int) (Math.random() * GameConfig.BOARD_HEIGHT);
        } while (snakeBodySet.contains(new Point(x, y)));
        
        food = new Point(x, y);
    }
    
    public void setDirection(String direction) {
        if ("UP".equals(direction) && !"DOWN".equals(currentDirection)) {
            nextDirection = "UP";
        } else if ("DOWN".equals(direction) && !"UP".equals(currentDirection)) {
            nextDirection = "DOWN";
        } else if ("LEFT".equals(direction) && !"RIGHT".equals(currentDirection)) {
            nextDirection = "LEFT";
        } else if ("RIGHT".equals(direction) && !"LEFT".equals(currentDirection)) {
            nextDirection = "RIGHT";
        }
    }
    
    public void setDifficulty(GameConfig.Difficulty difficulty) {
        this.difficulty = difficulty;
    }
    
    public LinkedList<Point> getSnakeBody() { return snakeBody; }
    public Point getFood() { return food; }
    public int getScore() { return score; }
    public int getFoodCount() { return foodCount; }
    public GameState getState() { return state; }
    public GameConfig.Difficulty getDifficulty() { return difficulty; }
    public int getSnakeLength() { return snakeBody.size(); }
}
