package hu.relek.solve2048.controller;

import hu.relek.solve2048.logic.MachineInterface;

public interface Machine {
	void setIface(MachineInterface iface);
	void turn();
}
