package hu.relek.solve2048;

import hu.relek.solve2048.controller.GameOf2048;
import hu.relek.solve2048.controller.Machine;
import hu.relek.solve2048.controller.Player;
import hu.relek.solve2048.logic.Normal2048Machine;
import hu.relek.solve2048.players.ConsolePlayer;

public class Solve2048 {
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Player player;
		Machine machine;
		
		if (args.length == 0) {
			player = new ConsolePlayer();
			machine = new Normal2048Machine();
		} else if (args.length == 1) {
			player = (Player) Class.forName(args[0]).newInstance();
			machine = new Normal2048Machine();
		} else {
			player = (Player) Class.forName(args[0]).newInstance();
			machine = (Machine) Class.forName(args[1]).newInstance();
		}
		
		System.out.println("Starting a game of 2048.");
		System.out.println("Player class: " + player.getClass().getName());
		System.out.println("Machine class: " + machine.getClass().getName());
		
		GameOf2048 game = new GameOf2048(machine, player);
		game.start();
		
		System.out.println("Game over.");
	}

}
