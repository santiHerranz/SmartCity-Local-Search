package HillClimbing;

import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;
import domini.Treballador;


public class CiutatAction extends DynamicAction {
    public static final String ASSIGNAR_PASSATGER = "ASSIGNAR";
    public static final String DESASSIGNAR_PASSATGER = "DESASSIGNAR";
    public static final String MOURE_PASSATGER = "MOURE";

    public static final String ATTRIBUTE_QUEEN_LOC = "location";
    public static final String ATTRIBUTE_TREBALLADOR = "treballador";

    /**
     * Creates a queen action. Supported values of type are {@link #ASSIGNAR_PASSATGER}
     * , {@link #DESASSIGNAR_PASSATGER}, or {@link #MOURE_PASSATGER}.
     */
    public CiutatAction(String type, Treballador t, XYLocation loc) {
        super(type);
        setAttribute(ATTRIBUTE_QUEEN_LOC, loc);
        setAttribute(ATTRIBUTE_TREBALLADOR, t);
    }

    public Treballador getTreballador() { return (Treballador) getAttribute(ATTRIBUTE_TREBALLADOR); }

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
        return "CiutatAction["+ getLocation() +"=" + getTreballador().getNom() + "]";
    }

}
