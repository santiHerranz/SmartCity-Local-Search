package test;

import HillClimbing.Ciutat;
import HillClimbing.Objectiu;
import aima.core.util.datastructure.XYLocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Ravi Mohan
 * 
 */
public class CiutatGoalTestTest {
	Objectiu goalTest;

	Ciutat board;

	@Before
	public void setUp() {
		goalTest = new Objectiu();
		board = new Ciutat(8);
	}

	@Test
	public void testEmptyBoard() {
		Assert.assertFalse(goalTest.isGoalState(board));
	}

	@Test
	public void testSingleSquareBoard() {
		board = new Ciutat(1);
		Assert.assertFalse(goalTest.isGoalState(board));
		board.assignar_ruta(new XYLocation(0, 0));
		Assert.assertTrue(goalTest.isGoalState(board));
	}

	@Test
	public void testCorrectPlacement() {

		Assert.assertFalse(goalTest.isGoalState(board));

		for (int i = 0; i < board.treballadors.size(); i++) {
			board.assignar_ruta(new XYLocation(i, i));
		}


		Assert.assertTrue(goalTest.isGoalState(board));

		System.out.print(  board.getBoardPic());

	}
}
