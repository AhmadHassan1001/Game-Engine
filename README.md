# Game-Engine

## About
This project is part of Programming Languages Paradigms Course at `AU-FOE-CSED`, the goal of this code is better technical understanding for three paradigms which are:
- OOP using Javascript
- Functional Programming using Scala
- Logical Programming using Prolog

## Project
The primary objective of this project is to develop a Game Engine that simplifies the process of creating board-like games. The project is divided into two phases:
### First Phase (Game Controller)
Use scala and javascript to make two seperate Generic Game Engines and using each of them create 6 games (total of 12 games)
Games to be created:
- Tic-Tac-Toe
- Chess
- 8-Queens
- Connect 4
- Checkers
- Sudoku

The engine will have only two responsibilities: 
- Drawing the board and pieces
- Enforce the rules of moving pieces

The engine will NOT 
- Have intelligence for playing against the player
- Check the winner, or calculate the scores
- Save/Load the game status
---------------
### Second Phase (Game Solver)
This phase focus on developing a solver in the Scala engine for two specific games - Sudoku and 8-Queens. These solvers will be implemented using Prolog logical programming language. When playing either of these games, the user will have the option to click on a button that will solve the game in its current state. If the game is unsolvable, the solver will report that there is no solution available.

---------------
## Installation
- Javascript Installation:
  - Open repo on vscode 
  - [Install live server extension](https://www.geeksforgeeks.org/how-to-enable-live-server-on-visual-studio-code/)
  - Open `javascript\index.html`
  - Click `Go Live`
 
- Scala Installation:
  - Open **`Scala\scalaengine`** on vscode as main directory
  - [Install metals extension](https://scalameta.org/metals/docs/editors/vscode/)
  - In metals extension click "import build"
  - In metals extension click "Cascade compile"
  - In `src\main\scala\Main.scala` click run (at the top of the object `GameEngine`)
