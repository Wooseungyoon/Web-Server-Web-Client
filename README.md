**web-server-client**


A Java implementation of Simple HTTP Web Client and a Multithreaded Web Server.


**###Directory Structure**

1. **Server**: Contains source files for the server implementation.  

    -ServerInitializer.java: Initializes the WebServer at either a default 8080 or a user provided port.  
    -WebServer.java: Implements a multhreaded server and initializes a serverSocket to listens to the client requests. Once a client is connected, the processing is handed over to a separate RequestHandler thread.  
    -HttpRequest.java: Communicates with and processes a client's HTTP request in a separate thread.  

2. **Client**: Contains source files for the client implemenetation.

    -WebClient.java: Implements a single threaded web client which communicates with the server on a specific ip:port address and requests a file on the server.  
    -HttpResponse.java: Response by server of client's HTTP request.  
