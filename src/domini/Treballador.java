package domini;


import HillClimbing.Ciutat;

import static domini.Rol.TREBALLADOR;

public class Treballador {

    private String nom;
    private Posicio casa;
    private Posicio feina;

    public Rol rol = TREBALLADOR;
    public boolean assignat;

    public double getDistanciaFeina() {
        return Ciutat.calcular_distancia(this.getCasa(), this.getFeina());
    }

    public Treballador(String nom){
        this.nom = nom;
    }

    public Treballador(String nom, Posicio casa, Posicio feina){
        this(nom);
        this.casa = casa;
        this.feina = feina;
    }

    public String getNom() { return nom; }
    public Posicio getCasa() {
        return casa;
    }
    public Posicio getFeina() {return feina;}

    @Override
    public String toString() {
        return String.format("%s (%.1f km)", nom, getDistanciaFeina() );
    }

    public String Describe() {
        return String.format("%s (%s, %s)->(%s, %s) %.1f km", nom, casa.x,casa.y, feina.x, feina.y, getDistanciaFeina() );
    }

}
