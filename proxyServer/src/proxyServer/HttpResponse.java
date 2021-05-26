//paz_2017053772

package proxyServer;

import java.io.DataInputStream;
import java.io.IOException;

public class HttpResponse {
	
	final static String CRLF = "\r\n";
	
	//attribute of size of the buffer to read the object
	final static int BUF_SIZE = 8192;
	
	/**max size of object that the proxy can handle**/
	 final static int MAX_OBJECT_SIZE = 100000;
	 
	String version;
	int status;
	String statusLine = "";
	String headers = "";
	
	public byte[] body = new byte[MAX_OBJECT_SIZE];
	
	//to read the response from the server
	@SuppressWarnings("deprecation")
	public HttpResponse(DataInputStream fromServer) {
		/*length of the object */
		int length = -1;
		boolean gotStatusLine = false;
		
		/* first read status line and response headers*/
		try {
			String line = fromServer.readLine();
			while(line.length() != 0) {
				if(!gotStatusLine) {
					statusLine = line;
					gotStatusLine = true;
				}else {
					headers += line + CRLF;
				}
				/*get length of content as indicated by
				 * Content-Length header. unfortunately this is not
				 * present in every response. some servers return the
				 * header "content-length", others return
				 * "content-length". you need to check for both 
				 * here*/
				if(line.startsWith("Content-Length:") || line.startsWith("Content-length:")) {
					String[] tmp = line.split(" ");
					length = Integer.parseInt(tmp[1]);
					
				}
				
				line = fromServer.readLine();
				 
			}
			
		}catch(IOException e) {
			System.out.println("Error reading headers from server: " + e);
			return;
		}
		try {
			int bytesRead = 0;
			byte buf[] = new byte[BUF_SIZE];
			boolean loop = false;
			
			if(length == -1) {
				loop = true;
			}
			while(bytesRead < length || loop) {
				int res = fromServer.read(buf, 0, BUF_SIZE);
				
				if(res == -1) {
					break;
				}
				
				for(int i = 0; i < res && (i + bytesRead) < MAX_OBJECT_SIZE; i++) {
					body[bytesRead + i] = buf[i];
				}
				bytesRead += res;
			}
		}catch(IOException e) {
			System.out.println("Error reading response body:" + e);
			return;
		}
	}
	

	public String toString() {
		String res = "";
		
		res = statusLine + CRLF;
		res += headers;
		res += CRLF;
		
		return res;
	}
}
