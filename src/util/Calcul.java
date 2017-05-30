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



    public static List<List<Accio>> permute(List<Accio> accions) {
        List<List<Accio>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), accions);
        return list;
    }

    private static void backtrack(List<List<Accio>> list, List<Accio> tempList, List<Accio> accions){
        if(tempList.size() == accions.size()){
            list.add(new ArrayList<>(tempList));
        } else{
            for(int i = 0; i < accions.size(); i++){
                if(tempList.contains(accions.get(i))) continue; // element already exists, skip

                if (!valida(tempList)) continue;

                tempList.add(accions.get(i));
                backtrack(list, tempList, accions);
                tempList.remove(tempList.size() - 1);
            }
        }
    }

    private static boolean valida(List<Accio> tempList) {


        int onBoard = 0;
        for (int i = 0; i < tempList.size() ; i++) {
            Accio a = tempList.get(i);
            if (a.accio=="T")
                onBoard++;
            if (a.accio=="D")
                onBoard--;
            if (onBoard<0 || onBoard> 2)
                return false;
        }
        return true;
    }

}
