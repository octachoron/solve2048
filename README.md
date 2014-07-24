Solve2048
=========

## What is this?
An independent reimplementation of, and an AI for the recently popular game [2048](http://gabrielecirulli.github.io/2048/). Currently, the vast majority of it is the result of a weekend project to make an AI for the game. The main focus was initially the AI itself, but manual playing is possible, and the framework tries to make experimenting with your own algorithm easy.

## How to build?
The repository contains a minimal Eclipse 3.8 project you can import, then follow the usual steps. There is no special configuration in the project, so importing the sources into a blank project in your favourite IDE should also work. There is no build script at the moment.

## How to play?
Start the Solve2048 class. Without arguments, you'll get to a minimalistic text-based interface where you can play manually, similarly to the original game. Rules and scoring should be the same.

## Command line arguments
The program accepts at most two arguments. The first is the full class name of the Player implementation to use. This class determines what to do when it's the player's turn. The second argument is the full class name of the Machine implementation to use. This class determines what to do when it's the computer's turn. Choosing only the Machine implementation is currently not possible, but you can always choose the default Player as well. Starting the program with no arguments is equivalent to starting as
```
java -cp bin hu.relek.solve2048.Solve2048 hu.relek.solve2048.players.ConsolePlayer hu.relek.solve2048.logic.Normal2048Machine
```

## How to hack?
To implement a new Player algorithm, create a new class that implements the Player interface. The framework will call your setIface() method to give you a PlayerInterface you can use to interact with the table. The turn() method is called when it is your turn to move. Until some decent documentation is made, take a look at the default Players when something is unclear. Code to explore the state graph of the game is provided.

Implementing a Machine is very similar, if the need arises.

To use the new class, put it on the classpath, and give the full class name as a command line argument to Solve2048, as described above.

## What implementations are included?
Players:
 - RandomPlayer, chooses a legal move at random
 - GreedyPlayer, chooses the move that yields the highest immediate score gain
 - HighestExpectedScorePlayer, chooses the move with the highest expected score after a few turns
 - ConsolePlayer, asks what to do on the console

Machines:
 - Normal2048Machine, the default behaviour: randomly chooses an empty square, and puts there a 2 with 90% probability, or a 4 with 10% probability
 - ConsoleMachine, asks what to do on the console

## Is there a GUI?
No, not yet. A pluggable system similar to how Players and Machines are done is planned.

## How does the AI work?
Currently, the strongest Player, HighestExpectedScorePlayer, essentially does a depth-first search of the state graph to calculate the expected value of the score in a few turns for each legal move, and chooses the one where it is highest. (As it turns out, I reinvented the wheel with this approach, called an expectimax search. You learn something every day. :-) ) Less lookahead is done when there are a lot of empty cells, and each legal move is evaluated in parallel, to gain some speed on machines with parallel hardware. It does not stop when it gets the 2048 tile.

## Similar efforts
There is at least [one](http://ov3y.github.io/2048-AI/).
