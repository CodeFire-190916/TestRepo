package ua.com.codefire.arch;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Controller {

    private static final String ZIPNAME = "C:\\Users\\Tanya\\Downloads\\WebSpider.zip";
    private static final String FILENAME = "C:\\Users\\Tanya\\Downloads\\Yarova_Tetyana_CV_QA.docx";
    private static final int BUFFER_SIZE = 1024;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnRecover;

    public void onRecoverClick() {

        ZipInputStream zipIs = null;
        try {
            try {
                zipIs = new ZipInputStream(new FileInputStream(ZIPNAME));
                ZipEntry entry = null;
                while ((entry = zipIs.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        System.out.print("Directory: ");
                    } else {
                        System.out.print("File: ");
                    }
                    System.out.println(entry.getName());
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                zipIs.close();
            } catch (Exception e) {
            }
        }
    }

    public void onSaveClick() {
        byte[] buffer = new byte[BUFFER_SIZE];

        final String filenameShort = FILENAME.substring(
                FILENAME.lastIndexOf(File.separator));
        try {
            final FileOutputStream fos = new FileOutputStream(FILENAME + ".zip");
            try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                final ZipEntry ze = new ZipEntry(filenameShort);
                zos.putNextEntry(ze);
                try (FileInputStream fis = new FileInputStream(FILENAME)) {
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                }
                zos.closeEntry();
                System.out.println("Done");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}


