package HillClimbing;

import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;
import domini.Ruta;
import domini.Treballador;


public class CiutatAction extends DynamicAction {
    public static final String ASSIGNAR_PASSATGER = "ASSIGNAR";
    public static final String DESASSIGNAR_PASSATGER = "DESASSIGNAR";
    public static final String MOURE_PASSATGER = "MOURE";

    public static final String ATTRIBUTE_QUEEN_LOC = "Pos";
    public static final String ATTRIBUTE_TREBALLADOR = "Treb";
    public static final String ATTRIBUTE_RUTA = "Ruta";

    /**
     * Creates a queen action. Supported values of type are {@link #ASSIGNAR_PASSATGER}
     * , {@link #DESASSIGNAR_PASSATGER}, or {@link #MOURE_PASSATGER}.
     */
    public CiutatAction(String type, Treballador t, Ruta ruta, XYLocation loc) {
        super(type);
        setAttribute(ATTRIBUTE_QUEEN_LOC, loc);
        setAttribute(ATTRIBUTE_TREBALLADOR, t);
        setAttribute(ATTRIBUTE_RUTA, ruta);
    }

    public Treballador getTreballador() { return (Treballador) getAttribute(ATTRIBUTE_TREBALLADOR); }
    public Ruta getRuta() { return (Ruta) getAttribute(ATTRIBUTE_RUTA); }

    public XYLocation getLocation() {
        return (XYLocation) getAttribute(ATTRIBUTE_QUEEN_LOC);
    }

    public int getX() {
        return getLocation().getXCoOrdinate();
    }

    public int getY() {
        return getLocation().getYCoOrdinate();
    }


    public String toString() {
        return super.toString(); // "CiutatAction["+ getLocation() +"=" + getTreballador().getNom() + "]";
    }

}
