# Architecture - MVC Design Pattern

## Overview
This Snake Game uses the **Model-View-Controller (MVC)** architecture for clean separation of concerns.

## Components

### Model (GameModel.java)
- **Responsibility:** Game logic and state
- **Features:** Snake movement, collision detection, food spawning, scoring
- **Independent:** Doesn't know about rendering or input handling

### View (GamePanel.java)
- **Responsibility:** Rendering graphics
- **Features:** Draws snake, food, score, game states
- **Read-only:** Only reads from model, never modifies it

### Controller (GameController.java)
- **Responsibility:** Input handling
- **Features:** Processes keyboard input, updates model
- **Bridge:** Connects user input to model operations

## Data Flow
```
User Input → Controller → Model → View (Render)
```

## Key Classes

1. **GameConfig.java** - Centralized configuration (board size, colors, difficulty)
2. **GameState.java** - Enum for game states (MENU, RUNNING, PAUSED, GAME_OVER)
3. **SnakeGame.java** - Main frame and game loop coordinator

## Benefits

✅ **Separation of Concerns** - Each class has one responsibility
✅ **Testability** - Model can be tested independently
✅ **Maintainability** - Easy to modify without affecting other parts
✅ **Scalability** - Can add new features without major refactoring
✅ **Reusability** - Components can be reused in other projects

## Game Flow

1. **Initialization** - SnakeGame creates Model, View, Controller
2. **Game Loop** - Timer triggers model.update() every 100ms
3. **Input** - Controller processes keyboard events
4. **Update** - Model updates snake position, checks collisions
5. **Render** - View repaints based on model state

## Why MVC?

- **Professional Standard** - Used in enterprise applications
- **Easy Debugging** - Logic and UI are separated
- **Team Collaboration** - Multiple developers can work on different components
- **Testing** - Unit tests can focus on model logic without UI

---
**This architecture makes the code clean, maintainable, and professional!**
