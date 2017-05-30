package domini;

import HillClimbing.Ciutat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class Recorregut {

    int seientsBuits = Ciutat.MAX_PASSATGERS;
    int maxPassatgers = Ciutat.MAX_PASSATGERS;
    double maxDistancia = Ciutat.MAX_DISTANCE;

    public Treballador conductor;
    public List<Treballador> passatgers = new ArrayList<>();
    public List<Accio> passos = new ArrayList<>();

    public Recorregut(int maxPassatgers, double maxDistancia) {
        passatgers = new ArrayList<Treballador>();
        this.maxPassatgers = maxPassatgers;
        this.seientsBuits = maxPassatgers;
        this.maxDistancia = maxDistancia;
    }

    public Recorregut() {

    }

    public Recorregut(List<Accio> passos) {
       this.passos = new ArrayList<>(passos);
    }


    public void take(Treballador t) throws Exception {
        if (seientsBuits==0)
            throw new Exception("Cotxe ple");
        passatgers.add(t);
        seientsBuits--;
    }


    public void drop(Treballador t) throws Exception {
        for (Iterator<Treballador> iterator = passatgers.iterator(); iterator.hasNext();) {
            Treballador o = iterator.next();
            if (o.equals(t)) {
                iterator.remove();
                seientsBuits++;
                return;
            }
        }
        throw new Exception("Treballador "+ t.getNom() +" no trobat");
    }


    public double getDistancia() {
        return Ciutat.calcula_distancia(this.passos);
    }

    @Override
    public String toString() {
        return Arrays.toString(passos.toArray());
    }

    public String Describe() {
        return String.format("%s %.1f km", this, getDistancia() );
    }

    public List<Posicio> getParades() {

        List<Posicio> resultat = new ArrayList<>();
        for (Accio a: passos ) {
            resultat.add(new Posicio(a.lloc.x,a.lloc.y));
        }
        return resultat;
    }
}
