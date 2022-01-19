import java.net.*;
import java.io.*;


public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Utility.PORT);
            System.out.println("Listening to port : " + Utility.PORT);
            int count = 0;
            while(true){
                try {
                    count += 1;
                    Socket socket = serverSocket.accept();
                    System.out.println("Downloading file..... ");
                    Runnable runnable = new FileDownloader(socket, String.valueOf(count));
                    Thread thread = new Thread(runnable);
                    thread.start();

                } catch (IOException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }
}
