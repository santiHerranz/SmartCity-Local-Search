package HillClimbing;

import aima.core.search.framework.evalfunc.HeuristicFunction;

public class Heuristica implements HeuristicFunction {
    @Override
    public double h(Object state) {
        Ciutat board = (Ciutat) state;



        return board.getDistanciaEstimadaTotal();
    }
}
