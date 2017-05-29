package domini;

/**
 * Created by santi on 17/05/2017.
 */
public class Accio {
    Posicio lloc = null;
    public String accio;
    public Treballador treballador;


    public Accio(String accio, Treballador treballador, Posicio lloc) {
        this.accio = accio;
        this.treballador = treballador;
        this.lloc = lloc;
    }

    @Override
    public String toString() {
        return accio + treballador.getNom();
    }



}
