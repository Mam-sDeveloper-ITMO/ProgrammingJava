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
     * @return A map of directories, where each directory is represented as a nested
     *         map of package+file names and corresponding files.
     * @throws IOException If an I/O error occurs.
     */
    public static Map<String, Object> findFilesWithExtension(String packageName, String extension) throws IOException {
        Map<String, Object> directories = new HashMap<>();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resourceUrl = resources.nextElement();
            File directory = new File(resourceUrl.getFile());
            if (directory.exists() && directory.isDirectory()) {
                addMatchingFiles(directory, extension, directories, packageName);
            }
        }

        return directories;
    }

    /**
     * Recursively add files with the given extension to the map of directories.
     *
     * @param directory      The directory to search.
     * @param extension      The extension to match.
     * @param directories    The map of directories.
     * @param currentPackage The current package name for constructing the
     *                       package+file names.
     */
    private static void addMatchingFiles(File directory, String extension, Map<String, Object> directories,
            String currentPackage) {
        File[] files = directory.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(extension)) {
                String packageFileName = currentPackage + "." + getRelativeFilePath(directory, file);
                directories.put(packageFileName, file);
            } else if (file.isDirectory()) {
                String subPackageName = currentPackage + "." + file.getName();
                Map<String, Object> subDirectory = new HashMap<>();
                directories.put(file.getName(), subDirectory);
                addMatchingFiles(file, extension, subDirectory, subPackageName);
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
    private static String getRelativeFilePath(File baseDirectory, File file) {
        String basePath = baseDirectory.getAbsolutePath();
        String filePath = file.getAbsolutePath();
        if (filePath.startsWith(basePath)) {
            return filePath.substring(basePath.length() + 1);
        }
        return filePath;
    }
}
