package geocodigos.geoconv.Csv;

import java.io.StringWriter;
import java.util.ArrayList;

import geocodigos.geoconv.Models.PointModel;

/**
 * Created by elidioxg on 05/05/16.
 */
public class CsvExporter {
    private final String strId = "id";
    private final String strName = "Name";
    private final String strLat = "Latitude";
    private final String strLon = "Longitude";
    private final String strSector = "Sector";
    private final String strNorth = "North";
    private final String strEast = "East";
    private final String strDate = "Date";
    private final String strDesc = "Description";

    /**
     *
     * @param array
     * @param separator
     * @return
     */
    public static String create(ArrayList<PointModel> array, String separator){

        StringWriter writer = new StringWriter();
        try {
            writer.append(strId);
            writer.append(separator);
            writer.append(strName);
            writer.append(separator);
            writer.append(strLat);
            writer.append(separator);
            writer.append(strLon);
            writer.append(separator);
            writer.append(strNorth);
            writer.append(separator);
            writer.append(strEast);
            writer.append(separator);
            writer.append(strSector);
            writer.append(separator);
            writer.append(strDate);
            writer.append(separator);
            writer.append(strDesc);
            writer.append('\n');

            for (int i = 0; i <= array.size() - 1; i++) {
                writer.append(array.get(i).getId());
                writer.append(separator);
                writer.append(array.get(i).getName());
                writer.append(separator);
                writer.append(array.get(i).getlatitude());
                writer.append(separator);
                writer.append(array.get(i).getLongitude());
                writer.append(separator);
                writer.append(array.get(i).getNorth());
                writer.append(separator);
                writer.append(array.get(i).getEast());
                writer.append(separator);
                writer.append(array.get(i).getSector());
                writer.append(separator);
                writer.append(array.get(i).getData());
                writer.append(separator);
                writer.append(array.get(i).getDescription());
                writer.append('\n');
            }
            writer.flush();
        } catch (Exception e){
            e.printStackTrace();
        }
        return writer.toString();
    }
}
