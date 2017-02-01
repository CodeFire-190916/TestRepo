package ua.com.codefire.arch;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class IndexController {

    private static final Logger LOG = Logger.getLogger(IndexController.class.getName());

    private static final int BUFFER_SIZE = 1024;

    @FXML
    private Node root;

    /**
     * Handle recover click action.
     */
    public void onRecoverClick() {
//        try (ZipInputStream zipIs = new ZipInputStream(new FileInputStream(ZIPNAME))) {
//            ZipEntry entry;
//
//            while ((entry = zipIs.getNextEntry()) != null) {
//
//                if (entry.isDirectory()) {
//                    System.out.print("Directory: ");
//                } else {
//                    System.out.print("File: ");
//                }
//
//                System.out.println(entry.getName());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Handle save click action.
     */
    public void onSaveClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selected = directoryChooser.showDialog(root.getScene().getWindow());

        if (selected != null && selected.isDirectory()) {
            boolean done = processArchivate(selected);

            System.out.println(done ? "OK" : "ERROR");
        }
    }

    private boolean processArchivate(File dir) {
        File[] files = dir.listFiles();

        if (files == null) {
            return false;
        }

        boolean result = false;

        byte[] buffer = new byte[BUFFER_SIZE];

        final String filename = String.format(Locale.getDefault(), "%s.zip", dir.getName());

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(filename))) {

            for (File file : files) {
                if (file.isFile()) {
                    zos.putNextEntry(new ZipEntry(file.getName()));

                    // Read file and write it to ZIP.
                    Files.copy(file.toPath(), zos);

                    zos.closeEntry();

                    result = true;
                }
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
            result = false;
        }

        return result;
    }
}


