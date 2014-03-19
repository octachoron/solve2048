package hu.relek.solve2048.logic;

public interface PlayerInterface {
	void slide(Direction dir);
	Game2048Table takeSnapshot();
}
