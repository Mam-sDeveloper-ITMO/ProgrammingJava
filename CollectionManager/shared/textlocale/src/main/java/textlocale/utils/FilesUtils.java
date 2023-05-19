package textlocale.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Provides utility methods for working with files.
 *
 * @see TextLocal
 */
public class FilesUtils {
    /**
     * Find all files with the given extension in the given package.
     *
     * @param packageName The package to search.
     * @param extension   The extension to match.
     * @return A list of matching files.
     * @throws IOException If an I/O error occurs.
     */
    public static List<File> findFilesWithExtension(String packageName, String extension) throws IOException {
        List<File> matchingFiles = new ArrayList<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resourceUrl = resources.nextElement();
            File directory = new File(resourceUrl.getFile());
            if (directory.exists() && directory.isDirectory()) {
                addMatchingFiles(directory, extension, matchingFiles);
            }
        }

        return matchingFiles;
    }

    /**
     * Recursively add files with the given extension to the list of matching files.
     *
     * @param directory     The directory to search.
     * @param extension     The extension to match.
     * @param matchingFiles The list of matching files.
     */
    private static void addMatchingFiles(File directory, String extension, List<File> matchingFiles) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(extension)) {
                matchingFiles.add(file);
            } else if (file.isDirectory()) {
                addMatchingFiles(file, extension, matchingFiles);
            }
        }
    }
}
