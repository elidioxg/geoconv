package geocodigos.geoconv.Conversion;

import android.util.Log;

public class ConversaoGMS {

    public void ConversaoGMS(){

    }

    public String converteGraus(double graus) {
        double aux, grau, min, seg;
        grau = (long) graus;
        Log.i("grau ", String.valueOf(grau));
        min =  ((graus - grau)*60);
        Log.i("min ", String.valueOf(min));
        aux = (long) min;
        Log.i("aux ", String.valueOf(min));
        seg = (min - aux)*60;
        Log.i("seg ", String.valueOf(seg));
        return doubleToStr(grau, min, seg);
    }

    public String grausConverte(double grau, double min, double seg) {
        min = min + (seg/60);
        Log.i("min ", String.valueOf(min));
        grau = grau + (min/60);
        Log.i("grau ", String.valueOf(grau));
        return doubleToStr(grau);
    }

    public String doubleToStr(double graus) {
        return String.valueOf(graus);
    }
    public String doubleToStr(double grau, double min, double seg){
        String aux1,aux2,aux3;
        aux1 = String.valueOf(grau);
        aux2 = String.valueOf(min);
        aux3 = String.valueOf(seg);
        return (aux1+" "+aux2+ " "+aux3);
    }
}
