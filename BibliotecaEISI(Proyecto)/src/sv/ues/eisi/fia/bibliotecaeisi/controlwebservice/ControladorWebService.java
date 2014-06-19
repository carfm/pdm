package sv.ues.eisi.fia.bibliotecaeisi.controlwebservice;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import android.util.Log;

public class ControladorWebService {
	public static String informacionError = "Error";

	public String obtenerRespuestaDeURL(String url)
			{
		String respuesta = " ";

		try {
			HttpParams params = new BasicHttpParams();
			int timeoutConnection = 10000;
			HttpConnectionParams.setConnectionTimeout(params, timeoutConnection);
			int timeoutSocket = 10000;
			HttpConnectionParams.setSoTimeout(params, timeoutSocket);
			HttpClient httpClient = new DefaultHttpClient(params);
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			respuesta = EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			// Toast.makeText(ctx, "Error de conexion",
			// Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return respuesta;
	}

	public Document mapeoXML(String xml) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);
		} catch (ParserConfigurationException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (SAXException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		} catch (IOException e) {
			Log.e("Error: ", e.getMessage());
			return null;
		}
		return doc;
	}

	public String getValue(Element item, String str) {
		NodeList n = item.getElementsByTagName(str);
		return this.getElementValue(n.item(0));
	}

	public final String getElementValue(Node elem) {
		Node child;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (child = elem.getFirstChild(); child != null; child = child
						.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
					}
				}
			}
		}
		return "";
	}
	

	// HTTP  request
	//POST(INSERTAR),PUT(ACTUALIZAR,DELETE(BORRAR)
    public void sendHttpRequest(Object obj,String clase,String metodo) throws Exception {
    	  try {
            String url = "http://192.168.1.9:8080/WSprestamoLibrosEISI/webresources/"+clase+"/";
            URL ob = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ob.openConnection();

            //add reqUest header
            con.setRequestMethod(metodo);
            con.setRequestProperty("Content-Type", "application/json");
            //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            Gson gson = new Gson();
            String urlParameters = gson.toJson(obj);
            		//"{\"carnet\":\"NN00006\",\"nombre\":\"carlos\",\"apellido\":\"perez\",\"sexo\":\"M\",\"matganadas\":0}";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
