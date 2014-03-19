package hu.relek.solve2048.players;

import hu.relek.solve2048.controller.Player;
import hu.relek.solve2048.logic.Direction;
import hu.relek.solve2048.logic.PlayerInterface;
import hu.relek.solve2048.stategraph.MachineMove;
import hu.relek.solve2048.stategraph.StateGraphNode;
import hu.relek.solve2048.stategraph.StateGraphNodeImpl;

import java.util.ArrayList;
import java.util.List;

public class HighestExpectedScorePlayer implements Player {

	private PlayerInterface iface;
	private List<ScoreComputerThread> threads = new ArrayList<ScoreComputerThread>(4);
	
	private class ScoreComputerThread extends Thread {
		
		private int idx;
		private StateGraphNode node;
		private int maxDepth;
		private int result;
		
		private boolean dataValid = false;
		private boolean resultValid = false;
		
		private Object startLock = new Object();
		private Object resultLock = new Object();
		
		
		public ScoreComputerThread(int idx) {
			super("HighestExpectedScorePlayer score computer thread " + idx);
			this.idx = idx;
		}
		
		public void setNode(StateGraphNode node) {
			this.node = node;
		}
		
		public void setMaxDepth(int maxDepth) {
			this.maxDepth = maxDepth;
		}
		
		public void startTask() {
			synchronized(startLock) {
				resultValid = false;
				dataValid = true;
				startLock.notifyAll();
			}
		}
		
		@Override
		public void run() {
			while (true) {
				while (!dataValid) {
					try {
						synchronized (startLock) {
							startLock.wait();
						}
					} catch (InterruptedException e) {
						continue;
					}
				}
				
				result = computeExpectedScore(node, 0, maxDepth);
				resultValid = true;
				dataValid = false;
				synchronized (resultLock) {
					resultLock.notifyAll();
				}
			}
		}
		
		public int getResult() {
			while (!resultValid) {
				try {
					synchronized (resultLock) {
						resultLock.wait();
					}
				} catch (InterruptedException e) {
					continue;
				}
			}
			
			return result;
		}
	}
	
	public HighestExpectedScorePlayer() {
		for (int i = 0; i < 4; i++) {
			ScoreComputerThread thread = new ScoreComputerThread(i);
			threads.add(thread);
			thread.start();
		}
		System.out.println("HESP thread pool is up");
	}
	
	@Override
	public void setIface(PlayerInterface iface) {
		this.iface = iface;
	}

	@Override
	public void turn() {
		StateGraphNode node = new StateGraphNodeImpl(iface.takeSnapshot());
		
		List<Direction> validMoves = node.getValidMoves();
		System.out.println("Valid moves: " + validMoves);
		
		Direction bestMove = null;
		int maxScore = -1;
		
		for (Direction dir : validMoves) {
			ScoreComputerThread thread = threads.get(dir.ordinal());
			thread.setNode(node.move(dir));
			thread.setMaxDepth(node.getValidMachineMoves().size() > 12 ? 4 : 6);
			thread.startTask();
		}
		
		for (Direction dir : validMoves) {
//			int expectedScore = computeExpectedScore(node.move(dir), 0, node.getValidMachineMoves().size() > 8 ? 3 : 5);
			int expectedScore = threads.get(dir.ordinal()).getResult();
			
			System.out.println("Move " + dir + " has expected score " + expectedScore);
			
			if (expectedScore > maxScore) {
				bestMove = dir;
				maxScore = expectedScore;
			}
		}
		
		System.out.println("Moving " + bestMove);
		iface.slide(bestMove);
		
	}
	
	/**
	 * The maxDepth parameter provides a way to tune performance. Higher numbers yield better
	 * decisions, lower numbers makes the decision process faster.
	 * 
	 * @param node
	 * @param depth
	 * @param maxDepth Maximum depth of recursion.
	 * @return
	 */
	private int computeExpectedScore(StateGraphNode node, int depth, int maxDepth) {
		/* If it is the player's turn, and it is game over, the expected score is the current score.
		 * If it is the computer's turn, there is always at least one possible move, the one created
		 * by the player in the last turn, as it always leaves at least one free cell.
		 * 
		 * For other states, there are 2 cases:
		 *  - Computer's turn: the expected score is the weighted average of expected score for all possible moves.
		 *  - Player's turn: the expected score is the highest expected score for all possible moves.
		 */
		
		boolean machinesTurn = (depth % 2 == 0);
		
		if (!machinesTurn && depth > maxDepth) {
			return node.getContents().getScore();
		}
		
		if (node.getContents().isGameOver()) { //No game over possible if it is the computer's turn
			return node.getContents().getScore();
		}
		
		if (machinesTurn) {
			List<MachineMove> validMoves = node.getValidMachineMoves();
			double sumExpectedScore = 0;
			double sumWeights = 0;
			for (MachineMove move : validMoves) {
				double weight = ((move.getB() == 2) ? 0.9 : 0.1);
				sumWeights += weight;
				sumExpectedScore += computeExpectedScore(node.machineMove(move), depth + 1, maxDepth) * weight;
			}
			
			return (int)Math.round(sumExpectedScore / sumWeights);
		} else {
			List<Direction> validMoves = node.getValidMoves();
			
			int maxScore = -1;
			
			for (Direction dir : validMoves) {
				int expectedScore = computeExpectedScore(node.move(dir), depth + 1, maxDepth);
				
				if (expectedScore > maxScore) {
					maxScore = expectedScore;
				}
			}
			
			return maxScore;
		}
	}

}
