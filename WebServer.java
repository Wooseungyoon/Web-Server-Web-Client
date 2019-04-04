package WebServer;

import java.util.*;
import java.net.*;
import java.io.*;

public class WebServer {
	private static final int PORT = 8088;
	public static void main(String[] args) {
		
		try {
			ServerSocket test = new ServerSocket(PORT);//mission 1
					while(true) {
						Socket connectionSock =test.accept();//fill in
						
						HttpRequest request = new HttpRequest(connectionSock);
						
						Thread thread = new Thread(request);//mission 2
						thread.start();
					}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
