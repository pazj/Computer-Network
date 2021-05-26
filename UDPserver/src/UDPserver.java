import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.util.Scanner;

public class UDPserver extends Thread {
	
	public void run() {
		DatagramPacket receivePacket, sendPacket = null;
		final int PORTNUMBER = 1234;
		MulticastSocket ms = null;
			super.run();
			try {
				ms = new MulticastSocket(PORTNUMBER);
				//socket create
				System.out.println(PORTNUMBER + "Port Server Ready");
				
				while(true) {

					byte[] buf = new byte[1024];
					receivePacket = new DatagramPacket(buf, buf.length);
					//data receive

					ms.receive(receivePacket); //fill in here 
					//receive from data
					String msg = new String (receivePacket.getData(),0,receivePacket.getLength());
					System.out.println("From opponent client MSG: " + msg);
					
					//server -> my client
					@SuppressWarnings("resource")
					Scanner sc = new Scanner(System.in);
					String sendMessage = sc.nextLine();
					sendPacket = new DatagramPacket(buf,buf.length,PORTNUMBER);  //fill in here
					ms.send(sendPacket);
				
				}
			}catch(IOException e) {
				e.printStackTrace();
			}finally {
				ms.close();
			}
	}
	

}
