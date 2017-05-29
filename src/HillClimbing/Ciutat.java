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
	public static final int MAX_PASSATGERS_PER_RUTA = 10;

	int size;
	public List<Treballador> conductors = new ArrayList<>();
	public List<Treballador> treballadors = new ArrayList<>();
	Treballador[][] rutes;


	public Ciutat(int size) {
		this.size = size;
		rutes = new Treballador[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rutes[i][j] = null;
			}
		}
	}

	public int getSize() {
		return size;
	}


	public void clear() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				rutes[i][j] = null;// TODO
			}
		}
		treballadors = new ArrayList<>();
	}

	public void setRutes(Ciutat ciutat) {
		clear();
		this.treballadors = ciutat.treballadors; //new ArrayList<>(ciutat.treballadors); //
		this.conductors = ciutat.conductors;
		this.rutes = ciutat.rutes;

	}






	public void buidar(){
		treballadors = new ArrayList<>();
	}
	public void omplir(int workerNumber){
		buidar();

		Posicio home = null;
		Posicio work = null;

		int counter = 1;

		while (conductors.size() < workerNumber/2) {
			try {
				home = Posicio.novaPosicio();
				work = Posicio.novaPosicio();
			} catch (Exception e) {}

			double dist = Ciutat.calcular_distancia(home, work);

			if (dist > Ciutat.MAX_DISTANCE/2 && dist <= Ciutat.MAX_DISTANCE) {
				Treballador t = new Treballador(String.format("C%02d",  counter++), home, work);
				t.rol = Rol.CONDUCTOR;
				conductors.add(t);
			}

		}


		while (treballadors.size() < workerNumber/2) {
			try {
				home = Posicio.novaPosicio();
				work = Posicio.novaPosicio();
			} catch (Exception e) {}

			double dist = Ciutat.calcular_distancia(home, work);

			if (dist > Ciutat.MIN_DISTANCIA && dist <= Ciutat.MAX_DISTANCE) {
				Treballador t = new Treballador(String.format("P%02d",  counter++), home, work);
				treballadors.add(t);
				t.rol = Rol.PASSATGER;
/*				if(drivers<workerNumber/2) {
					t.rol = Rol.CONDUCTOR;
					drivers++;
				} else{
					t.rol = Rol.PASSATGER;
				}*/
			}

		}



	}

	public void assignar_ruta(XYLocation l, Treballador t) {

		if (t != null && !t.assignat) {
			if (!(hiHaPassatger(l))) {
				rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] = t ;
				t.assignat=true;
			}
		}
	}



	public void desassignar_ruta(XYLocation l) {
		if (rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] != null) {
			Treballador t = rutes[l.getXCoOrdinate()][l.getYCoOrdinate()];
			t.assignat=false;
			rutes[l.getXCoOrdinate()][l.getYCoOrdinate()]= null;
		}
	}

	public void intercanvi_ruta(XYLocation l) {
		for (int i = 0; i < size; i++)
			rutes[l.getXCoOrdinate()][i] = null; // TODO
		rutes[l.getXCoOrdinate()][l.getYCoOrdinate()] = null;// TODO
	}

	public void moveQueen(XYLocation from, XYLocation to) {
		if ((hiHaPassatger(from)) && (!(hiHaPassatger(to)))) {
			Treballador t = getPassatger(from.getXCoOrdinate(), from.getYCoOrdinate());
			desassignar_ruta(from);
			assignar_ruta(to,t);
		}
	}

	public boolean hiHaPassatger(XYLocation l) {
		return (hiHaPassatger(l.getXCoOrdinate(), l.getYCoOrdinate()));
	}

	private boolean hiHaPassatger(int x, int y) {
		return (rutes[x][y] != null);
	}

	public  Treballador getPassatger(XYLocation l) {
		return rutes[l.getXCoOrdinate()][l.getYCoOrdinate()];
	}

	protected Treballador getPassatger(int x, int y) {
		return rutes[x][y];
	}


	public Ruta getRuta(int row) {

		Ruta ruta = new Ruta();
		try {
			ruta.afegir_conductor(conductors.get(row));
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < this.size ; i++) {
			Treballador p = this.getPassatger(i,row);
			if (p != null) {
				ruta.afegir_passatger(p);
			}
		}
		ruta.getMillor();

		return ruta;
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
			if (t.assignat)
				result += MAX_DISTANCE - Ciutat.calcular_distancia(t.getCasa(),t.getFeina());
			else
				result += MAX_DISTANCE;
		}
		return result;
	}


	public boolean pucAssignarPassatger(Treballador t, Ruta ruta) {

		if ( ruta.passatgers.size()>=Ciutat.MAX_PASSATGERS_PER_RUTA )
			return false;

		ruta.afegir_passatger(t);
		double distancia = ruta.getMillor().getDistancia();

		return (distancia<=MAX_DISTANCE);

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

			Ruta ruta = this.getRuta(row);

			buffer.append(ruta);
			buffer.append("\n");
		}
		return buffer.toString();
	}


	public String getRutesText() {
		StringBuffer buffer = new StringBuffer();
		for (int row = 0; (row < size); row++) { // row

			Ruta ruta = this.getRuta(row);

			buffer.append(ruta.Texte());
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




	public static double calcular_distancia(Posicio p1, Posicio p2) {

		double result = 0;
		result += Math.abs(p1.x-p2.x);
		result += Math.abs(p1.y-p2.y);
		result *= 0.1;
		return result ;
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