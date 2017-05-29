package HillClimbing;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ResultFunction;

import java.util.Objects;

/**
 * Created by santi on 28/05/2017.
 */
public class Resultat implements ResultFunction {
    @Override
        public Object result(Object s, Action a) {
        Ciutat ciutat = (Ciutat) s;
            if (a instanceof CiutatAction) {
                CiutatAction qa = (CiutatAction) a;

                Ciutat newCiutat = new Ciutat(ciutat.getSize());
                newCiutat.copiarCiutat(ciutat);

                if (Objects.equals(qa.getName(), CiutatAction.ASSIGNAR_PASSATGER))
                    newCiutat.assignar_ruta(qa.getLocation(), qa.getTreballador());
                else if (Objects.equals(qa.getName(), CiutatAction.DESASSIGNAR_PASSATGER))
                    newCiutat.desassignar_ruta(qa.getLocation());
                else if (Objects.equals(qa.getName(), CiutatAction.MOURE_PASSATGER))
                    newCiutat.moveQueenTo(qa.getLocation());
                s = newCiutat;
            }
            // if action is not understood or is a NoOp
            // the result will be the current state.
            return s;
        }
}
