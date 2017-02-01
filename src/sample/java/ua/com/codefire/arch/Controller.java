package ua.com.codefire.arch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Controller {

    private static final int BUFFER_SIZE = 1024;
    private static final String ZIPNAME = "C:\\Users\\Tanya\\Downloads\\WebSpider.zip";
    private static final String FILENAME = "C:\\Users\\Tanya\\Downloads\\Yarova_Tetyana_CV_QA.docx";

    /**
     * Handle recover click action.
     */
    public void onRecoverClick() {

        try (ZipInputStream zipIs = new ZipInputStream(new FileInputStream(ZIPNAME))) {
            ZipEntry entry;

            while ((entry = zipIs.getNextEntry()) != null) {

                if (entry.isDirectory()) {
                    System.out.print("Directory: ");
                } else {
                    System.out.print("File: ");
                }

                System.out.println(entry.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle save click action.
     */
    public void onSaveClick() {
        byte[] buffer = new byte[BUFFER_SIZE];

        final String filenameShort = FILENAME
                .substring(FILENAME.lastIndexOf(File.separator));

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(FILENAME + ".zip"))) {

            zos.putNextEntry(new ZipEntry(filenameShort));

            try (FileInputStream fis = new FileInputStream(FILENAME)) {
                int length;
                while ((length = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, length);
                }
            }

            zos.closeEntry();

            System.out.println("Done");
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}


