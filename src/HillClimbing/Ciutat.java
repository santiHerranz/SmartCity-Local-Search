package HillClimbing;

import aima.core.util.datastructure.XYLocation;
import domini.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a quadratic board with a matrix of rutes on which queens can be
 * placed (only one per square) and moved.
 * 
 * @author Ravi Mohan
 * @author Ruediger Lunde
 */
public class Ciutat {


	public static final double MIN_DISTANCIA = 5;
	public static final double MAX_DISTANCE = 30;
	public static final int MAX_PASSATGERS = 2;
	public static final int MAX_PASSATGERS_PER_RUTA = 4;

	public List<Treballador> treballadors = new ArrayList<>();


	public void omplir(int workerNumber){
		buidar();

		Posicio home = null;
		Posicio work = null;

		int drivers = 0;

		while (treballadors.size() < workerNumber) {
			try {
				home = Posicio.novaPosicio();
				work = Posicio.novaPosicio();
			} catch (Exception e) {}

			double dist = Ciutat.calcular_distancia(home, work);
			if (dist > Ciutat.MIN_DISTANCIA && dist <= Ciutat.MAX_DISTANCE) {
				Treballador t = new Treballador(String.format("%02d",  treballadors.size()+1), home, work);
				treballadors.add(t);
/*				if(drivers<workerNumber/2) {
					t.rol = Rol.CONDUCTOR;
					drivers++;
				} else{
					t.rol = Rol.PASSATGER;
				}*/
			}

		}

	}
	public void buidar(){
		treballadors = new ArrayList<>();
	}


	public static double calcular_distancia(Posicio p1, Posicio p2) {

		double result = 0;
		result += Math.abs(p1.x-p2.x);
		result += Math.abs(p1.y-p2.y);
		result *= 0.1;
		return result ;
	}

	/** Parameters for initialization. */
	public enum Config {
		EMPTY, QUEENS_IN_FIRST_ROW, QUEEN_IN_EVERY_COL
	}

	/**
	 * X---> increases left to right with zero based index Y increases top to
	 * bottom with zero based index | | V
	 */
	Treballador[][] rutes;

	int size;

	/**
	 * Creates a board with <code>size</code> rows and size columns. Column and
	 * row indices start with 0.
	 */
	public Ciutat(int size) {
		omplir(size*size);
		this.size = size;
		rutes = new Treballador[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rutes[i][j] = null; // TODO sense acció
			}
		}
	}

	/**
	 * Creates a board with <code>size</code> rows and size columns. Column and
	 * row indices start with 0.
	 * 
	 * @param config
	 *            Controls whether the board is initially empty or contains some
	 *            queens.
	 */
	public Ciutat(int size, Config config) {
		this(size);
		if (config == Config.QUEENS_IN_FIRST_ROW) {
			for (int i = 0; i < size; i++)
				assignar_ruta(new XYLocation(i, 0));
		} else if (config == Config.QUEEN_IN_EVERY_COL) {
			Random r = new Random();
			for (int i = 0; i < size; i++)
				assignar_ruta(new XYLocation(i, r.nextInt(size)));
		}
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rutes[i][j] = null;// TODO
			}
		}
		treballadors = new ArrayList<>();
	}

	public void copiarCiutat(Ciutat ciutat) {
		clear();
		treballadors = ciutat.treballadors; //new ArrayList<>(ciutat.treballadors);
		for (int i = 0; i < ciutat.getRutesPassatgers().size(); i++) {
			XYLocation pos = ciutat.getRutesPassatgers().get(i);
			Treballador t = ciutat.getPassatger(pos.getXCoOrdinate(), pos.getYCoOrdinate());
			assignar_ruta(pos, t);
		}
	}


	public int getSize() {
		return size;
	}

	/** Column and row indices start with 0! */
	public void assignar_ruta(XYLocation l) {

		Treballador t = treballadors
				.stream()
				.filter(a -> a.assignat==false)
				.findFirst().orElse(null);

		if (t != null) {
			if (!(hiHaPassatger(l))) {
				rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] = t ;
				t.assignat=true;
			}
		}
	}

	public void assignar_ruta(XYLocation l, Treballador t) {

		if (t != null) {
			if (!(hiHaPassatger(l))) {
				rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] = t ;
				t.assignat=true;
			}
		}
	}



	public void desassignar_ruta(XYLocation l) {
		if (rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] != null) {
			Treballador t = rutes[l.getXCoOrdinate()][l.getYCoOrdinate()];
			rutes[l.getXCoOrdinate()][l.getYCoOrdinate()]= null;
			t.assignat=false;
		}
	}

	/**
	 * Moves the queen in the specified column (x-value of <code>l</code>) to
	 * the specified row (y-value of <code>l</code>). The action assumes a
	 * complete-state formulation of the n-queens problem.
	 * 
	 * @param l
	 */
	public void moveQueenTo(XYLocation l) {
		for (int i = 0; i < size; i++)
			rutes[l.getXCoOrdinate()][i] = null; // TODO
		rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] = null;// TODO
	}

	public void moveQueen(XYLocation from, XYLocation to) {
		if ((hiHaPassatger(from)) && (!(hiHaPassatger(to)))) {
			desassignar_ruta(from);
			assignar_ruta(to);
		}
	}

	public boolean hiHaPassatger(XYLocation l) {
		return (hiHaPassatger(l.getXCoOrdinate(), l.getYCoOrdinate()));
	}

	protected boolean hiHaPassatger(int x, int y) {
		return (rutes[x][y] != null);
	}

	public int getNumeroPassatgerAssignats() {
		int count = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (rutes[i][j] != null)
					count++;
			}
		}
		return count;
	}

	public List<XYLocation> getRutesPassatgers() {
		ArrayList<XYLocation> result = new ArrayList<XYLocation>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (hiHaPassatger(i, j))
					result.add(new XYLocation(i, j));
			}
		}
		return result;

	}

	public double getDistanciaEstimadaTotal() {
		double result = 0;
		for (Treballador t: treballadors ) {
			if (t.assignat==true)
				result += MAX_DISTANCE - 1; //Ciutat.calcular_distancia(t.getCasa(),t.getFeina());
			else
				result += MAX_DISTANCE;
		}
		return result;
	}


	public boolean pucAssignarPassatger(XYLocation l) {

		int x = l.getXCoOrdinate();
		int y = l.getYCoOrdinate();

		Ruta ruta = new Ruta();
		try {
			ruta.afegir_conductor(new Treballador("C",new Posicio(size*1/3,size*1/3), new Posicio(size*2/3,size*2/3)));
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < this.size ; i++) {
			Treballador t = getPassatger(i,y);
			if (t != null) {
				ruta.afegir_passatger(t);
			}
		}

		double distancia = ruta.getMillor().getDistancia();
		return (distancia<=MAX_DISTANCE);

	}

	private Treballador getPassatger(int x, int y) {
		return rutes[x][y];
	}


	@Override
	public int hashCode() {
		List<XYLocation> locs = getRutesPassatgers();
		int result = 17;
		for (XYLocation loc : locs) {
			result = 37 * loc.hashCode();
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (this.getClass() != o.getClass()))
			return false;
		Ciutat aBoard = (Ciutat) o;
		boolean retVal = true;
		List<XYLocation> locs = getRutesPassatgers();

		for (XYLocation loc : locs) {
			if (!(aBoard.hiHaPassatger(loc)))
				retVal = false;
		}
		return retVal;
	}

	public void print() {
		System.out.println(getBoardPic());
	}

	public String getBoardPic() {
		StringBuffer buffer = new StringBuffer();
		for (int row = 0; (row < size); row++) { // row
			for (int col = 0; (col < size); col++) { // col
				if (hiHaPassatger(col, row))
					buffer.append(" P ");
				else
					buffer.append(" - ");
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}


	public String getRutesPic() {
		StringBuffer buffer = new StringBuffer();
		for (int row = 0; (row < size); row++) { // row

			Ruta ruta = new Ruta();
			try {
				ruta.afegir_conductor(new Treballador("C",new Posicio(size*1/3,size*1/3), new Posicio(size*2/3,size*2/3)));
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int i = 0; i < this.size ; i++) {
				Treballador t = getPassatger(i,row);
				if (t != null) {
					ruta.afegir_passatger(t);
				}
			}
			ruta.getMillor();

			buffer.append(ruta);
			buffer.append("\n");
		}
		return buffer.toString();
	}




	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (int row = 0; row < size; row++) { // rows
			for (int col = 0; col < size; col++) { // columns
				if (hiHaPassatger(col, row))
					buf.append('P');
				else
					buf.append('-');
			}
			buf.append("\n");
		}
		return buf.toString();
	}


	public static double calcula_distancia(List<Accio> recorregut) {
		double d = 0.0;
		Posicio p1 = recorregut.get(0).lloc;
		for (Accio p: recorregut ) {
			d += calcular_distancia(p1, p.lloc);
			p1 = p.lloc;
		}
		return d;
	}


	public static double calcular_distancia_ruta(List<Accio> passos) {
		double d = 0.0;
		Accio p1 = passos.get(0);
		for (Accio p: passos ) {
			d += Ciutat.calcular_distancia(p1.lloc, p.lloc);
			p1 = p;
		}
		return d;
	}



}