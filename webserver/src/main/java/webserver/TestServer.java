package webserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

public class TestServer {
	
	//XML TILL JSON VARIABLER.
		 public static int PRETTY_PRINT_INDENT_FACTOR = 4;
		    public static String TEST_XML_STRING =
		        "<?xml version=\"1.0\" ?><test attrib=\"moretest\">Turn this to JSON</test>";
		public void omdb() {
			
			HttpClient httpclient = null;
			HttpGet httpGet = null;
			HttpResponse response = null;
			StatusLine status = null;
			HttpEntity entity = null;
			InputStream data = null;
			Reader reader = null;

			Gson gson = new Gson();

			try {
				// Create the client that will call the API
				httpclient = HttpClients.createDefault();
				httpGet = new HttpGet("http://localhost:4567/search/Buffy");

				// Call the API and verify that all went well
				response = httpclient.execute(httpGet);
				status = response.getStatusLine();
				if (status.getStatusCode() == 200) {
					// All went well. Let's fetch the data
					entity = response.getEntity();
					data = entity.getContent();

					try {
						// Attempt to parse the data as JSON
						reader = new InputStreamReader(data);
						Movie movie = gson.fromJson(reader, Movie.class);

						System.out.println(
								"Title: " + movie.getTitle() + ", Year: " + movie.getYear() + ",: " + movie.getActors());

					} catch (Exception e) {
						// Something didn't went well. No calls for us.
						e.printStackTrace();
						System.out.println("OMDB didn't respond in a good manner.");
					}
				} else {
					// Something didn't went well. No calls for us.
					System.out.println("OMDB didn't respond in a good manner.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public JSONObject trailerAPI() {
			
			HttpClient httpclient = null;
			HttpGet httpGet = null;
			HttpResponse response = null;
			StatusLine status = null;
			HttpEntity entity = null;
			InputStream data = null;
			Reader reader = null;

			Gson gson = new Gson();

			try {
				// Create the client that will call the API
				httpclient = HttpClients.createDefault();
				httpGet = new HttpGet("http://www.omdbapi.com/?t=harry&y=&plot=short&r=json");

				// Call the API and verify that all went well
				response = httpclient.execute(httpGet);
				status = response.getStatusLine();
				if (status.getStatusCode() == 200) {
					// All went well. Let's fetch the data
					entity = response.getEntity();
					data = entity.getContent();

					try {
						// Attempt to parse the data as JSON
						reader = new InputStreamReader(data);
						Movie movie = gson.fromJson(reader, Movie.class);

						System.out.println(
								"Title: " + movie.getTitle() + ", Year: " + movie.getYear() + ",: " + movie.getActors());

					} catch (Exception e) {
						// Something didn't went well. No calls for us.
						e.printStackTrace();
						System.out.println("Trailer API didn't respond in a good manner.");
					}
				} else {
					// Something didn't went well. No calls for us.
					System.out.println("Trailer API didn't respond in a good manner.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//TESTA XML till JSON:
			
			    try {
		            JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
		            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		            System.out.println();
		            System.out.println(jsonPrettyPrintString);
		        } catch (JSONException je) {
		            System.out.println(je.toString());
		        }
		    return null;
		}

		public static void main(String[] args) throws IOException {
			TestServer ws=new TestServer();
			ws.omdb();
		//	ws.trailerAPI();
		}

}
