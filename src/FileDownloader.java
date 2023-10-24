import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownloader {

    public static void downloadFile(String fileUrl, String saveFilePath) {
        try {
            URL url = new URL(fileUrl);
            try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            }
            System.out.println("Plik zapisany: " + saveFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[] fileUrls = {
                "https://scieżka-do-pliku1",
                "https://scieżka-do-pliku2",
                "https://scieżka-do-pliku3"
        };

        String[] saveFilePaths = {
                "sciezka-na-dysku1.txt",
                "sciezka-na-dysku2.txt",
                "sciezka-na-dysku3.txt"
        };

        Thread[] threads = new Thread[fileUrls.length];

        for (int i = 0; i < fileUrls.length; i++) {
            String fileUrl = fileUrls[i];
            String saveFilePath = saveFilePaths[i];
            threads[i] = new Thread(() -> downloadFile(fileUrl, saveFilePath));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Wszystkie pliki zostały pobrane.");
    }
}
