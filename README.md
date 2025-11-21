## SnakeGameJava

SnackeGame Java is a simple, interactive Snake game developed in Java. This project is perfect for beginners who want to practice Java programming skills. It demonstrates fundamental concepts such as object-oriented programming, basic game mechanics, and GUI development with Java. Use this repository as a hands-on introduction to game development in Java.
A modern, well-architected Snake Game built with Java Swing following **MVC (Model-View-Controller)** design patterns.

## ✨ Features

- **Professional Architecture** - MVC design pattern for maintainability
- **Configurable Difficulty** - Easy, Medium, Hard, Insane modes (press 1-4)
- **Modern UI** - Enhanced graphics with grid, snake eyes, food shine effects
- **Game States** - Menu, Playing, Paused, Game Over with overlays
- **Responsive Controls** - Smooth arrow key controls with input buffering
- **Statistics** - Real-time score, snake length, and difficulty display
- **Pause System** - Press SPACE to pause/resume gameplay
- **Clean Code** - Fully documented with Javadoc comments

## 🎮 How to Play

### Controls
- **Arrow Keys (↑↓←→)** - Move the snake
- **SPACE** - Start game / Pause / Resume / Restart
- **1-4** - Select difficulty (EASY, MEDIUM, HARD, INSANE)
- **Q** - Quit game

### Objective
- Eat food (red squares) to grow and score points
- Avoid colliding with walls and yourself
- Maximize your score!

### Scoring
- **+10 points** - For each food eaten
- Difficulty increases when changing levels

## 🚀 Quick Start

### Download & Run (Pre-built JAR)
```
java -jar SnakeGame.jar
```

### Build from Source
```
# Compile all files
javac src/*.java -d out/

# Create JAR file
jar cfe SnakeGame.jar SnakeGame -C out/ .

# Run JAR
java -jar SnakeGame.jar
```

### Run Directly (No JAR)
```
javac src/*.java -d out/
java -cp out/ SnakeGame
```

## 📁 Project Structure

```
SnakeGameJAVA/
├── src/
│   ├── SnakeGame.java          Main application & window
│   ├── GameModel.java          Game logic (Model)
│   ├── GamePanel.java          Rendering (View)
│   ├── GameController.java     Input handling (Controller)
│   ├── GameConfig.java         Configuration & constants
│   └── GameState.java          Game state enumeration
├── README.md                   This file
├── ARCHITECTURE.md             Design documentation
└── .gitignore
```

## 🏗️ Architecture

This project uses **MVC (Model-View-Controller)** architecture:

- **Model** (`GameModel.java`) - Pure game logic, state management
- **View** (`GamePanel.java`) - Rendering and visualization
- **Controller** (`GameController.java`) - Input handling and coordination

### Benefits
- ✅ Separation of concerns
- ✅ Easy to test and extend
- ✅ Maintainable and scalable
- ✅ Professional code structure

See `ARCHITECTURE.md` for detailed design documentation.

## 🎯 Difficulty Levels

| Level | Speed | Challenge |
|-------|-------|----------|
| EASY | 150ms | Beginner friendly |
| MEDIUM | 100ms | Balanced gameplay |
| HARD | 70ms | Fast-paced action |
| INSANE | 40ms | Extreme challenge |

## 💻 Requirements

- Java 8 or higher
- No external dependencies (pure Swing)

## 📊 Code Quality

- ✅ Full Javadoc documentation
- ✅ MVC design pattern
- ✅ Object-oriented principles
- ✅ Performance optimized
- ✅ Error handling

## 🎓 Learning Outcomes

This project demonstrates:
- Java Swing GUI development
- Game development fundamentals
- Design patterns (MVC)
- Object-oriented programming
- Event-driven programming
- Timer-based game loops
- Collision detection algorithms
- Data structures (LinkedList, HashSet)

## 🔮 Future Enhancements

- High score persistence (file/database)
- Multiplayer support
- Sound effects and music
- Power-ups and obstacles
- Leaderboard system
- Mobile port (Android)

## 📄 License

This project is open source and available for educational purposes.

## 👨‍💻 Author

**Aashish Kishor**  
GitHub: [@Aashishkishor](https://github.com/Aashishkishor)  
Project: SnakeGameJAVA

## 🙏 Acknowledgments

Built with Java Swing following professional game development patterns and best practices.

---

**Ready to play? Download and run - it's that easy!** 🚀
