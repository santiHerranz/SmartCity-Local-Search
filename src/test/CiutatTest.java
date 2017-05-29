package test;

import HillClimbing.Accions;
import HillClimbing.Ciutat;
import HillClimbing.Resultat;
import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CiutatTest {
	ActionsFunction af;
	ResultFunction rf;
	Ciutat oneBoard;
	Ciutat eightBoard;

	@Before
	public void setUp() {
		oneBoard = new Ciutat(1);
		oneBoard.omplir(oneBoard.getSize());
		eightBoard = new Ciutat(8);
		eightBoard.omplir(eightBoard.getSize());


		af = new Accions();
		rf = new Resultat();
	}

	@Test
	public void testSimpleBoardSuccessorGenerator() {

		// No podem saber el # d'accions per que depen del regorregut plantejat

		List<Action> actions = new ArrayList<Action>(af.actions(oneBoard));
		//Assert.assertEquals(1, actions.size());

		printActions(actions);
/*

		Ciutat next = (Ciutat) rf.result(oneBoard, actions.get(0));
		Assert.assertEquals(1, next.getNumeroPassatgerAssignats());
*/
	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

	@Test
	public void testComplexBoardSuccessorGenerator() {
		/*
		List<Action> actions = new ArrayList<Action>(af.actions(eightBoard));
		Assert.assertEquals(8, actions.size());



		Ciutat next = (Ciutat) rf
				.result(eightBoard, actions.get(0));
		Assert.assertEquals(1, next.getNumeroPassatgerAssignats());


		actions = new ArrayList<Action>(af.actions(next));
		Assert.assertEquals(8, actions.size());
		*/

	}
}
