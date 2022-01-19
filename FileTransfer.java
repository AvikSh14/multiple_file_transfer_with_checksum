import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileTransfer implements Runnable {
    File file;
    Socket socket = null;
    String host = "127.0.0.1";


    public FileTransfer(File file) {
        this.file = file;
    }

    public void run() {
        if (this.file.isFile()) {
            try {
                this.socket = new Socket(host, Utility.PORT);
                //System.out.println(this.file.getName());

                byte[] bytes = new byte[Utility.BUFFER_SIZE];
                InputStream inputStream = new FileInputStream(this.file);
                OutputStream outputStream = this.socket.getOutputStream();

                int count;
                byte[] checkSum = checksum(file);
                if(checkSum!=null) {
                    //System.out.println();
                    outputStream.write(new byte[]{(byte)checkSum.length});
                    outputStream.write(checkSum);
                    //System.out.println("CheckSum : " + checkSum.length);
                }

                //out.write(file.length());
                while ((count = inputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, count);
                }

                outputStream.close();
                inputStream.close();
                this.socket.close();
            } catch (IOException exception) {
                System.out.println(exception.getLocalizedMessage());
            }

        }
    }

    private static byte[] checksum(File file) throws IOException {
        MessageDigest digest = null;
        byte[] byteArray = new byte[Utility.BUFFER_SIZE];
        try {
            digest = MessageDigest.getInstance("MD5");

            FileInputStream fis = new FileInputStream(file);

            int bytesCount = 0;

            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
