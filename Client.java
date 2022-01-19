// A Java program for a Client 

import java.net.*;
import java.io.*;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Arrays;
import java.util.concurrent.*;


public class Client {
    private static String processDirectory = "./client";

    private static int getPageCount(int concurrency) {
        File folder = new File(processDirectory);
        File[] files = folder.listFiles();
        int pageCount = 1;
        if (files != null) {
            pageCount =  (int)Math.ceil((double) files.length / (double)concurrency);
        }

        return pageCount;
    }

    private static File[] getFiles(int pageNumber, int concurrency) {
        File folder = new File(processDirectory);
        System.out.println("Directory : " + processDirectory);
        File[] files = folder.listFiles();
        System.out.println("Length: " + files.length);
        Arrays.sort(files, Comparator.comparingInt(file -> Integer.parseInt(file.getName().substring(6))));
        int start = (pageNumber - 1) * concurrency;
        int end = pageNumber * concurrency;
        return Arrays.copyOfRange(files, start, end);
    }

    public static void main(String[] args) throws IOException {
       // Scanner scanner = new Scanner(System.in);
        processDirectory = "./" + args[0];

      //  System.out.print("Please enter concurrency: ");
        int concurrency = 1;
        try {
            concurrency = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }


//        try {
//            concurrency = scanner.nextInt();
//        } catch (Exception exception) {
//            System.out.println(exception.getLocalizedMessage());
//        }

        System.out.println("Directory : " + processDirectory + ", concurrency: " + concurrency);

        long start = System.currentTimeMillis();
        int pages = getPageCount(concurrency);
        for (int i = 1; i <= pages; i++) {
            try {
                ExecutorService executorService = Executors.newFixedThreadPool(concurrency);
                File[] files = getFiles(i, concurrency);
                for (File file : files) {
                    Runnable runnable = new FileTransfer(file);
                    executorService.submit(runnable);
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        long transferTime = (end - start);
        System.out.println("Transfer Time for concurrency " + concurrency + " is: " + transferTime);
    }
}