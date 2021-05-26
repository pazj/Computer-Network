import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;


public class httpClient {
	 public static void main(String[] args) throws IOException{
	      httpClient client = new httpClient();
	      String s;
	      s = client.getWebContentByGet("http://166.104.143.225:60469/test/index.html");
	      System.out.println(s);
	      
	      Scanner keyboard = new Scanner(System.in);
	      String num = "2017053772/";
	      num += keyboard.nextLine();
	      
	      s = client.getWebContentByPost("http://166.104.143.225:60469/test/picResult", num);
	      System.out.println(s);
	      
	      s = client.getWebContentByPost("http://166.104.143.225:60469/test/postHandleTest", num);
	      System.out.println(s);
	   }
	   
	//GET
	public String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException{
		if(urlString ==  null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://") ? urlString : ("http://" + urlString).intern());
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		connection.setRequestProperty("User-Agent", "Mozilla/4.0(compatible; MSIE 6.0; windows NT 5.2; Trident/4.0;.NET CLR 1.1.4322; .NET 2.0.50727)");
		connection.setRequestProperty("Accept", "text/html");
		connection.setConnectTimeout(timeout);
		try {
			if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		InputStream input = connection.getInputStream();
		BufferedReader reader =  new BufferedReader(new InputStreamReader(input,charset));
		String line = null;
		StringBuffer sb =  new StringBuffer();
		while((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
			}
		if(reader != null) {
			reader.close();
		}
		if (connection != null) {
			connection.disconnect();
		}
		return sb.toString();
		
	}
	
	public String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException{
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		
		connection.setRequestProperty("Content-Type", "tex/xml;charset=UTF-8");
		
		connection.setRequestProperty("Accept", "text/xml");
		connection.setRequestProperty("User-agent","2017053772/PAZCAROLINAJIMENEZSAUCEDO/WEBCLIENT/COMPUTERNETWORK");
		connection.setConnectTimeout(timeout);
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		
		byte[] content = data.getBytes("UTF-8");
		
		out.write(content);
		out.flush();
		out.close();
		
		   try {
		         if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
		            return null;
		         }
		      }
		      catch(IOException e) {
		         e.printStackTrace();
		         return null;
		      }
		      
		      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
		      String line;
		      StringBuffer sb = new StringBuffer();
		      
		      while((line = reader.readLine()) != null) {
		         sb.append(line).append("\r\n");
		      }
		      if(reader != null) {
		         reader.close();
		      }
		      if(connection != null) {
		         connection.disconnect();
		      }
		      return sb.toString();
		   }
		   
		   public String getWebContentByPost(String urlString, String data) throws IOException{
		      return getWebContentByPost(urlString, data, "UTF-8", 5000);
		   }
		   
		   public String getWebContentByGet(String urlString) throws IOException {
		      return getWebContentByGet(urlString, "iso-8859-1", 5000);
		   }
		
		
	}
	

