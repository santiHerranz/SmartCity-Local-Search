package test;

import HillClimbing.Ciutat;
import aima.core.util.datastructure.XYLocation;
import domini.Treballador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CiutatBasicTest {

    Ciutat ciutat;

    @Before
    public void setUp() {

        ciutat = new Ciutat(8);
    }


    @Test
    public void testBasics() {
        Assert.assertEquals(0, ciutat.getNumeroPassatgerAssignats());
        ciutat.assignar_ruta(new XYLocation(0, 0), new Treballador("Prova"));
        Assert.assertEquals(1, ciutat.getNumeroPassatgerAssignats());

        ciutat.assignar_ruta(new XYLocation(0, 0), new Treballador("Prova"));
        Assert.assertEquals(1, ciutat.getNumeroPassatgerAssignats());

        ciutat.assignar_ruta(new XYLocation(1, 1), new Treballador("Prova"));
        Assert.assertEquals(2, ciutat.getNumeroPassatgerAssignats());

        System.out.print(  ciutat.getBoardPic());

        System.out.println("***");

        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(1, 1)));
        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(0, 0)));
        ciutat.moveQueen(new XYLocation(1, 1), new XYLocation(3, 3));
        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(3, 3)));
        Assert.assertTrue(!(ciutat.hiHaPassatger(new XYLocation(1, 1))));
        Assert.assertEquals(2, ciutat.getNumeroPassatgerAssignats());

        System.out.print(  ciutat.getBoardPic());

    }
}
