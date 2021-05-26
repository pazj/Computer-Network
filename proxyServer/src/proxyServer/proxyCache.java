//paz_2017053772

package proxyServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class proxyCache {
	
	//port for the proxy
	private static int port;
	
	//socket for client connections
	private static ServerSocket socket;
	
	
	//create the ProxyCache OBJECT AND THE SOCKET
	public static void init(int p) {
				port = p;
				try {
					//creating a new server socket at the port passed into the cache
					socket =  new ServerSocket(port);
				}catch(IOException e) {
					System.out.println("error creating socket: " + e);
					System.exit(-1);
				}
			}
		
	
	public static void handle(Socket client) {
		Socket server = null;
		HttpRequest request = null;
		HttpResponse response = null;
		
		//process request. if there are any exceptions, then simply
		//return and end this request. this unfortunately means the 
		//client will hang for a while, until it timeouts
		
		//lets read the request
		try {
			BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
		    System.out.println("Reading request...");
			request = new HttpRequest(fromClient);
		    System.out.println("Got request...");
		}catch (IOException e) {
			System.out.println("Error reading request from client: " + e);
			return;
		}
		
		//send request to server
		try {
				//open socket and write request to socket
				server  =  new Socket(request.getHost(), request.getPort());
				DataOutputStream toServer = new DataOutputStream(server.getOutputStream());
			    toServer.writeBytes(request.toString());
				
		}catch(UnknownHostException e) {
			System.out.println("Unknown host: " + request.getHost());
			System.out.println(e);
			return;
		}catch(IOException e) {
			System.out.println("Error writing request to server: " + e);
			return;
		}
		try {
			DataInputStream fromServer = new DataInputStream(server.getInputStream());
			response = new HttpResponse(fromServer);
			DataOutputStream toClient =  new DataOutputStream(client.getOutputStream());
			toClient.writeBytes(response.toString());
		    toClient.write(response.body);
			
			/* write response to client. first headers, then body*/
			client.close();
			server.close();
			
			/*insert object into the cache*/
			/* fill in (optional exercise only)*/
		}catch(IOException e) {
			System.out.println("Error writing response to client: " + e);
		}
	}
	
	//read command line arguments and start proxy
public static void main(String[] args) {
		int myPort = 0;
	
	try {
		System.out.println("starting proxy....");
		myPort = Integer.parseInt(args[0]);
	}catch(ArrayIndexOutOfBoundsException e) {
		System.out.println("Need port number as argument");
		System.exit(-1);
		}catch(NumberFormatException e) {
			System.out.println("Please give port number as integer");
			System.exit(-1);
		}
	
	init(myPort);
	
	//main loop. listen for incoming connection and spawn a new 
	//thread for handling them
	Socket client = null;
	
	while (true) {
	    try {
		client = socket.accept();
		System.out.println("Got connection " + client);
		handle(client);
		} catch (IOException e) {
		System.out.println("Error reading request from client: " + e);
		continue;
	    }
	}

   }


}

