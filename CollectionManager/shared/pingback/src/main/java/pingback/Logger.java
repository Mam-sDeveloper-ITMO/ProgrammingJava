package pingback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Logger provides logging to PingBack.
 * {@link https://deta.space/discovery/r/xqsncnys6yzsgzjd}
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Logger {
    /**
     * Logger instances
     */
    @Getter
    private static Map<String, Logger> loggers = new HashMap<>();

    /**
     * Deta PingBack app url
     */
    @Getter
    private final String appUrl;

    /**
     * Deta PingBack api key
     */
    private final String apiKey;

    /**
     * Deta PingBack project name
     */
    @Getter
    private final String project;

    /**
     * Logger name. Used for global accessing logger instance.
     */
    @Getter
    private final String name;

    /**
     * Logging mode (console, pingback, both)
     */
    @Getter
    @Setter
    private LogMode logMode = LogMode.BOTH;

    /**
     * Simple console logging with System.out.println
     *
     * @param channel     channel name
     * @param title       log title
     * @param description log description
     * @param level       log level
     */
    private void consoleLog(String channel, String title, String description, Level level) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        String log = "[%s] [%s - %s] [%s] %s: %s".formatted(formattedDateTime, project, channel, level.name(), title, description);
        System.out.println(log);
    }

    /**
     * Log message to PingBack.
     *
     * @param channel     channel name
     * @param title       log title
     * @param description log description
     * @param level       log level
     */
    private HttpResponse pingbackLog(String channel, String title, String description, Level level) {
        Map<String, Object> data = new HashMap<>();
        data.put("project", this.project);
        data.put("channel", channel);
        data.put("name", level.name().toLowerCase());
        data.put("title", title);
        data.put("description", description);
        data.put("icon", level.getIcon());

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(appUrl + "/api/v1/events");
        try {
            StringEntity params = new StringEntity(new Gson().toJson(data));
            httpPost.addHeader("content-type", "application/json");
            httpPost.setHeader("X-API-KEY", this.apiKey);
            httpPost.setEntity(params);
            HttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (UnsupportedEncodingException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Log message to PingBack.
     *
     * @param project     project name
     * @param channel     channel name
     * @param title       log title
     * @param description log description
     * @param level       log level
     */
    public void log(String channel, String title, String description, Level level) {
        if (logMode == LogMode.CONSOLE || logMode == LogMode.BOTH) {
            consoleLog(channel, title, description, level);
        }
        if (logMode == LogMode.PINGBACK || logMode == LogMode.BOTH) {
            pingbackLog(channel, title, description, level);
        }
    }

    /**
     * Log message to default channel
     *
     * @param title       log title
     * @param description log description
     * @param level       log level
     */
    public void log(String title, String description, Level level) {
        log("default", title, description, level);
    }

    /**
     * Get logger instance.
     *
     * @param name logger name
     * @return logger instance
     */
    public static Logger getLogger(String name) {
        return loggers.get(name);
    }

    /**
     * Get logger instance if exists, otherwise create new logger instance.
     *
     *
     */
    public static Logger getLogger(String appUrl, String apiKey, String project, String name) {
        if (loggers.containsKey(name)) {
            return loggers.get(name);
        } else {
            Logger logger = new Logger(appUrl, apiKey, project, name);
            loggers.put(name, logger);
            return logger;
        }
    }
}
