package hu.relek.solve2048.logic;

import hu.relek.numbertable.Coordinates;

import java.util.List;

public interface MachineInterface {
	void put(int value, Coordinates cell);
	List<Coordinates> getFreeCells();
}
