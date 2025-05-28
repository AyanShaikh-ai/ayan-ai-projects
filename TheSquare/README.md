# The Square – AI-Driven Grid-Based Strategy Game

**Developer:** Ayan Shaikh  
**Language:** Java (BlueJ)  
**Project Type:** AI-Based Game Simulation  
**Built for:** AI-focused coursework at King's College London

## Project Overview

**The Square** is a grid-based, text-driven strategy game developed in Java using BlueJ. The player navigates through a 5x5 grid starting from Room (1,1), aiming to reach the Exit at Room (5,5). The challenge lies in avoiding two AI-powered enemies — **Rook** and **Bishop** — each governed by unique, rule-based behavior engines that respond to player actions in real time.

The game showcases adversarial AI agents, decision-making logic, inventory systems, and dynamic command parsing — reflecting both programming depth and an understanding of AI behavior models.

## Key Features

### AI Behavior

- **Rook** senses adjacent rooms, unlocks/locks doors, and strategically moves to trap or avoid the player.  
- **Bishop** seeks the player and ends the game if the player is found without a screwdriver.  
- Both enemies operate autonomously, acting after every player move, simulating intelligent adversaries.

### Inventory & Commands

- Pickup/drop system for keys and screwdrivers  
- Locking/unlocking directional doors with keys  
- Command-driven gameplay with input parsing for actions such as `search`, `drop`, `map`, and more

### Map Rendering

- Dynamic ASCII-based map rendering through `MapRenderer.java`, showing:  
  - Player and robot positions  
  - Door states (locked/unlocked)  
  - Room structure (without revealing item positions)

### Command System

Commands include:
- `search` – reveals contents of a room  
- `drop <item>` – drop key or screwdriver  
- `pick-up <item>` – pick up a key or screwdriver  
- `lock/unlock <direction>` – lock or unlock doors  
- `map` – render current status of game board  

These commands are interpreted and executed by `Parser.java`, `ActionWords.java`, and `Action.java`.

## File Structure

| File | Description |
|------|-------------|
| `Game.java` | Main loop and game engine |
| `Room.java` | Grid room structure |
| `Square.java` | 5x5 game board logic |
| `MapRenderer.java` | ASCII map drawing |
| `Player.java` | Player actions and inventory |
| `Robot.java` | Base robot class |
| `Rook.java` | Advanced AI behavior with environment sensing |
| `Bishop.java` | Rule-based AI behavior for pursuit |
| `Action.java`, `ActionWords.java`, `Parser.java` | Command processing system |
| `SquareGame.jar` | Executable game build |

## Why This Project Matters

This project applies AI thinking in a constrained system:
- Simulated environment  
- Adversarial AI  
- Game-state manipulation  
- Decision-based control flow

It reflects:
- Strong Object-Oriented Design  
- Command Parsing and Game Loop Mechanics  
- Real-time AI Behavior Simulation

## How to Run

1. Open in BlueJ  
2. Instantiate the `Game` class  
3. Call the `play()` method to begin  

Or run the included `.jar` file (`SquareGame.jar`) if Java is installed.

## Relevance to MBZUAI

This project showcases:
- AI agent logic and autonomy  
- Algorithmic thinking  
- End-to-end solution development  
- Passion for blending coding with intelligent systems  

Developed during Ayan Shaikh’s AI degree at King’s College London as a stepping stone toward deeper AI research and applied innovation.
