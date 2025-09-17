package io.github.glynch.owcs.test.okhttp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

import okhttp3.mockwebserver.MockResponse;

public class AuthenticatedMockContentServer extends MockContentServer {

    public AuthenticatedMockContentServer() {

    }

    public void enqueueMultiTicket() {
        getServer().enqueue(new MockResponse().setResponseCode(200)
                .setBody("multi-ST-1-l5FXR0vjW1amNsULsH5K-cas-localhost-1"));
    }

    public void enqueueEncryptedToken() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("encrypted_token.json").getFile());
        try {
            String encryptedToken = FileUtils.readFileToString(file, "UTF-8");
            getServer().enqueue(new MockResponse().setBody(encryptedToken));
        } catch (IOException e) {

        }
    }

    @Override
    public void enqueue(MockResponse response) {
        enqueueMultiTicket();
        enqueueEncryptedToken();
        super.enqueue(response);
    }

    @Override
    public void enqueue(String response) {
        enqueueMultiTicket();
        enqueueEncryptedToken();
        super.enqueue(response);
    }

    @Override
    public void enqueue(Path path) throws IOException {
        enqueueMultiTicket();
        enqueueEncryptedToken();
        super.enqueue(path);
    }

}
