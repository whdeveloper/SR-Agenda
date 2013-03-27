package com.whd.sragenda.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.util.Xml;
import com.whd.android.adapters.AgendaSQLAdapter;
import com.whd.sragenda.Constants;
import com.whd.sragenda.dataholders.Wedstrijd;
import com.whd.sragenda.dataholders.Wedstrijden;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUpdate extends AsyncTask<Void, Void, Void> {
	
	private Context c;
	private UpdateXMLParser updater;
	private URL url;
	private Wedstrijden xmlwedstrijden;
	private Wedstrijd xmlwedstrijd;
	private AgendaSQLAdapter sql;

	public GetUpdate(Context context) {
		c = context;
	}

	protected Void doInBackground(Void... params) {
		try {
			updater = new UpdateXMLParser();
			url = new URL(Constants.UPDATEURL);
			xmlwedstrijden = updater.parse(url.openStream());
	      
			sql = new AgendaSQLAdapter(c);
        	sql.openToWrite();
			sql.deleteAll();
			for (int i = 0; i < xmlwedstrijden.size(); i++) {
	        	xmlwedstrijd = ((Wedstrijd)xmlwedstrijden.get(i));
	        	sql.insert(xmlwedstrijd.getLocatie(), xmlwedstrijd.getDatum(), xmlwedstrijd.getTijd(), xmlwedstrijd.getAfstand());
	        }
			return null;
	    } catch (MalformedURLException localMalformedURLException) {
	    	Log.e("Roosters", "<--====-->  Error: MURLE " + localMalformedURLException.getMessage());
	    	localMalformedURLException.printStackTrace();
	    	return null;
	    } catch (IOException localIOException) {
	    	Log.e("Roosters", "<--====-->  Error: IOE " + localIOException.getMessage());
	    	localIOException.printStackTrace();
	    	return null;
	    } finally {
	    	if (sql != null) sql.close();
	    }
	}

	public class UpdateXMLParser extends DefaultHandler {
		private Wedstrijd wedstrijdholder;
		private Wedstrijden wedstrijden = new Wedstrijden();
		
		public UpdateXMLParser(){}

		public Wedstrijden parse(InputStream is) {
			RootElement ROOT = new RootElement("wedstrijden");
			
			Element wedstrijd 	= ROOT.getChild("wedstrijd");
			Element locatie 	= wedstrijd.getChild("locatie");
			Element datum 		= wedstrijd.getChild("datum");
			Element tijd 		= wedstrijd.getChild("tijd");
			Element afstand 	= wedstrijd.getChild("afstand");
	      
			wedstrijd.setStartElementListener(new StartElementListener() {
				public void start(Attributes paramAnonymousAttributes) {
					wedstrijdholder = new Wedstrijd();
				}
			});
	      
			locatie.setEndTextElementListener(new EndTextElementListener() {
				public void end(String locatie) {
					wedstrijdholder.setLocatie(locatie);
				}
			});
	      
			datum.setEndTextElementListener(new EndTextElementListener() {
				public void end(String datum) {
					wedstrijdholder.setDatum(datum);
				}
			});
			  
			tijd.setEndTextElementListener(new EndTextElementListener() {
				public void end(String tijd) {
					wedstrijdholder.setTijd(tijd);
				}
			});
			
			afstand.setEndTextElementListener(new EndTextElementListener() {
				public void end(String afstand) {
					wedstrijdholder.setAfstand(afstand);
				}
			});
			  
			wedstrijd.setEndElementListener(new EndElementListener() {
				public void end() {
					wedstrijden.add(wedstrijdholder);
				}
			});
	      
			try {
				Xml.parse(is, Xml.Encoding.UTF_8, ROOT.getContentHandler());
				return wedstrijden;
			} catch (SAXException localSAXException) {
				Log.e("Roosters", "Error: " + localSAXException.getMessage());
				return null;
			} catch (IOException localIOException) {
				Log.e("Roosters", "Error: " + localIOException.getMessage());
				return null;
			}
		}
	}
}