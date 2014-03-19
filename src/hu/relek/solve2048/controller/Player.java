package hu.relek.solve2048.controller;

import hu.relek.solve2048.logic.PlayerInterface;

public interface Player {
	void setIface(PlayerInterface iface);
	void turn();
}
