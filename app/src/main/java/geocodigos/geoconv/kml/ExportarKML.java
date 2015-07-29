package geocodigos.geoconv.kml;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;

import geocodigos.geoconv.Database.DatabaseHelper;
import geocodigos.geoconv.model.PointModel;

public class ExportarKML extends Activity implements XmlSerializer {

    private OutputStream outputStream;
    private String s;
    private Writer writer;
    DatabaseHelper database;
    private ArrayList<PointModel> campos;

    public ArrayList<PointModel> pontos() {
        ArrayList<PointModel> campos = new ArrayList<PointModel>();

        database = new DatabaseHelper(getApplicationContext());
        database.getWritableDatabase();
        campos.clear();
        campos = database.pegarPontos();
        Log.i("campos:", campos.toString());
        database.close();
        return campos;
    }

    public String criarCamada(int tipo) throws
            IllegalArgumentException, IllegalStateException, IOException {
        campos = pontos();
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        xmlSerializer.setOutput(writer);

        xmlSerializer.startDocument("UTF-8", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        xmlSerializer.startTag("", "kml").attribute("", "xmlns",
                "http://www.opengis.net/kml/2.2");
        xmlSerializer.startTag("", "Document");
            switch (tipo) {
                case 0:
                    for(int i=0; i<campos.size(); i++) {
                        if(Integer.parseInt(campos.get(i).getSelecao().trim())==1) {
                            xmlSerializer.startTag("", "Placemark");
                            xmlSerializer.startTag("", "name");
                            xmlSerializer.text(campos.get(i).getRegistro());
                            xmlSerializer.endTag("", "name");
                            xmlSerializer.startTag("", "description");
                            xmlSerializer.text(campos.get(i).getDescricao());
                            xmlSerializer.endTag("", "description");
                            xmlSerializer.startTag("", "Point");
                            xmlSerializer.startTag("", "coordinates");
                            xmlSerializer.text(campos.get(i).getlatitude() + " , " +
                                    campos.get(i).getLongitude() + ", " + campos.get(i).getAltitude());
                            xmlSerializer.endTag("", "coordinates");
                            xmlSerializer.endTag("", "Point");
                            xmlSerializer.endTag("", "Placemark");
                        }
                    }
                    break;
                case 1:
                    xmlSerializer.startTag("", "Placemark");
                    xmlSerializer.startTag("","LineString");
                    xmlSerializer.startTag("","Coordinates");
                    for(int i=0; i<campos.size();i++){
                        if(Integer.parseInt(campos.get(i).getSelecao().trim())==1) {
                            xmlSerializer.text(campos.get(i).getlatitude() + "," +
                                    campos.get(i).getLongitude() + "," + campos.get(i).getAltitude());
                        }
                    }
                    xmlSerializer.endTag("","Coordinates");
                    xmlSerializer.endTag("","LineString");
                    xmlSerializer.endTag("","Placemark");
                    break;
                case 2:
                    xmlSerializer.startTag("", "name");
                    xmlSerializer.text("");//pegar nome do mapa
                    xmlSerializer.endTag("", "name");
                    xmlSerializer.startTag("", "Polygon");
                    xmlSerializer.startTag("", "extrude");
                    xmlSerializer.text("1");
                    xmlSerializer.endTag("", "extrude");
                    xmlSerializer.startTag("", "LinearRing");
                    xmlSerializer.startTag("", "coordinates");
                    for(int i=0; i<campos.size(); i++){
                        if(Integer.parseInt(campos.get(i).getSelecao().trim())==1) {
                            xmlSerializer.text(campos.get(i).getlatitude() + "," +
                                    campos.get(i).getLongitude() + "," + campos.get(i).getAltitude());
                        }
                    }
                    xmlSerializer.endTag("","coordinates");
                    xmlSerializer.endTag("", "LinearRing");

                    xmlSerializer.endTag("", "Polygon");
                    break;
                default:
                    break;

        }
        xmlSerializer.endTag("", "Document");
        xmlSerializer.endTag("", "kml");
        xmlSerializer.endDocument();
        Log.i("arquivo KML", writer.toString());
        return writer.toString();
    }

    private void escreverArquivo(String arqKml){
        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("nome.kml",
                    Context.MODE_PRIVATE));
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
/*
    public static String CreateXMLString() throws
            IllegalArgumentException, IllegalStateException, IOException {

        //ArrayList<PointModel> teste = new ArrayList<PointModel>();
        //teste = pontos();

        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);

        xmlSerializer.startDocument("UTF-8", true);
        //xmlSerializer.setFeature("http://www.opengis.net/kml/2.2", true);
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        xmlSerializer.startTag("", "kml").attribute("", "xmlns",
                "http://www.opengis.net/kml/2.2");
        //xmlSerializer.sesetFeature("xmlns= http://www.opengis.net/kml/2.2", false);

        xmlSerializer.startTag("", "Placemark");

        //xmlSerializer tempXml = Xml.newSerializer();
        //StringWriter tempWriter = new StringWriter();

        xmlSerializer.startTag("", "name");
        xmlSerializer.text("Ponto 1");
        xmlSerializer.endTag("", "name");
        //xmlSerializer.attribute("","ID", "01");
        //xmlSerializer.attribute(namespace, name, value)

        xmlSerializer.startTag("", "description");
        xmlSerializer.text("descrição");
        xmlSerializer.endTag("", "description");

        xmlSerializer.startTag("", "Point");

        xmlSerializer.startTag("", "coordinates");
        xmlSerializer.text("122.08 , 37.42222, 0");
        xmlSerializer.endTag("", "coordinates");

        xmlSerializer.endTag("","Point");

        xmlSerializer.endTag("", "Placemark");

        xmlSerializer.endTag("", "kml");

        xmlSerializer.endDocument();

        Log.i("arquivo KML", writer.toString());
        return writer.toString();

    }
*/
    @Override
    public void setFeature(String name, boolean state)
            throws IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getFeature(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setProperty(String name, Object value)
            throws IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getProperty(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOutput(OutputStream outputStream, String s) throws IOException, IllegalArgumentException, IllegalStateException {

        this.outputStream = outputStream;
        this.s = s;
    }

    @Override
    public void setOutput(Writer writer) throws IOException, IllegalArgumentException, IllegalStateException {

        this.writer = writer;
    }

    @Override
    public void startDocument(String encoding, Boolean standalone)
            throws IOException, IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void endDocument() throws IOException, IllegalArgumentException,
            IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPrefix(String prefix, String namespace) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public String getPrefix(String namespace, boolean generatePrefix)
            throws IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getDepth() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlSerializer startTag(String namespace, String name)
            throws IOException, IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlSerializer attribute(String namespace, String name, String value)
            throws IOException, IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlSerializer endTag(String namespace, String name)
            throws IOException, IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlSerializer text(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public XmlSerializer text(char[] buf, int start, int len)
            throws IOException, IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void cdsect(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void entityRef(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void processingInstruction(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void comment(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void docdecl(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void ignorableWhitespace(String text) throws IOException,
            IllegalArgumentException, IllegalStateException {
        // TODO Auto-generated method stub

    }

    @Override
    public void flush() throws IOException {
        // TODO Auto-generated method stub

    }

}