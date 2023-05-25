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
     * @param packagePath The package to search.
     * @param extension   The extension to match.
     * @return A map of directories, where each directory is represented as a nested
     *         map like {subdir1.subdir2: file}.
     * @throws IOException If an I/O error occurs.
     */
    public static Map<String, Object> findFilesWithExtension(String packagePath, String extension) throws IOException {
        Map<String, Object> files = new HashMap<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packagePath.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        if (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            files.putAll(_findFilesWithExtension(packagePath, directory, extension));
        }
        return files;
    }

    /**
     * Recursively find all files with the given extension in the given directory.
     *
     * @param packageName The package name. Used as keys prefix.
     * @param directory   The directory to search.
     * @param extension   The extension to match.
     * @return A map of directories, where each directory is represented as a nested
     */
    private static Map<String, Object> _findFilesWithExtension(String packageName, File directory, String extension) {
        Map<String, Object> files = new HashMap<>();
        File[] filesList = directory.listFiles();
        for (File file : filesList) {
            if (file.isFile()) {
                if (file.getName().endsWith(extension)) {
                    files.put(file.getName().replace(extension, ""), file);
                }
            } else if (file.isDirectory()) {
                Map<String, Object> subFiles = _findFilesWithExtension(file.getName(), file, extension);
                if (!subFiles.isEmpty()) {
                    files.put(file.getName(), subFiles);
                }
            }
        }
        return files;
    }
}
