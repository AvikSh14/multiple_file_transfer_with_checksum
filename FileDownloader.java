import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;


public class FileDownloader implements Runnable {
    Socket socket;
    String fileName;
    String storeLocation = "./server/";


    public FileDownloader(Socket socket, String fileName) {
        this.socket = socket;
        this.fileName = fileName;
    }


    public void run() {
        try {
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = this.socket.getInputStream();
                outputStream = new FileOutputStream(storeLocation + fileName);
                System.out.println("FileName : " + storeLocation + fileName);
                byte[] bytes = new byte[Utility.BUFFER_SIZE];
                System.out.println("Starting reading the file");
                int count;
                int checkSumLength = inputStream.read();
                byte[] checkSum = new byte[checkSumLength];
                int totalBytes = inputStream.read(checkSum);
                try {
                    MessageDigest digest = MessageDigest.getInstance("MD5");

                    while ((count = inputStream.read(bytes)) > 0) {
                        // System.out.println("Count soccketIntput FileDownloader : " + count);
                        outputStream.write(bytes, 0, count);
                        digest.update(bytes, 0, count);
                    }
                    System.out.println("Checksum Matched : " + Arrays.equals(checkSum, digest.digest()));
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }


                System.out.println("File successfully downloaded :" + fileName);
                outputStream.close();
                inputStream.close();
            } catch (FileNotFoundException ex) {
                System.out.println("File not found. ");
            }
            this.socket.close();

        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }


    }

}