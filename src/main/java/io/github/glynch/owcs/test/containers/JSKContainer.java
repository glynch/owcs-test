package io.github.glynch.owcs.test.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class JSKContainer extends GenericContainer<JSKContainer> {

    private static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("grahamlynch/jsk");
    private static final int DEFAULT_PORT = 7003;
    private static final String DEFAULT_PATH = "/sites";
    private final int port;
    private final String path;

    public JSKContainer(String image) {
        this(DockerImageName.parse(image));
    }

    public JSKContainer(DockerImageName dockerImageName) {
        this(dockerImageName, DEFAULT_PATH, DEFAULT_PORT);
    }

    public JSKContainer(String image, String path, int port) {
        this(DockerImageName.parse(image), path, port);
    }

    public JSKContainer(DockerImageName dockerImageName, String path, int port) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DOCKER_IMAGE_NAME);
        this.port = port;
        this.path = path;
        withExposedPorts(port)
                .waitingFor(Wait.forHttp(getPath() + "/HelloCS").forStatusCode(200));
    }

    public String getEndpoint() {
        return "http://" + getHost() + ":" + getMappedPort(port);
    }

    public String getPath() {
        return path;
    }

    public String getBaseUrl() {
        return getEndpoint() + getPath();
    }

    public String getContentServer() {
        return getBaseUrl() + "/ContentServer";
    }

    public String getSatelliteServer() {
        return getBaseUrl() + "/Satellite";
    }

    public String getCatalogManager() {
        return getBaseUrl() + "/CatalogManager";
    }

    public String getHelloCS() {
        return getBaseUrl() + "/HelloCS";
    }

    public String getRestUrl() {
        return getBaseUrl() + "/REST";
    }

}
