
import java.io.*;

public class GenerateFile {
    private static final long sizeInBytes = 10*1024*1024;

    public static void main(String[] args) {
        createFile();
    }


    private static void createFile() {
        for(int i=1; i<=Utility.TOTAL_FILES_TO_BE_GENERATED; i++) {
            try {
                String path = "src/main/java/client" + File.separator + "client" + i;
                File file = new File(path);
                //file.createNewFile();
                if(!file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }

                if(!file.exists()) {
                    RandomAccessFile raf = new RandomAccessFile(file, "rw");
                    raf.setLength(sizeInBytes);
                    raf.close();
                    System.out.println("File created");
                    //file.createNewFile();
                }


            } catch (IOException exception) {
                System.out.println(exception.getLocalizedMessage());
            }
        }
    }
}
