import java.io.IOException;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class MyRunnable implements Runnable{

    private Socket socket_;

    MyRunnable(Socket socket) {

        socket_ = socket;

    }

    @Override
    public void run() {

            try {

                HTTPRequest request = new HTTPRequest( socket_ );

                HTTPResponse response = new HTTPResponse( request );

                response.send();

            } catch (IOException | NoSuchAlgorithmException e) {

                throw new RuntimeException(e);

            }

        }

    }
