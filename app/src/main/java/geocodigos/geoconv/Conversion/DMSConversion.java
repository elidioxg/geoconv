package geocodigos.geoconv.Conversion;

import java.util.ArrayList;

public class DMSConversion {
    private final String formatPrecision = "%.5f";
    public void DMSConversion(){

    }

    /**
     * Convert Decimal Degrees to Degrees, Minutes and Seconds
     * @param degrees
     * @return
     */
    public String convertFromDegrees(double degrees) {
        double aux, aux2, deg, min, sec;
        deg = (long) degrees;
        aux =  ((degrees - deg)*60);
        min = (long) aux;
        aux2 =  (aux - min)*60;
        sec = (long) aux2;
        if(sec>=60){
            min=min+1;
            sec=sec-60.;
        }
        if(min>=60){
            deg=deg+1;
            min=min-60.;
        }
        String str1,str2,str3;
        if(deg<0){deg*=-1;}
        if(min<0){min*=-1;}
        if(sec<0){sec*=-1;}
        str1 = String.format("%.0f",deg);
        str2 = String.format("%.0f",min);
        str3 = String.format("%.0f",sec);
        return (str1+" "+str2+ " "+str3);
    }

    /**
     *
     * @param positive If North or East then is positive
     * @param degrees
     * @param min
     * @param seg
     * @return Decimal Degrees Coordinates
     */
    public String convertToDegrees(boolean positive, String degrees, String min, String seg) {
        double deg = Double.parseDouble(degrees);
        double minutes = Double.parseDouble(min);
        double seconds = Double.parseDouble(seg);
        minutes = minutes + (seconds/60);
        deg = deg + (minutes/60);
        if(!positive) {
            deg *=-1;
        }
        String result = String.format(formatPrecision, deg);
        result = result.replace(",", ".");
        return result;
    }

    /**
     *
     * @param positive If North or East then its Positive
     * @param degrees
     * @param min
     * @param seg
     * @return Cordinates in Decimal Degrees
     */
    public String convertToDegrees(boolean positive, double degrees, double min, double seg) {
        min = min + (seg/60);
        degrees = degrees + (min/60);
        if(!positive) {
            degrees *=-1;
        }
        return doubleToStr(degrees);
    }

    /**
     * Convert Lat/Lon to Degrees, Minutes and Seconds
     * @param lat
     * @param lon
     * @return
     */
    public ArrayList<String> DegreesConversion(double lat, double lon) {
        ArrayList<String> arrayList = new ArrayList<>();
        String strLat = convertFromDegrees(lat);
        String strLon = convertFromDegrees(lon);
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

}