package geocodigos.geoconv.implementation;

import java.util.Calendar;

/**
 * Created by root on 06/01/15.
 */
public class getDate {

    public String returnDate() {
        Calendar c = Calendar.getInstance();
        int iAno = c.get(Calendar.YEAR);
        int iMes = c.get(Calendar.MONTH);
        int iDia = c.get(Calendar.DAY_OF_MONTH);
        String sqlData =
                String.valueOf(iAno)+"-"+String.valueOf(iMes)+
                        "-"+String.valueOf(iDia);
        return sqlData;
    }
}