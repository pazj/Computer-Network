
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UDPserver us = new UDPserver();
		us.start();
		UDPClient uc = new UDPClient();
		uc.start();

	}

}
