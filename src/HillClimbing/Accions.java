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

            int boardSize = ciutat.getSize();

            for (int row = 0; row < boardSize; row++) {
                int seientLliure = 0;
                    for (int col = 0; col < boardSize; col++) {
                         if (!ciutat.hiHaPassatger(col, row))
                             break;
                        seientLliure++;
                    }

                    XYLocation novaPossicio = new XYLocation( seientLliure, row);

                    if ((ciutat.pucAssignarPassatger(novaPossicio))) {
                        actions.add(new CiutatAction(CiutatAction.ASSIGNAR_PASSATGER, novaPossicio));
                    }
            }

        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                XYLocation possicioTreballador = new XYLocation(col, row);
                if ((ciutat.hiHaPassatger(possicioTreballador))) {
                    actions.add(new CiutatAction(CiutatAction.DESASSIGNAR_PASSATGER, possicioTreballador));
                }
            }
        }

            return actions;
        }
    }