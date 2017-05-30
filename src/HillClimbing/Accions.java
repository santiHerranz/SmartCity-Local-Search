package HillClimbing;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.util.datastructure.XYLocation;
import domini.Ruta;
import domini.Treballador;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by santi on 28/05/2017.
 */
public class Accions implements ActionsFunction {
    @Override
        public Set<Action> actions(Object state) {
            Ciutat ciutat = (Ciutat) state;

            Set<Action> actions = new LinkedHashSet<Action>();

            int boardSize = ciutat.getSize();

            // Accions possibles:
            // Afegir un passatger a qualsevol ruta si cumpleix les restriccions


            List<Treballador> encaraPerAssignar =  ciutat.treballadors
                    .stream()
                    .filter(g -> g.assignat == false)
                    .collect(Collectors.toList());

            for (Treballador t: encaraPerAssignar ) {

                for (int row = 0; row < boardSize; row++) {
                    Ruta ruta = ciutat.getRuta(row);

                    if ((ciutat.pucAssignarPassatger(t, ruta))) {
                        int seientLliure = 0;
                        for (int col = 0; col < boardSize; col++) {
                            if (!ciutat.hiHaPassatger( new XYLocation(col, row)))
                                break;
                            seientLliure++;
                        }

                        XYLocation novaPossicio = new XYLocation( seientLliure, row);
                        actions.add(new CiutatAction(CiutatAction.ASSIGNAR_PASSATGER, t, ruta, novaPossicio));

                    }
                }
            }

/*
            // Treure un passatger de qualsevol ruta assignada
            for (int row = 0; row < boardSize; row++) {
                for (int col = 0; col < boardSize; col++) {
                    XYLocation posicio = new XYLocation(col, row);

                    int x = posicio.getXCoOrdinate();
                    int y = posicio.getYCoOrdinate();

                    if ((ciutat.hiHaPassatger(posicio))) {

                        Ruta ruta = new Ruta();
                        try {
                            ruta.afegir_conductor(new Treballador("C"+(y+1),new Posicio(0,0), new Posicio(0,0)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < ciutat.size ; i++) {

                            Treballador p = ciutat.getPassatger(i,row);
                            if (p != null) {
                                ruta.afegir_passatger(p);
                            }
                        }

                        Treballador p = ciutat.getPassatger(posicio);
                        if (p.assignat)
                            actions.add(new CiutatAction(CiutatAction.DESASSIGNAR_PASSATGER, p, ruta, posicio));
                    }
                }
            }
*/

            return actions;
        }
    }