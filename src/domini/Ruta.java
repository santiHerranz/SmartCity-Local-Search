package domini;

import HillClimbing.Ciutat;
import aima.core.agent.impl.DynamicAction;
import util.Calcul;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Una rutaActual te un conductor i 0 o mes passatgers
 * es pot:
 * + afegir conductor
 * + afegir passatger
 * + treure passatger
 */
public class Ruta {



    public Treballador conductor;
    public List<Treballador> passatgers = new ArrayList<>();

    private Recorregut millor;
    public Recorregut getMillor() {
        if (millor == null)
            millor = millor_recorregut();
        return millor;
    }


    // Representacio del recorregut
    private String[] state;
    private String description = "";

    public static final aima.core.agent.Action ASSIGNAR_PASSATGER_RUTA = new DynamicAction("ASSIGNAR_PASSATGER_RUTA") ;
    public static final aima.core.agent.Action DESASSIGNAR_PASSATGER_RUTA = new DynamicAction("DESASSIGNAR_PASSATGER_RUTA");


    public Ruta() {
        passatgers = new ArrayList<Treballador>();
    }


    public void afegir_conductor(Treballador w){
        conductor = w;
        w.assignat = true;
        millor = null;
    }

    public void afegir_passatger(Treballador w) {
        passatgers.add(w);
        millor = null;
    }

    public void treure_passatger(Treballador t) throws Exception {
        for (Iterator<Treballador> iterator = passatgers.iterator(); iterator.hasNext();) {
            Treballador o = iterator.next();
            if (o.equals(t)) {
                iterator.remove();
                millor = null;
                return;
            }
        }
        throw new Exception("Treballador "+ t.getNom() +" no trobat");
    }


    public void addState(String value) {

        description += value;

        String[] newState = new String[state.length+1];
        System.arraycopy(newState, 0, this.state, 0, state.length);
        newState[state.length] = value;
        state = newState;
    }

    public String getState(){
        return this.description;
    }



    public String getDescription(){
        return this.description;
    }


    private String TAG_TAKE = "T";
    private String TAG_DROP = "D";
    private String TAG_SHIFT = "S";
    private String TAG_END = ";";



    @Override
    public String toString() {
        String s = "";
        if (millor!= null) {
            s += String.format("%s", millor.passos);
        }
        return s;
    }


    public String Texte() {
        String s = "";
        if ( conductor != null)
            s += "["+ conductor.getNom() +" (";
        for (Treballador p:passatgers ) {
            s += " "+ p.getNom() +" ";
        }
        s += ")";
        if (millor!= null) {
            s += String.format(" Total= %.1f km", Ciutat.calcular_distancia_ruta(millor.passos));
        }

        return s;
    }


    public boolean puc_agafar_Passatger_a_la_ruta() {
        boolean result = true;
        if (millor!=null)
            result = millor.getDistancia()<Ciutat.MAX_DISTANCE;
        return result;
    }

    public boolean puc_treure_Passatger_de_la_ruta() {
        boolean result = passatgers.size()>0;
        return result;
    }

    public Treballador getUltimPassatger() {
        return this.passatgers.get(this.passatgers.size()-1);
    }

    public void calular_millor_recorregut() {
        millor = millor_recorregut();
        System.out.println(millor.getDistancia());
    }










    public Recorregut millor_recorregut() {

        Recorregut millor = null;

        if (this.passatgers.size() == 0) {  // Sense pasatgers

            Recorregut recorregut = new Recorregut();
            // Desplaçaments del conductor per calcular la distancia del recorregut
            recorregut.passos.add(0, new Accio("", conductor, conductor.getCasa()));
            recorregut.passos.add(recorregut.passos.size(), new Accio("E", conductor, conductor.getFeina()));
            millor = recorregut;

        } else {    // Calcular el millor recorregut permutant els passetgers
            double min = Double.MAX_VALUE;

            for (Recorregut recorregut : obtenirTotsRecorregutsValids(this.passatgers)) {

                // afegim els desplaçaments del conductor per calcular la distancia final del recorregut
                recorregut.passos.add(0, new Accio("", conductor, conductor.getCasa()));
                recorregut.passos.add(recorregut.passos.size(), new Accio("", conductor, conductor.getFeina()));

                // calculem la distancia completa
                double distancia =  recorregut.getDistancia();

                // si distancia es minim
                if (distancia<min) {
                    min = distancia;
                    millor = recorregut;
                }
            }
        }
        return millor;
    }

    private List<Recorregut> obtenirTotsRecorregutsValids(List<Treballador> passatgers) {
        List<Recorregut> result = new ArrayList<Recorregut>();

//        List<List<Accio>> combinacions = getCombinacioAccionsPermutant(passatgers);
        List<List<Accio>> combinacions = getCombinacioAccionsBackTracking(passatgers);

        for (List<Accio> accions : combinacions) {
            Recorregut recorregut = new Recorregut(accions);

            // Comprobar que les accions del recorregut siguin valides
            if (validarAccions(recorregut)) {
                result.add(recorregut);
            } else {
                //System.out.format(" * %s Error\n", recorregut);
            }
        }
        return result;
    }


    private List<List<Accio>> getCombinacioAccionsPermutant(List<Treballador> passatgers) {

        List<Accio> accions = new ArrayList<>();
        for (Treballador t: passatgers) {
            accions.add(new Accio("T", t, t.getCasa()));
            accions.add(new Accio("D", t, t.getFeina()));
        }

        List<List<Accio>> result = Calcul.permutar(new ArrayList<>(accions));

        //System.out.format("accions:%d -> valides:%d\n", accions.size(), result.size());
        return result;
    }

    public static boolean validarAccions(Recorregut r) {

        try{
            for (Accio a: r.passos ) {
                switch (a.accio.charAt(0))         {
                    case 'T':
                        r.take(a.treballador);
                        break;
                    case 'D':
                        r.drop(a.treballador);
                        break;
                }
            }
            return true;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
        }
        return false;

    }

    private static  List<List<Accio>> getCombinacioAccionsBackTracking(List<Treballador> passatgers) {

        List<List<Accio>> combinacions = new ArrayList<>();
        List<Accio> temp = new ArrayList<>();
        List<Treballador> temp2 = new ArrayList<>();
        backtraking(0, passatgers, temp, temp2, combinacions);
        return combinacions;
    }


    public static boolean backtraking(
            int k
            , List<Treballador> list
            , List<Accio> accions
            , List<Treballador> onboard
            , List<List<Accio>> combinacions
    ) {

        boolean result = false;
        if (k < list.size()) {
            for (int i = 0; i < list.size(); i++) {
                Treballador w = list.get(i);
                if (!onboard.contains(w)) {
                    onboard.add(w);
                    Accio t = new Accio("T", w, w.getCasa());
                    accions.add(t);
                    Accio d = new Accio("D", w, w.getFeina());
                    accions.add(d);
                    if (accions.size() == list.size()*2) {
                        combinacions.add(new ArrayList<>(accions));
                        //System.out.println(accions);

                        int from = 1, to = accions.size();
                        combinacions.addAll(afegir_drops_retardats(accions, from, to));
                    }
                    result = backtraking(k + 1, list, accions, onboard, combinacions);
                    accions.remove(t);
                    accions.remove(d);
                    onboard.remove(w);
                }
            }
        }
        return result;
    }

    private static List<List<Accio>> afegir_drops_retardats(List<Accio> accions, int from, int to) {
        List<List<Accio>> combinacions = new ArrayList<>();
        List<Accio> copia = new ArrayList<>(accions);
        for (int j = from; j < to-1 ; j++) {
            Collections.swap(copia,j,j+1);
            combinacions.add(new ArrayList<>(copia));
            //System.out.println(copia);
        }
        return  combinacions;
    }















}
