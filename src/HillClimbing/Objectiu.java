package HillClimbing;

import aima.core.search.framework.problem.GoalTest;


public class Objectiu implements GoalTest {
    @Override
    public boolean isGoalState(Object state) {
        Ciutat ciutat = (Ciutat) state;
        //return board.getNumeroPassatgerAssignats() == board.getSize() && board.getDistanciaEstimadaTotal() == 0;
        return ciutat.getNumeroPassatgerAssignats() == ciutat.treballadors.size();
    }
}