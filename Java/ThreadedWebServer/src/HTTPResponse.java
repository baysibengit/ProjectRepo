import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class HTTPResponse {

    //member variable
    private  HTTPRequest request_;
    private OutputStream outputStream_;

    private Room room;

    private String echoMessage_;
    private String messageType_;
    private String roomname_;
    private String username_;
    private String message_;


    //Constructor
    public HTTPResponse(HTTPRequest request) {

        this.request_ = request;

    }

    public void send() throws IOException, NoSuchAlgorithmException {
        //Get output stream from client socket
        outputStream_ = request_.getClient().getOutputStream();
        //Wrap output stream in print writer
        PrintWriter pw = new PrintWriter(outputStream_);
        //Create a file object with requested file name as the path
        File file = new File(request_.getRequestedFileName());

        if ( !request_.getIsWebSocket() ) {

            if (file.exists()) {
                //Sends out success
                pw.print("HTTP/1.1 200 OK\r\n");
            } else {
                //Send out error message
                String fileResponseError = "File not found: " + request_.getRequestedFileName();
                pw.print("HTTP/1.1 404 Not Found\r\n\r\n" + fileResponseError);
                pw.flush();
                pw.close();
                request_.getClient().close();
                return;
            }
            //Handle all header types for requested files
            if (request_.getRequestedFileName().endsWith(".html")) {
                pw.print("Content-type: text/html\r\n");
            } else if (request_.getRequestedFileName().endsWith(".css")) {
                pw.print("Content-type: text/css\r\n");
            } else if (request_.getRequestedFileName().endsWith(".jpeg") || request_.getRequestedFileName().endsWith(".jpg")) {
                pw.print("Content-type: image/jpeg\r\n");
            }
            //Send blank line to signify that we are done with headers
            pw.print("\r\n");
            //Flush header contents
            pw.flush();

            FileInputStream fileInputStream = new FileInputStream(file);

            //Use transferTo so we do not read it line by line and paste everything
            fileInputStream.transferTo(outputStream_);

            //Close all connections  and flush remaining contents of file
            outputStream_.flush();
            pw.close();
            outputStream_.close();
            request_.getClient().close();
        }

        if ( request_.getIsWebSocket() ) {

            //Encode accept
            String acceptString = request_.getAcceptString_();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(acceptString.getBytes());
            String finalAcceptString = Base64.getEncoder().encodeToString(md.digest());

            pw.print("HTTP/1.1 101 Switching Protocols \r\n");
            pw.print("Upgrade: websocket \r\n");
            pw.print("Connection: Upgrade \r\n");
            pw.print("Sec-WebSocket-Accept: " + finalAcceptString + "\r\n\r\n");
            pw.flush();

            //Keep connection open to receive messages
            while (true) {


                //Wrap input in a dataInputStream
                DataInputStream dataInputStream = new DataInputStream( request_.getClient().getInputStream() );

                // FIRST TWO BYTES
                byte[] headerBytes = dataInputStream.readNBytes(2);

                // MASK BIT
                boolean hasMask = false;
                int maskIdentifier = headerBytes[1] & 0x80;
                if (maskIdentifier != 0) {

                    hasMask = true;

                }

//                boolean hasMask = (headerBytes[1] & 0x80) != 0;

                // LENGTH VALUE
                int payloadLength = headerBytes[1];

                if (payloadLength < 126) {
                    payloadLength = payloadLength & 0x7F;
                } else if (payloadLength == 126) {
                    payloadLength = dataInputStream.readShort();
                } else {
                    payloadLength = (int) dataInputStream.readLong();
                }

                // Initialize byte array to handle if there is a mask
                byte[] decodedMessage;

                if ( hasMask ) {

                    decodedMessage = new byte[payloadLength];
                    byte[] maskArray = dataInputStream.readNBytes(4);

                    //Initialize empty  variable to store decoded value
                    byte[] encodedMessage = dataInputStream.readNBytes(payloadLength);

                    for ( int i = 0; i < encodedMessage.length; i++ ) {
                        decodedMessage[i] = (byte) (encodedMessage[i] ^ maskArray[i % 4]);
                    }

                } else {
                    decodedMessage = dataInputStream.readNBytes( payloadLength );
                }

                System.out.println( "Decoded Message: " + new String( decodedMessage ) ); // DEBUGGING

                echoMessage_ = new String(decodedMessage);

                //echoMessage_ = readPacket(request_);

                System.out.println( "Thread Id " + String.valueOf(Thread.currentThread().threadId()) );
                sendMessage( echoMessage_ , outputStream_ );

                messageType_ = getMessageType();
                roomname_ = getRoomname();
                username_ = getUsername();
                if (messageType_.equals("message")) {
                    message_ = getMessage();
                }

                room = Room.getRoom( roomname_ );

                if ( messageType_.equals("join") ) {

                    room.addClient( username_, request_.getClient() );

                    //Sends all messages upon joins
                    for( String message : room.getMessagesList() ) {

                        sendMessage( message, outputStream_ );

                    }

                }

                if ( messageType_.equals("leave") ) {

                    room.removeClient(username_, request_.getClient());


                }

                room.sendMessageToAllUsers( echoMessage_ );


            } // END OF WHILE-TRUE LOOP

        } // END OF WEB SOCKET STUFF

    } // END OF send()


//    private String readPacket(HTTPRequest request) throws IOException {
//
//        request = request_;
//        String finalDecodedMessage = "";
//        //Wrap input in a dataInputStream
//        DataInputStream dataInputStream = new DataInputStream( request.getClient().getInputStream() );
//
//            // FIRST TWO BYTES
//            byte[] headerBytes = dataInputStream.readNBytes(2);
//
//            // MASK BIT
//            boolean hasMask = false;
//            int maskIdentifier = headerBytes[1] & 0x80;
//            if (maskIdentifier != 0) {
//
//                hasMask = true;
//
//            }
//
//    //                boolean hasMask = (headerBytes[1] & 0x80) != 0;
//
//            // LENGTH VALUE
//            int payloadLength = headerBytes[1];
//
//            if (payloadLength < 126) {
//                payloadLength = payloadLength & 0x7F;
//            } else if (payloadLength == 126) {
//                payloadLength = dataInputStream.readShort();
//            } else {
//                payloadLength = (int) dataInputStream.readLong();
//            }
//
//            // Initialize byte array to handle if there is a mask
//            byte[] decodedMessage;
//
//            if ( hasMask ) {
//
//                decodedMessage = new byte[payloadLength];
//                byte[] maskArray = dataInputStream.readNBytes(4);
//
//                //Initialize empty  variable to store decoded value
//                byte[] encodedMessage = dataInputStream.readNBytes(payloadLength);
//
//                for ( int i = 0; i < encodedMessage.length; i++ ) {
//                    decodedMessage[i] = (byte) (encodedMessage[i] ^ maskArray[i % 4]);
//                }
//
//            } else {
//                decodedMessage = dataInputStream.readNBytes( payloadLength );
//            }
//
//            System.out.println( "Decoded Message: " + new String( decodedMessage ) ); // DEBUGGING
//
//            finalDecodedMessage = new String(decodedMessage);
//
//
//
//
//            return finalDecodedMessage;
//    }

    public void handShake(HTTPRequest request){
        request = request_;
        request.getAcceptString_();

    }


    public static void sendMessage( String message, OutputStream outputStream ) throws IOException {

        DataOutputStream dataOutputStream = new DataOutputStream( outputStream );
        int messageLength = message.length();

        byte b0 = (byte) 0x81;
        dataOutputStream.writeByte(b0);
        byte b1;

        if (messageLength < 126) {
            b1 = (byte) messageLength;
            dataOutputStream.writeByte(b1);

        } else if (messageLength < Math.pow(2, 16)) {
            dataOutputStream.writeByte(126);
            dataOutputStream.writeShort(messageLength);
        } else {
            dataOutputStream.writeByte(127);
            dataOutputStream.writeLong(messageLength);
        }
        dataOutputStream.writeBytes(message);
        dataOutputStream.flush();

    }

    public String getMessageType() {

        String roomname = echoMessage_.split("\"type\":\"") [1];
        roomname = roomname.split("\"")[0];
        return roomname;

    }

    public String getUsername() {

        String roomname = echoMessage_.split("\"user\":\"")[1];
        roomname = roomname.split("\"")[0];
        return roomname;

    }

    public String getRoomname() {

        String roomname = echoMessage_.split("\"room\":\"")[1];
        roomname = roomname.split("\"")[0];
        return roomname;

    }

    public String getMessage() {

        String roomname = echoMessage_.split("\"message\":\"")[1];
        roomname = roomname.split("\"")[0];
        return roomname;

    }

} // END OF HTTPResponse CLASS
