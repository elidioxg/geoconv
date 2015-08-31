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
        Log.i("aux ", String.format("%.0f",min));
        seg = (min - aux)*60;
        Log.i("seg ", String.format("%.0f",seg));
        if(seg>=Double.valueOf(60)){
            min=min+1;
            seg=seg-60.;
        }
        if(min>=Double.valueOf(60)){
            grau=grau+1;
            min=min-60.;
        }
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
        return String.format("%.5f",graus);
    }
    public String doubleToStr(double grau, double min, double seg){
        //aprimorar procedimento, formatar no padrao que vai receber no edittext
        String aux1,aux2,aux3;
        if(grau<0){grau=grau*-1;}
        if(min<0){min=min*-1;}
        if(seg<0){seg=seg*-1;}
        aux1 = String.format("%.0f",grau);
        aux2 = String.format("%.0f",min);
        aux3 = String.format("%.0f",seg);
        return (aux1+" "+aux2+ " "+aux3);
    }
}
