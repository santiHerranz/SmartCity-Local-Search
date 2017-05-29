package HillClimbing;

import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;


public class CiutatAction extends DynamicAction {
    public static final String ASSIGNAR_PASSATGER = "ASSIGNAR_PASSATGER";
    public static final String DESASSIGNAR_PASSATGER = "DESASSIGNAR_PASSATGER";
    public static final String MOURE_PASSATGER = "MOURE_PASSATGER";

    public static final String ATTRIBUTE_QUEEN_LOC = "location";

    /**
     * Creates a queen action. Supported values of type are {@link #ASSIGNAR_PASSATGER}
     * , {@link #DESASSIGNAR_PASSATGER}, or {@link #MOURE_PASSATGER}.
     */
    public CiutatAction(String type, XYLocation loc) {
        super(type);
        setAttribute(ATTRIBUTE_QUEEN_LOC, loc);
    }

    public XYLocation getLocation() {
        return (XYLocation) getAttribute(ATTRIBUTE_QUEEN_LOC);
    }

    public int getX() {
        return getLocation().getXCoOrdinate();
    }

    public int getY() {
        return getLocation().getYCoOrdinate();
    }
}
