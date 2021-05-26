import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient extends Thread {
	public void run() {
		DatagramSocket datagramSocket = null;
		DatagramPacket receivePacket, sendPacket = null;
		InetAddress inetAddress = null;
		BufferedReader br = null;
		int portNumber = 1234;
		
		try {
			inetAddress = InetAddress.getByName("172.16.90.72"); //opponent ip
			//socket open
			datagramSocket = new DatagramSocket();
			//create inputstream
			br = new BufferedReader(new InputStreamReader(System.in));
			//send a message to the other server
			while(true) {
				System.out.println(portNumber + "Connect to opponent server port");
				String msg = " ";
				System.out.println("Input message : ");
				msg = br.readLine();
				//datagramPacket(client->server)
				sendPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length, inetAddress, portNumber);
				System.out.println("Sending input");
				datagramSocket.send(sendPacket);
				
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			datagramSocket.close();
		}
		
	try {
		//get data from my server
		while(true) {
			byte[] buf = new byte[1024];
			System.out.println("Receiving data");
			receivePacket = new DatagramPacket(buf, buf.length, inetAddress, portNumber); //fill in 
			datagramSocket.receive(receivePacket);
			
			//message send server -> client
			String msg = new String (receivePacket.getData(),0,receivePacket.getData().length);
			System.out.println("From server " + msg);
			
		}
	}catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
  }
}
