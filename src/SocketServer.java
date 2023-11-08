import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {

    public static final int PORT = 4462;

    public static ArrayList<User> list;

    public static void main(String[] args) {

        DataOutputStream out;
        DataInputStream in;
        list = new ArrayList<>();

        try {
            ServerSocket s_socket = new ServerSocket(PORT);

            System.out.println("SERVER SOCKET CREATED");

            while(true) {
                Socket c_socket = s_socket.accept();
                System.out.println("SOCKET ACCEPTED");

                ThreadManager c_thread = new ThreadManager();

                c_thread.setSocket(c_socket);

                try {

                    c_thread.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}