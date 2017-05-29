package util;


import domini.Accio;

import java.util.ArrayList;
import java.util.List;

public class Calcul {

    public static  List<List<Accio>> permutar(List<Accio> original) {
        if (original.size() == 0) {
            List<List<Accio>> result = new ArrayList<>();
            result.add(new ArrayList<Accio>());
            return result;
        }
        Accio firstElement = original.remove(0);
        List<List<Accio>> returnValue = new ArrayList<>();
        List<List<Accio>> permutations = permutar(original);
        for (List<Accio> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                List<Accio> temp = new ArrayList<>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }
}
