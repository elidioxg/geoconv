package geocodigos.geoconv.Conversion;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import geocodigos.geoconv.R;

public class ConversaoGMS {
//to do: colocar os procedimentos de validação aqui
    public void ConversaoGMS(){

    }

    public String converteGraus(double graus) {
        double aux, aux2, grau, min, seg;
        grau = (long) graus;
        aux =  ((graus - grau)*60);
        min = (long) aux;
        aux2 =  (aux - min)*60;
        seg = (long) aux2;
        if(seg>=60){
            min=min+1;
            seg=seg-60.;
        }
        if(min>=60){
            grau=grau+1;
            min=min-60.;
        }
        return doubleToStr(grau, min, seg);
    }

    public String grausConverte(boolean positivo, double grau, double min, double seg) {
        min = min + (seg/60);
        grau = grau + (min/60);
        if(!positivo) {
            grau = grau*-1;
        }
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
    public ArrayList<String> conversaoGraus(double lat, double lon) {
        ArrayList<String> arrayList = new ArrayList<String>();
        ConversaoGMS cg = new ConversaoGMS();
        String strLat = cg.converteGraus(lat);
        String strLon = cg.converteGraus(lon);
        String coordLat[] = strLat.split(" ");
        String coordLon[] = strLon.split(" ");
        arrayList.add(coordLat[0]);
        arrayList.add(coordLat[1]);
        arrayList.add(coordLat[2]);
        arrayList.add(coordLon[0]);
        arrayList.add(coordLon[1]);
        arrayList.add(coordLon[2]);
        return arrayList;
    }

    public int validacao(double latitude, double longitude){
        if (latitude <= -90.0 || latitude >= 90.0) {
            return 1;
        }
        if(longitude <= -180.0 || longitude >= 180.0)
        {
            return 2;
        }
        return 0;
    }

    public int validacao (String setor, String norte, String leste){

        if(setor.length()==4){

        }
        if (setor.length() <4 || setor.length()>4) {
            return 1;
        }
        if(Double.parseDouble(norte) <0 || Double.parseDouble(norte) >10000000){
            return 2;
         }                              //0                                  //1000000
        if(Double.parseDouble(leste) <160000 || Double.parseDouble(leste) >834000){//834000
            return 3;
        }
        return 0;
    }

    public int validacao(String latgrau, String latmin, String latseg,
                         String longrau, String lonmin, String lonseg ){
        if(Double.parseDouble(latgrau) >90 || Double.parseDouble(latgrau) <-90){
            return 1;
        }
        if (Double.parseDouble(longrau) > 180 || Double.parseDouble(longrau) < -180) {
            return 2;
        }
        if (Double.parseDouble(latmin) >= 60 || Double.parseDouble(latmin) < 0) {
            return 3;
        }
        if (Double.parseDouble(lonmin) >= 60 || Double.parseDouble(lonmin) < 0) {
            return 4;
        }
        if (Double.parseDouble(latseg) >= 60 || Double.parseDouble(latseg) < 0) {
            return 5;
        }
        if (Double.parseDouble(lonseg) >= 60 || Double.parseDouble(lonseg) < 0) {
            return 6;
        }
        return 0;
    }
}
