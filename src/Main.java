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

    private static void HillClimbingSearch() {
        Ciutat ciutat = new Ciutat(10);
        ciutat.omplir(ciutat.getSize()*5);

        System.out.println("\nSmartCity HillClimbing  -->");
        try {
            Problem problem = new Problem(ciutat
                    , new Accions()
                    , new Resultat()
                    , new Objectiu()
                    , new Cost()
            );
            HillClimbingSearch search = new HillClimbingSearch(
                    new Heuristica()
                    );
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
        Ciutat ciutat = new Ciutat(20);
        ciutat.omplir(80);

        System.out.print(ciutat.treballadors);

        System.out.println("\nSmartCity Simulated Annealing Search  -->");
        try {
            Problem problem = new Problem(ciutat
                    , new Accions()
                    , new Resultat()
                    , new Objectiu()
                    , new Cost()
            );
            SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
                    new Heuristica(),
                    new Scheduler(2, 0.045, 20)
            );
            SearchAgent agent = new SearchAgent(problem, search);

            System.out.println();
            printActions(agent.getActions());
            System.out.println("Search Outcome=" + search.getOutcome());
            System.out.println("Final State=\n" + search.getLastSearchState());
            printInstrumentation(agent.getInstrumentation());

            ciutat = (Ciutat)search.getLastSearchState();

            System.out.print(  ciutat.getBoardPic());
            System.out.print(  ciutat.getRutesPic());
            System.out.print(  ciutat.getRutesText());

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
