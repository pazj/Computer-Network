//paz_2017053772

package proxyServer;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
	//Auxiliary variables
	final static String CRLF = "\r\n";
	final static int HTTP_PORT = 80;
	
	String method;
	String URL;
	String version;
	String headers = "";
	
	//create variables for port and server
	private String host;
	private int port;
	
	public HttpRequest(BufferedReader from) {
		String firstLine = "";
		try {
			firstLine = from.readLine();
		}catch(IOException e) {
			System.out.println("Error reading request line: " + e);
		}
		
		String[] tmp = firstLine.split(" ");
		method = tmp[0];
		URL = tmp[1];
		version = tmp[2];
		
		System.out.println("URL is: " + URL);
		
		if(!method.equals("GET")) {
			System.out.println("Error: Method not GET");
		}
		try {
			String line = from.readLine();
			while(line.length() != 0) {
				headers += line + CRLF;
				
				/* we need to find a host header to know which server to
				 * contact in case the request URI is not complete */
				
				if(line.startsWith("Host:")) {
					tmp = line.split(" ");
					if(tmp[1].indexOf(':') > 0) {
						String[] tmp2 = tmp[1].split(":");
						host = tmp2[0];
						port = Integer.parseInt(tmp2[1]);
						
					}else {
						host = tmp[1];
						port = HTTP_PORT;
					}
				}
				 line = from.readLine();
			}
		}catch(IOException e) {
			System.out.println("Error reading from socket: " + e);
			return;
		}
		System.out.println("Host to contact is: " + host + "at port" + port);
	}
	
	/** return host for which this request is intended**/
	public String getHost() {
		return host;
	}
	/** return port from server**/
	public int getPort() {
		return port;
	}
	
	/** Return URL to connect to */
    public String getURL() {
    		return URL;
    }
	 /**convert request into a string for easy re-sending**/
	public String toString() {
		String req = "";
		
		req = method + " " + URL + " " + version + CRLF;
		req += headers;
		
		/* this proxy does not support persistent connections*/
		req += "Connection: close" + CRLF;
		req += CRLF;
		
		return req;
	}
}
