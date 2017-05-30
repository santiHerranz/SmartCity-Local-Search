package domini;

import java.util.Random;

/**
 * Created by santi on 3/5/17.
 */
public class Posicio {

        public int x;
        public int y;

        public static Posicio novaPosicio() {

            Random random = new Random();
            int x = random.nextInt(100);
            int y = random.nextInt(100);

            try {
                return new Posicio(x,y);
            } catch (Exception e) {}

            return null;
        }

        public Posicio(int x, int y){ // throws Exception
/*            if (!valid(x,y))
                throw new Exception("Error coordenada");*/
            this.x = x; this.y= y;
        }

        // TODO The city has 10 x 10 Km and streets form a chess where every block is a square of 100 x 100 m

        boolean valid(int x, int y){
            if(x<0) return false;
            if(x>100) return false;
            if(y<0) return false;
            if(y>100) return false;
            return true;
        }

    @Override
    public boolean equals(Object obj) {
        return x == ((Posicio)obj).x && y == ((Posicio)obj).y;
    }

    @Override
    public String toString() {
        String result = "";
        result += x ;
        result += "\t"+ y;
        return ""+ result +"";
    }


    public boolean comprovarRang(int initX, int endX, int initY, int endY) {
        if ((initX <= x && x <= endX) && (initY <= y && y <= endY)) return true;
        return false;
    }
}

