import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class HTTPRequest {
    //Member variables
    private Socket client;
    private String requestedFileName;
    private boolean valid = false;
    private Map< String, String > headerMap_ = new HashMap<>();
    private Scanner clientInputScanner_;
    private String acceptString_ = "";
    private boolean isWebSocketRequest = false;
    //Constructors
    public HTTPRequest(Socket client) throws IOException {
        this.client = client;
        // Parse and process the client's  request
        readRequest();
    }
    public Socket getClient() {
        return client;
    }

    private void readRequest() {
        try {
            clientInputScanner_ = new Scanner(client.getInputStream());
            boolean done = false;
            boolean gotFileName = false;
            String fileLocation = "./reasources/";
            // Read all the headers sent from the client (browser)
            while ( true ) { // !done) {
                String headerLine = clientInputScanner_.nextLine();
                System.out.println(headerLine);

                if (!gotFileName) {  // handle 1st line of http request
                    //split by empty space
                    String[] pieces = headerLine.split(" ");
                    //GET filename versionHTTP
                    requestedFileName = pieces[1];
                    //Send path to index.html
                    if (requestedFileName.equals("/")) {
                        requestedFileName = "/index.html";
                    }
                    // "index.html" -> /path/to/file/index.html
                    // "abc.jpb" -> /path/to/file/abc.jpg
                    requestedFileName = fileLocation + requestedFileName;
                    gotFileName = true;
                    valid = true;
                }
                else if (headerLine.isEmpty()) {
                   // done = true;
                    break; // alternative to using "done" variable
                }
                else {
                    //  handle rest of head header lines and load into map
                    String headerKey = headerLine.split(":")[0];

                    String headerValue = headerLine.split(": ")[1];

                    headerMap_.put(headerKey, headerValue);
                }
            }
            //Iterate and print out elements of header map

            System.out.println("list of headers:");

            for (Map.Entry<String,String> entry : headerMap_.entrySet())
                System.out.println("Key = " + entry.getKey() +
                        ", Value = " + entry.getValue());

            //Determine boolean if is websocket request
            if (headerMap_.containsKey("Sec-WebSocket-Key")){
                isWebSocketRequest = true;
            }

            //Print out key
            System.out.println( "Sec-WebSocket-Key "  + headerMap_.get("Sec-WebSocket-Key"));
            //Store key in string variable
            String keyString = headerMap_.get("Sec-WebSocket-Key");
            //Hard coded magic string given in slides
            String magicSting = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
            //Concatenate magic string with key string to get accept keyString
            acceptString_ = keyString + magicSting;






        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //Get function
    public String getRequestedFileName() {
        return requestedFileName;
    }
    //Get function for accept string
    public String getAcceptString_(){
        return acceptString_;
    }
    public boolean getIsWebSocket(){
        return isWebSocketRequest;
    }

}






