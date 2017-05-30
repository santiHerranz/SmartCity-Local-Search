package test;

import domini.Accio;
import domini.Posicio;
import domini.Recorregut;
import domini.Treballador;
import org.junit.Test;
import util.Calcul;

import java.util.ArrayList;
import java.util.List;

import static domini.Ruta.validarAccions;

/**
 * Created by santi on 30/5/17.
 */
public class PermutacioTest {


    @Test
    public void PermutacioTest() throws Exception {

        List<Treballador> passatgers = new ArrayList<>();


        //
        Treballador c = new Treballador("1", new Posicio(1,1), new Posicio(11,11));
        passatgers.add(c);
        passatgers.add(new Treballador("2", new Posicio(5,5), new Posicio(4,4)));
        passatgers.add(new Treballador("3", new Posicio(2,2), new Posicio(12,12)));
        passatgers.add(new Treballador("4", new Posicio(3,3), new Posicio(13,13)));
        passatgers.add(new Treballador("5", new Posicio(4,4), new Posicio(10,10)));
//        passatgers.add(new Treballador("6", new Posicio(11,11), new Posicio(6,6)));


        List<Accio> cc = new ArrayList<>();
        for (Treballador t: passatgers) {
            cc.add(new Accio("T", t, t.getCasa()));
            cc.add(new Accio("D", t, t.getFeina()));
        }

        List<List<Accio>> combinacions = Calcul.permutar(cc);


        List<Recorregut> result = new ArrayList<Recorregut>();


        for (List<Accio> accions : combinacions) {

            if (accions.get(0).treballador.getNom()!= "1") continue;;
            if (accions.get(accions.size()-1).treballador.getNom()!= "1") continue;;

            Recorregut recorregut = new Recorregut(accions);

            // Comprobar que les accions del recorregut siguin valides
            if (validarAccions(recorregut)) {

                if(recorregut.getDistancia()<=30)
                    result.add(recorregut);
            } else {
                //System.out.format(" * %s Error\n", recorregut);
            }
        }

        passatgers.stream().forEach(System.out::println);
        result.stream().forEach(System.out::println);


    }
}
