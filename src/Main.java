import HillClimbing.*;
import aima.core.agent.Action;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.evalfunc.HeuristicFunction;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.problem.StepCostFunction;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {

        //HillClimbingSearch();
        SimulatedAnnealingSearch();

    }
    static double ddd = 0;

    private static void HillClimbingSearch() {
        Ciutat ciutat = new Ciutat(10);
        ciutat.omplir(ciutat.getSize()*5);

        System.out.println("\nSmartCity HillClimbing  -->");
        try {
            Problem problem = new Problem(ciutat
                    , new Accions()
                    , new Resultat()
                    , new Objectiu()
                    , new StepCostFunction() {
                @Override
                public double c(Object state, Action action, Object o1) {

/*
                    Ciutat board = (Ciutat) state;
                    return board.getDistanciaEstimadaTotal();
*/
                    return  ((CiutatAction)action).getTreballador().getDistanciaFeina();

                }
            });
            HillClimbingSearch search = new HillClimbingSearch(new HeuristicFunction() {
                @Override
                public double h(Object state) {
/**/
                    Ciutat board = (Ciutat) state;
                    return board.getDistanciaEstimadaTotal();
                }
            });
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            System.out.println("Search Outcome=" + search.getOutcome());
            System.out.println("Final State=\n" + search.getLastSearchState());
            printInstrumentation(agent.getInstrumentation());

            ciutat = (Ciutat)search.getLastSearchState();

            System.out.print(  ciutat.getBoardPic());
            System.out.print(  ciutat.getRutesPic());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static void SimulatedAnnealingSearch() {
        Ciutat ciutat = new Ciutat(10);
        ciutat.omplir(20);

        System.out.println("\nSmartCity Simulated Annealing Search  -->");
        try {
            Problem problem = new Problem(ciutat
                    , new Accions()
                    , new Resultat()
                    , new Objectiu(), new StepCostFunction() {
                @Override
                public double c(Object state, Action action, Object o1) {

/*
                    Ciutat board = (Ciutat) state;
                    return board.getDistanciaEstimadaTotal();
*/
                    return  ((CiutatAction)action).getTreballador().getDistanciaFeina();

                }
            });
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(new HeuristicFunction() {
                @Override
                public double h(Object state) {
                    Ciutat board = (Ciutat) state;
                    return board.getDistanciaEstimadaTotal();
                }
            },
                    new Scheduler(2, 0.045, 50));
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            System.out.println("Search Outcome=" + search.getOutcome());
            System.out.println("Final State=\n" + search.getLastSearchState());
            printInstrumentation(agent.getInstrumentation());

            ciutat = (Ciutat)search.getLastSearchState();

            System.out.print(  ciutat.getBoardPic());
            System.out.print(  ciutat.getRutesPic());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private static void printInstrumentation(Properties properties) {
        Iterator<Object> keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List<Action> actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i).toString();
            System.out.println(action);
        }
    }
}
