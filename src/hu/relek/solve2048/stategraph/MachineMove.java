package hu.relek.solve2048.stategraph;

import hu.relek.numbertable.Coordinates;

public class MachineMove implements Pair<Coordinates, Integer> {

	private Coordinates cell;
	private Integer value;
	
	public MachineMove(Coordinates cell, Integer value) {
		this.cell = cell;
		this.value = value;
	}
	
	@Override
	public Coordinates getA() {
		return cell;
	}

	@Override
	public Integer getB() {
		return value;
	}

}
