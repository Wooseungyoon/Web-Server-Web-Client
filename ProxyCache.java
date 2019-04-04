package Proxy;

import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyCache 
{
    /** Port for the proxy */
    private static int port;

    /** Socket for client connections */
    private static ServerSocket socket;

    /** Cache for Socket */
    private static Map cache;

    /** Create the ProxyCache object and the socket */
    public static void init(int p)
    {
	port = p;

	try
        {
	    socket = new ServerSocket (port);
	}
        catch (IOException e)
        {
	    System.out.println("Error creating socket: " + e);
	    System.exit(-1);
	}
    }

    public static void handle(Socket client)
    {
	Socket server = null;
	HttpRequest request = null;
	HttpResponse response = null;

	/* Process request. If there are any exceptions, then simply
	 * return and end this request. This unfortunately means the
	 * client will hang for a while, until it timeouts. */

	/* Read request */
	try
        {
	    BufferedReader fromClient = new BufferedReader (new InputStreamReader (client.getInputStream()));
            request = new HttpRequest (fromClient);
	}
        catch (IOException e)
        {
	    System.out.println("Error reading request from client: " + e);
	    return;
	}

	/* Send request to server */
	try
        {
	    /* Open socket and write request to socket */
	    System.out.println("\n\n3\nSend request to server:");
	    server = new Socket (request.getHost(), request.getPort());
	    System.out.println (server);
	    DataOutputStream toServer = new DataOutputStream (server.getOutputStream());
	    toServer.writeBytes (request.toString());
            System.out.println ("Already sent request to server.\n\n");
	}
        catch (UnknownHostException e)
        {
	    System.out.println("Unknown host: " + request.getHost());
	    System.out.println(e);
	    return;
	}
        catch (IOException e)
        {
	    System.out.println("Error writing request to server: " + e);
	    return;
	}
        catch (NullPointerException e)
        {
	    System.out.println("Error writing request to server: " + e);
	    return;
	}

	/* Read response and forward it to client */
        System.out.println ("4\nRead response and forward it to the client:\n");
	try
        {
	    DataInputStream fromServer = new DataInputStream (server.getInputStream());
	    response = new HttpResponse (fromServer);
	    DataOutputStream toClient = new DataOutputStream (client.getOutputStream());

	    /* Write response to client: headers */
            System.out.println ("Write headers to client:");
            System.out.print (response.toString());
            toClient.writeBytes (response.toString());

	    /* Write response to client: body */
            System.out.println ("Write body to client:");
            System.out.println (response.body);
           
            toClient.write (response.body);
        }
        catch (IOException e)
        {
	    System.out.println();
	}

        System.out.println ("Finished sending response to client");

	try
        {
	    client.close();
	    server.close();

	    /* Insert object into the cache */
	    cache.put (request, response);
	}
        catch (IOException e)
        {
	    System.out.println("\n\nError writing response to client: " + e);
	}
        catch (NullPointerException e)
        {
	    System.out.println();
	}
    }


    /** Read command line arguments and start proxy */
    public static void main(String args[])
    {
	int myPort = 0;
        System.out.println("1\nSetting up socket");  
	
	try
        {
	    myPort = Integer.parseInt(args[0]);
	}
        catch (ArrayIndexOutOfBoundsException e)
        {
	    System.out.println("Need port number as argument");
	    System.exit(-1);
	}
        catch (NumberFormatException e)
        {
	    System.out.println("Please give port number as integer.");
	    System.exit(-1);
	}
	
	init(myPort);
        System.out.println("Ready and listening at port: " + myPort + "\n\n");    

	/** Main loop. Listen for incoming connections and spawn a new
	 * thread for handling them */
	Socket client = null;
	
	while (true)
        {
	    try
            {
		client = socket.accept();
		handle(client);
	    }
            catch (IOException e)
            {
		System.out.println("Error reading request from client: " + e);
		/* Definitely cannot continue processing this request,
		 * so skip to next iteration of while loop. */
		continue;
	    }
	}

    }
}