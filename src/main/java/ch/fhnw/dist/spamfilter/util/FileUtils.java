package ch.fhnw.dist.spamfilter.util;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Hoang Tran <hoang.tran@students.fhnw.ch>
 */
public class FileUtils {
    public static final String TEMP_PATH = "C:/spam-filter-bayer-temp";

    private FileUtils() {
        // utility classes do not have public constructor
    }

    public static String getFileContent(File file) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
            return new String(encoded);
        } catch (IOException e) {
            System.err.println("Could not read content from file: " + file.getAbsolutePath());
        }
        return "";
    }

    public static ZipFile getZipFileFromResources(String fileName) {
        File file = getFileFromResources(fileName);
        if (fileExists(file)) {
            try {
                return new ZipFile(file);
            } catch (ZipException e) {
                System.err.println("Could not read zip file: " + fileName);
            }
        }
        return null;
    }

    public static File getFileFromResources(String fileName) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource != null) {
            // replace %20 back to whitespace
            String pathname = resource.getFile().replace("%20", " ");
            return new File(pathname);
        }
        return null;
    }

    public static boolean fileExists(File file) {
        return (file != null && file.exists());
    }


    public static boolean deleteTempDir() {
        File tempDir = new File(TEMP_PATH);
        return deleteNonEmptyDirectory(tempDir);
    }

    public static boolean deleteNonEmptyDirectory(File directory) {
        boolean deleteSuccessful = true;
        File[] childFiles = directory.listFiles();
        if (childFiles != null && childFiles.length > 0) {
            for (File file : childFiles) {
                if (file.isDirectory()) {
                    deleteSuccessful &= deleteNonEmptyDirectory(file);
                } else {
                    deleteSuccessful &= file.delete();
                }
            }
        }
        deleteSuccessful &= directory.delete();
        return deleteSuccessful;
    }
}
