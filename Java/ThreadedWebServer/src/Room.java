import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Room {

    // STATIC
    private static HashMap<String, Room > roomsMap_ = new HashMap<>();

    private String roomname_;

    private HashMap< String, Socket > usersMap_ = new HashMap<>();

    private ArrayList<String> messagesList_ = new ArrayList<>();


    private Room(String roomname ) {

        roomname_ = roomname ;

    }

    public synchronized static Room getRoom( String roomname ) {

         Room room = roomsMap_.get( roomname );

         if (room == null){

             room = new Room( roomname );

             roomsMap_.put(roomname, room);

             return room;

        }

         return room;

    }

    //Function to store all clients(users)
    public synchronized void addClient( String username, Socket socket ) {

        usersMap_.put( username, socket );

    }

    //Function to remove clients
    public synchronized void removeClient( String username, Socket socket ) {

        usersMap_.remove( username, socket );

    }

    public synchronized void sendMessageToAllUsers ( String message ) throws IOException {
        //Adding all messages to arraylist ,so I can later send it out when somebody joins
        //to fulfill extras assignment on ChatServerExtras
        messagesList_.add( message );

        String myUsername = (message.split("\"user\":\"")[1]).split("\"")[0];;

        for (Map.Entry<String, Socket> entry: usersMap_.entrySet() ){

            if(!myUsername.equals(entry.getKey())) {
                Socket socket = entry.getValue();

                HTTPResponse.sendMessage(message, socket.getOutputStream());
            }

         }
    }

    public ArrayList< String > getMessagesList() {
        //Getter for all messages
        return messagesList_;

    }

}
