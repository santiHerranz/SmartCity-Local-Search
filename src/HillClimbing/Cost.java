package HillClimbing;

import aima.core.agent.Action;
import aima.core.search.framework.problem.StepCostFunction;
import domini.Ruta;

import java.util.stream.Collectors;


public class Cost implements StepCostFunction {

    @Override
    public double c(Object c, Action action, Object o1) {

        Ciutat ciutat = (Ciutat) c;
        double max = ciutat.treballadors
                .stream()
                .filter(t -> t.assignat == false)
                .collect(Collectors.toList())
                .size()
                *Ciutat.MAX_DISTANCE;

        Ruta ruta = ((CiutatAction)action).getRuta();

        return max - ruta.getMillor().getDistancia();

    }
}
