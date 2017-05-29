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
            if (a instanceof CiutatAction) {
                CiutatAction qa = (CiutatAction) a;
                Ciutat board = (Ciutat) s;
                Ciutat newBoard = new Ciutat(board.getSize());
                newBoard.setBoard(board.getRutesPassatgers());
                if (Objects.equals(qa.getName(), CiutatAction.ASSIGNAR_PASSATGER))
                    newBoard.afegir_passatgerARuta(qa.getLocation());
                else if (Objects.equals(qa.getName(), CiutatAction.DESASSIGNAR_PASSATGER))
                    newBoard.removeQueenFrom(qa.getLocation());
                else if (Objects.equals(qa.getName(), CiutatAction.MOURE_PASSATGER))
                    newBoard.moveQueenTo(qa.getLocation());
                s = newBoard;
            }
            // if action is not understood or is a NoOp
            // the result will be the current state.
            return s;
        }
}
