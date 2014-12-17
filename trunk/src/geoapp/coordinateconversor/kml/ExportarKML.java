package geoapp.coordinateconversor.kml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class ExportarKML implements XmlSerializer {

	public static String CreateXMLString() throws 
	IllegalArgumentException, IllegalStateException, IOException {
		
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
	public void setOutput(OutputStream os, String encoding) throws IOException,
			IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOutput(Writer writer) throws IOException,
			IllegalArgumentException, IllegalStateException {
		// TODO Auto-generated method stub
		
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
