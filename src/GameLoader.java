import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GameLoader {
    public static void main(String[] args) {
        String zipPath = "D://Games/savegames/saves.zip";
        String extractPath = "D://Games/savegames";
        String saveFilePath = "D://Games/savegames/save1.dat";

        openZip(zipPath, extractPath);

        GameProgress progress = openProgress(saveFilePath);
        if (progress != null) {
            System.out.println(progress);
        }
    }

    public static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String filePath = extractPath + File.separator + entry.getName();
                try (FileOutputStream fos = new FileOutputStream(filePath)) {
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        fos.write(c);
                    }
                    fos.flush();
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String savePath) {
        GameProgress progress = null;
        try (FileInputStream fis = new FileInputStream(savePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            progress = (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return progress;
    }


}