import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;

import static javax.swing.UIManager.*;

public class SnakeGame extends JFrame {
    // Game constants
    private static final int TILE_SIZE = 25;
    private static final int GRID_WIDTH = 25;
    private static final int GRID_HEIGHT = 20;
    private static final int GAME_SPEED = 120;

    // Colors
    private final Color BACKGROUND_COLOR = new Color(15, 56, 15);
    public final Color SNAKE_HEAD_COLOR = new Color(130, 50, 205);
    private final Color SNAKE_BODY_COLOR = new Color(139, 116, 34);
    private final Color FOOD_COLOR = new Color(220, 20, 60);
    private final Color WALL_COLOR = new Color(139, 69, 19);
    private final Color SCORE_COLOR = new Color(255, 215, 0);
    private final Color GAME_OVER_COLOR = new Color(255, 69, 0);

    // Game components
    private GamePanel gamePanel;
    private Timer gameTimer;
    private Random random;

    // Game state
    private LinkedList<Point> snake;
    private Point food;
    private int direction; // 0: UP, 1: RIGHT, 2: DOWN, 3: LEFT
    private int nextDirection;
    private boolean gameRunning;
    private int score;
    private int highScore;
    private boolean paused;

    public SnakeGame() {
        initializeGame();
        setupUI();
        startGame();
    }

    private void initializeGame() {
        setTitle("SnakeGameJava");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        random = new Random();
        snake = new LinkedList<>();
        gameRunning = false;
        paused = false;
        score = 0;
        highScore = 0;

        // Initialize game panel
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(GRID_WIDTH * TILE_SIZE, GRID_HEIGHT * TILE_SIZE));
        gamePanel.setBackground(BACKGROUND_COLOR);

        add(gamePanel);
        pack();
        setLocationRelativeTo(null);

        // Setup keyboard controls
        setupControls();
    }

    private void setupUI() {
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem pauseItem = new JMenuItem("Pause/Resume");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> togglePause());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameItem);
        gameMenu.add(pauseItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem controlsItem = new JMenuItem("Controls");
        JMenuItem aboutItem = new JMenuItem("About");

        controlsItem.addActionListener(e -> showControls());
        aboutItem.addActionListener(e -> showAbout());

        helpMenu.add(controlsItem);
        helpMenu.add(aboutItem);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
    }

    private void setupControls() {
        InputMap inputMap = gamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = gamePanel.getActionMap();

        // Arrow keys
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");

        // WASD keys
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "up");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "right");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), "down");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "left");

        // Other controls
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "pause");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "restart");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "pause");

        actionMap.put("up", new DirectionAction(0));
        actionMap.put("right", new DirectionAction(1));
        actionMap.put("down", new DirectionAction(2));
        actionMap.put("left", new DirectionAction(3));
        actionMap.put("pause", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
        actionMap.put("restart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        actionMap.put("exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void startGame() {
        // Initialize snake in the center
        snake.clear();
        int startX = GRID_WIDTH / 2;
        int startY = GRID_HEIGHT / 2;
        snake.add(new Point(startX, startY));
        snake.add(new Point(startX - 1, startY));
        snake.add(new Point(startX - 2, startY));

        direction = 1; // Start moving right
        nextDirection = direction;
        score = 0;
        gameRunning = true;
        paused = false;

        spawnFood();

        // Setup game timer
        if (gameTimer != null) {
            gameTimer.stop();
        }
        gameTimer = new Timer(GAME_SPEED, new GameLoop());
        gameTimer.start();

        gamePanel.requestFocusInWindow();
    }

    private void spawnFood() {
        while (true) {
            int x = random.nextInt(GRID_WIDTH);
            int y = random.nextInt(GRID_HEIGHT);
            food = new Point(x, y);

            // Check if food spawns on snake
            boolean onSnake = false;
            for (Point segment : snake) {
                if (segment.equals(food)) {
                    onSnake = true;
                    break;
                }
            }

            if (!onSnake) break;
        }
    }

    private void moveSnake() {
        if (paused || !gameRunning) return;

        direction = nextDirection;
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        // Calculate new head position based on direction
        switch (direction) {
            case 0: newHead.y--; break; // UP
            case 1: newHead.x++; break; // RIGHT
            case 2: newHead.y++; break; // DOWN
            case 3: newHead.x--; break; // LEFT
        }

        // Check collision with walls
        if (newHead.x < 0 || newHead.x >= GRID_WIDTH || newHead.y < 0 || newHead.y >= GRID_HEIGHT) {
            gameOver();
            return;
        }

        // Check collision with self
        for (Point segment : snake) {
            if (segment.equals(newHead)) {
                gameOver();
                return;
            }
        }

        // Move snake
        snake.addFirst(newHead);

        // Check if food eaten
        if (newHead.equals(food)) {
            score += 10;
            if (score > highScore) {
                highScore = score;
            }
            spawnFood();
        } else {
            snake.removeLast();
        }

        gamePanel.repaint();
    }

    private void gameOver() {
        gameRunning = false;
        gameTimer.stop();
        gamePanel.repaint();
    }

    private void togglePause() {
        if (gameRunning) {
            paused = !paused;
            gamePanel.repaint();
        }
    }

    private void showControls() {
        String message = """
            🎮 GAME CONTROLS:
            
            Movement:
            ↑ / W - Move Up
            → / D - Move Right
            ↓ / S - Move Down
            ← / A - Move Left
            
            Game Controls:
            SPACE / P - Pause/Resume
            ENTER - New Game
            ESC - Exit Game
            
            Objective:
            • Eat the red food to grow
            • Avoid walls and yourself
            • Get the highest score!
            """;

        JOptionPane.showMessageDialog(this, message, "Game Controls", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showAbout() {
        String message = """
            SnakeGameJav
            Version 1.0
            
            Created with Java Swing
            Features:
            • Colorful graphics
            • Smooth animations
            • Multiple control schemes
            • Score tracking
            • Pause functionality
            
            Enjoy the game! 🎯
            """;

        JOptionPane.showMessageDialog(this, message, "About Snake Game", JOptionPane.INFORMATION_MESSAGE);
    }

    // Custom panel for game rendering
    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw walls/border
            g2d.setColor(WALL_COLOR);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(0, 0, GRID_WIDTH * TILE_SIZE - 1, GRID_HEIGHT * TILE_SIZE - 1);

            if (!gameRunning && score == 0) {
                drawWelcomeScreen(g2d);
                return;
            }

            // Draw food
            g2d.setColor(FOOD_COLOR);
            int foodX = food.x * TILE_SIZE;
            int foodY = food.y * TILE_SIZE;
            g2d.fillOval(foodX + 2, foodY + 2, TILE_SIZE - 4, TILE_SIZE - 4);

            // Draw snake glow effect
            g2d.setStroke(new BasicStroke(1));
            g2d.setColor(new Color(50, 205, 50, 100));
            for (Point segment : snake) {
                int x = segment.x * TILE_SIZE;
                int y = segment.y * TILE_SIZE;
                g2d.fillOval(x - 2, y - 2, TILE_SIZE + 4, TILE_SIZE + 4);
            }

            // Draw snake body
            for (int i = 1; i < snake.size(); i++) {
                Point segment = snake.get(i);
                int x = segment.x * TILE_SIZE;
                int y = segment.y * TILE_SIZE;

                // Gradient effect for body
                float ratio = (float) i / snake.size();
                Color bodyColor = new Color(
                        (int) (SNAKE_BODY_COLOR.getRed() * (1 - ratio) + 20 * ratio),
                        (int) (SNAKE_BODY_COLOR.getGreen() * (1 - ratio) + 100 * ratio),
                        (int) (SNAKE_BODY_COLOR.getBlue() * (1 - ratio))
                );

                g2d.setColor(bodyColor);
                g2d.fillRoundRect(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4, 8, 8);

                // Body pattern
                g2d.setColor(new Color(0, 100, 0));
                g2d.fillOval(x + 8, y + 8, 4, 4);
                g2d.fillOval(x + TILE_SIZE - 12, y + 8, 4, 4);
                g2d.fillOval(x + 8, y + TILE_SIZE - 12, 4, 4);
                g2d.fillOval(x + TILE_SIZE - 12, y + TILE_SIZE - 12, 4, 4);
            }

            // Draw snake head
            if (!snake.isEmpty()) {
                Point head = snake.getFirst();
                int headX = head.x * TILE_SIZE;
                int headY = head.y * TILE_SIZE;

                g2d.setColor(SNAKE_HEAD_COLOR);
                g2d.fillRoundRect(headX + 2, headY + 2, TILE_SIZE - 4, TILE_SIZE - 4, 10, 10);

                // Draw eyes
                g2d.setColor(Color.WHITE);
                int eyeSize = 6;
                int eyeOffset = 6;

                // Eye positions based on direction
                int leftEyeX, leftEyeY, rightEyeX, rightEyeY;

                switch (direction) {
                    case 0: // UP
                        leftEyeX = headX + eyeOffset;
                        leftEyeY = headY + eyeOffset;
                        rightEyeX = headX + TILE_SIZE - eyeOffset - eyeSize;
                        rightEyeY = headY + eyeOffset;
                        break;
                    case 1: // RIGHT
                        leftEyeX = headX + TILE_SIZE - eyeOffset - eyeSize;
                        leftEyeY = headY + eyeOffset;
                        rightEyeX = headX + TILE_SIZE - eyeOffset - eyeSize;
                        rightEyeY = headY + TILE_SIZE - eyeOffset - eyeSize;
                        break;
                    case 2: // DOWN
                        leftEyeX = headX + eyeOffset;
                        leftEyeY = headY + TILE_SIZE - eyeOffset - eyeSize;
                        rightEyeX = headX + TILE_SIZE - eyeOffset - eyeSize;
                        rightEyeY = headY + TILE_SIZE - eyeOffset - eyeSize;
                        break;
                    case 3: // LEFT
                        leftEyeX = headX + eyeOffset;
                        leftEyeY = headY + eyeOffset;
                        rightEyeX = headX + eyeOffset;
                        rightEyeY = headY + TILE_SIZE - eyeOffset - eyeSize;
                        break;
                    default:
                        leftEyeX = headX + eyeOffset;
                        leftEyeY = headY + eyeOffset;
                        rightEyeX = headX + TILE_SIZE - eyeOffset - eyeSize;
                        rightEyeY = headY + eyeOffset;
                }

                g2d.fillOval(leftEyeX, leftEyeY, eyeSize, eyeSize);
                g2d.fillOval(rightEyeX, rightEyeY, eyeSize, eyeSize);

                // Pupils
                g2d.setColor(Color.BLACK);
                g2d.fillOval(leftEyeX + 1, leftEyeY + 1, eyeSize - 2, eyeSize - 2);
                g2d.fillOval(rightEyeX + 1, rightEyeY + 1, eyeSize - 2, eyeSize - 2);
            }

            // Draw score
            drawHUD(g2d);

            // Draw game over screen
            if (!gameRunning && score > 0) {
                drawGameOverScreen(g2d);
            }

            // Draw pause screen
            if (paused) {
                drawPauseScreen(g2d);
            }
        }

        private void drawWelcomeScreen(Graphics2D g2d) {
            g2d.setColor(new Color(255, 255, 255, 200));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(new Color(25, 25, 112));
            g2d.setFont(new Font("Arial", Font.BOLD, 36));

            String title = "SNAKE GAME";
            FontMetrics fm = g2d.getFontMetrics();
            int titleWidth = fm.stringWidth(title);
            g2d.drawString(title, (getWidth() - titleWidth) / 2, getHeight() / 3);

            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            g2d.setColor(Color.DARK_GRAY);

            String[] messages = {
                    "Use ARROW KEYS or WASD to move",
                    "Press SPACE to pause/resume",
                    "Press ENTER to start new game",
                    "Press ESC to exit",
                    "",
                    "Click anywhere or press ENTER to START!"
            };

            for (int i = 0; i < messages.length; i++) {
                int msgWidth = g2d.getFontMetrics().stringWidth(messages[i]);
                g2d.drawString(messages[i], (getWidth() - msgWidth) / 2, getHeight() / 2 + i * 30);
            }
        }

        private void drawHUD(Graphics2D g2d) {
            g2d.setFont(new Font("Arial", Font.BOLD, 16));

            // Current score
            g2d.setColor(SCORE_COLOR);
            String scoreText = "Score: " + score;
            g2d.drawString(scoreText, 10, 20);

            // High score
            String highScoreText = "High Score: " + highScore;
            int highScoreWidth = g2d.getFontMetrics().stringWidth(highScoreText);
            g2d.drawString(highScoreText, getWidth() - highScoreWidth - 10, 20);

            // Snake length
            g2d.setColor(Color.WHITE);
            String lengthText = "Length: " + snake.size();
            int lengthWidth = g2d.getFontMetrics().stringWidth(lengthText);
            g2d.drawString(lengthText, (getWidth() - lengthWidth) / 2, 20);
        }

        private void drawGameOverScreen(Graphics2D g2d) {
            // Semi-transparent overlay
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(GAME_OVER_COLOR);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));

            String gameOver = "GAME OVER";
            FontMetrics fm = g2d.getFontMetrics();
            int gameOverWidth = fm.stringWidth(gameOver);
            g2d.drawString(gameOver, (getWidth() - gameOverWidth) / 2, getHeight() / 2 - 50);

            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.setColor(Color.WHITE);

            String scoreText = "Final Score: " + score;
            int scoreWidth = g2d.getFontMetrics().stringWidth(scoreText);
            g2d.drawString(scoreText, (getWidth() - scoreWidth) / 2, getHeight() / 2);

            String restartText = "Press ENTER to play again";
            int restartWidth = g2d.getFontMetrics().stringWidth(restartText);
            g2d.drawString(restartText, (getWidth() - restartWidth) / 2, getHeight() / 2 + 50);
        }

        private void drawPauseScreen(Graphics2D g2d) {
            // Semi-transparent overlay
            g2d.setColor(new Color(0, 0, 0, 150));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 48));

            String pause = "PAUSED";
            FontMetrics fm = g2d.getFontMetrics();
            int pauseWidth = fm.stringWidth(pause);
            g2d.drawString(pause, (getWidth() - pauseWidth) / 2, getHeight() / 2);

            g2d.setFont(new Font("Arial", Font.PLAIN, 18));
            g2d.setColor(Color.WHITE);

            String continueText = "Press SPACE to continue";
            int continueWidth = g2d.getFontMetrics().stringWidth(continueText);
            g2d.drawString(continueText, (getWidth() - continueWidth) / 2, getHeight() / 2 + 40);
        }
    }

    // Action for direction changes
    private class DirectionAction extends AbstractAction {
        private final int newDirection;

        public DirectionAction(int direction) {
            this.newDirection = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (paused || !gameRunning) return;

            // Prevent 180-degree turns
            if ((newDirection == 0 && direction != 2) || // UP, not from DOWN
                    (newDirection == 1 && direction != 3) || // RIGHT, not from LEFT
                    (newDirection == 2 && direction != 0) || // DOWN, not from UP
                    (newDirection == 3 && direction != 1)) { // LEFT, not from RIGHT
                nextDirection = newDirection;
            }
        }
    }

    // Game loop
    private class GameLoop implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            moveSnake();
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            setLookAndFeel(getLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the game window
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }

}
