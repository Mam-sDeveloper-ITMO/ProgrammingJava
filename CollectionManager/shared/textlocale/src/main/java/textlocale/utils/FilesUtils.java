package textlocale.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import textlocale.TextLocale;

/**
 * Provides utility methods for working with files.
 *
 * @see TextLocale
 */
public class FilesUtils {
    /**
     * Find all files with the given extension in the given package.
     *
     * @param packageName The package to search.
     * @param extension   The extension to match.
     * @return A map of package+file names and corresponding files.
     * @throws IOException If an I/O error occurs.
     */
    public static Map<String, File> findFilesWithExtension(String packageName, String extension) throws IOException {
        Map<String, File> matchingFiles = new HashMap<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resourceUrl = resources.nextElement();
            File directory = new File(resourceUrl.getFile());
            if (directory.exists() && directory.isDirectory()) {
                addMatchingFiles(directory, extension, matchingFiles, packageName);
            }
        }

        return matchingFiles;
    }

    /**
     * Recursively add files with the given extension to the map of package+file
     * names and files.
     *
     * @param directory     The directory to search.
     * @param extension     The extension to match.
     * @param matchingFiles The map of package+file names and corresponding files.
     * @param packageName   The package name for constructing the package+file
     *                      names.
     */
    private static void addMatchingFiles(File directory, String extension, Map<String, File> matchingFiles,
            String packageName) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(extension)) {
                String packageFileName = packageName + "." + getRelativeFilePath(directory, file, extension);
                matchingFiles.put(packageFileName, file);
            } else if (file.isDirectory()) {
                addMatchingFiles(file, extension, matchingFiles, packageName);
            }
        }
    }

    /**
     * Get the relative file path from the base directory to the file.
     *
     * @param baseDirectory The base directory.
     * @param file          The file.
     * @return The relative file path.
     */
    private static String getRelativeFilePath(File baseDirectory, File file, String extension) {
        String basePath = baseDirectory.getAbsolutePath();
        String filePath = file.getAbsolutePath();
        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length() + 1, filePath.length() - extension.length());
        }
        return filePath;
    }
}
