package test;

import HillClimbing.Ciutat;
import aima.core.util.datastructure.XYLocation;
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
        ciutat.afegir_passatgerARuta(new XYLocation(0, 0));
        Assert.assertEquals(1, ciutat.getNumeroPassatgerAssignats());

        ciutat.afegir_passatgerARuta(new XYLocation(0, 0));
        Assert.assertEquals(1, ciutat.getNumeroPassatgerAssignats());

        ciutat.afegir_passatgerARuta(new XYLocation(1, 1));
        Assert.assertEquals(2, ciutat.getNumeroPassatgerAssignats());

        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(1, 1)));
        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(0, 0)));
        ciutat.moveQueen(new XYLocation(1, 1), new XYLocation(3, 3));
        Assert.assertTrue(ciutat.hiHaPassatger(new XYLocation(3, 3)));
        Assert.assertTrue(!(ciutat.hiHaPassatger(new XYLocation(1, 1))));
        Assert.assertEquals(2, ciutat.getNumeroPassatgerAssignats());

        System.out.print(  ciutat.getBoardPic());

    }
}
