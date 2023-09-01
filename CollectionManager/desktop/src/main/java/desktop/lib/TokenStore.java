package desktop.lib;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import lombok.Cleanup;

public class TokenStore {
    private static Path getTokenPath() {
        var userDirectory = System.getProperty("user.home");
        return Path.of(userDirectory, ".collection-manager", ".token");
    }

    public static void saveToken(String token) throws IOException {
        var tokenPath = getTokenPath();
        var tokenFile = tokenPath.toFile();
        tokenFile.getParentFile().mkdirs();
        tokenFile.createNewFile();
        @Cleanup
        var tokenWriter = new FileWriter(tokenFile);
        tokenWriter.write(token);
    }

    public static Optional<String> getToken() throws IOException {
        var tokenPath = getTokenPath();
        var tokenFile = tokenPath.toFile();
        if (!tokenFile.exists()) {
            return Optional.empty();
        }
        @Cleanup
        var tokenReader = new java.io.FileReader(tokenFile);
        var token = new StringBuilder();
        int c;
        while ((c = tokenReader.read()) != -1) {
            token.append((char) c);
        }
        return Optional.of(token.toString());
    }

    public static void deleteToken() throws IOException {
        var tokenPath = getTokenPath();
        var tokenFile = tokenPath.toFile();
        tokenFile.delete();
    }
}
