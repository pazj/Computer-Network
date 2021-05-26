
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

	public class Webclient {
	    public static void main(String[] args) throws Exception {
	        Webclient http = new Webclient();
	        String s = http.sendGet(); 
	        System.out.println(s);
	        
	       // System.out.print("please input the ip address and port number : ");
	  
	        System.out.println("test POST method : ");
	        String se = http.getWebContentByPost("http://192.168.11.124:1732/index.html", "","ISO-8859-1" , 3000);
	        System.out.println(se);
	    } 

	    //HTTP GET request 
	    private String sendGet() throws Exception {
	    	
	        String url = "http://192.168.11.124:1732/index.html";

	        URL obj = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible ; MSIE 6.0; Windows MT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50272");
	        conn.setRequestProperty("Accept", "text/html");
	        //conn.setConnectTimeout(timeout);

	        int responseCode = conn.getResponseCode();
	        System.out.println("\nSending GET request to URL : " + url);
	        System.out.println("Response Code :" + responseCode);
	        
	        try {
	            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
	                return null;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }

	        InputStream input = conn.getInputStream();
	        String charset = "ISO-8859-1";
	        BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
	        String line = null;
	        StringBuffer sb = new StringBuffer();
	        
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append("\r\n");
	        }
	        if (reader != null) {
	            reader.close();
	        }
	        if (conn != null) {
	            conn.disconnect();
	        }
	        return sb.toString();
	    }
	    
	 
		public String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException { 
			if (urlString == null || urlString.length() == 0) {
				return null;
			}
			
		urlString = (urlString.startsWith("http://" ) ||  urlString.startsWith("https://")) ? urlString : ("http://" + urlString).intern(); 
		
		URL url = new URL(urlString); 
		HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

		connection.setDoOutput(true); 
		connection.setDoInput(true); 
		connection.setRequestMethod("POST"); 

		connection.setUseCaches(false); 
		connection.setInstanceFollowRedirects(true); 

		connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		connection.setRequestProperty("User-Agent", "1/Lisda Awalia/WebClient/COMNET"); 

		connection. setRequestProperty("Accept", "text/xml"); 
		connection. setConnectTimeout(timeout); 

		connection. connect(); 
		DataOutputStream out = new DataOutputStream(connection.getOutputStream()); 


		byte[] content = data.getBytes("UTF-8"); 

		out.write(content);
		out.flush();
		out.close();
		
		try {
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        InputStream input = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
        String line = null;
        StringBuffer sb = new StringBuffer();
        
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\r\n");
        }
        if (reader != null) {
            reader.close();
        }
        if (connection != null) {
            connection.disconnect();
        }
        return sb.toString();
		}
}
