package HillClimbing;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.util.datastructure.XYLocation;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by santi on 28/05/2017.
 */
public class Accions implements ActionsFunction {
    @Override
        public Set<Action> actions(Object state) {
            Ciutat ciutat = (Ciutat) state;

            Set<Action> actions = new LinkedHashSet<Action>();

            int numeroPassatgerAssignats = ciutat.getNumeroPassatgerAssignats();
            int boardSize = ciutat.getSize();
            for (int i = 0; i < boardSize; i++) {
                XYLocation newLocation = new XYLocation(numeroPassatgerAssignats, i);
                if ((ciutat.pucPossarPassatger(newLocation))) {
                    actions.add(new CiutatAction(CiutatAction.ASSIGNAR_PASSATGER, newLocation));
                }
            }

            return actions;
        }
    }