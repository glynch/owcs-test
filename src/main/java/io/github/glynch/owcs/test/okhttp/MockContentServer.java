package io.github.glynch.owcs.test.okhttp;

import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class MockContentServer {

    protected static final String DEFAULT_PATH = "/sites";
    protected static final int DEFAULT_PORT = 7003;
    private final MockWebServer server;

    public MockContentServer() {
        this.server = new MockWebServer();
    }

    public MockWebServer getServer() {
        return server;
    }

    public String url(String path) {
        return server.url(path).toString();
    }

    public void start() throws IOException {
        start(DEFAULT_PORT);
    }

    public void start(int port) throws IOException {
        server.start(port);
    }

    public void shutdown() throws IOException {
        server.shutdown();
    }

    public void enqueue(MockResponse response) {
        server.enqueue(response);
    }

    public void enqueue(String response) {
        enqueue(new MockResponse().setBody(response).setResponseCode(200));
    }

    public void enqueue(Path path) throws IOException {
        String body = FileUtils.readFileToString(path.toFile(), "UTF-8");
        server.enqueue(new MockResponse().setBody(body).setResponseCode(200));
    }

}
