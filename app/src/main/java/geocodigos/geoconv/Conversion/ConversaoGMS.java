package geocodigos.geoconv.Conversion;

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
}
