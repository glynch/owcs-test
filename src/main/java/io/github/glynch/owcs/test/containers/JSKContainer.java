package io.github.glynch.owcs.test.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * A container for the WebCenter Sites JumpstartKit
 * 
 * @see <a href="https://java.testcontainers.org/">Test containers for Java</a>
 */
public class JSKContainer extends GenericContainer<JSKContainer> {

    public static final DockerImageName DOCKER_IMAGE_NAME = DockerImageName.parse("grahamlynch/jsk");
    private static final int DEFAULT_PORT = 7003;
    private static final String DEFAULT_PATH = "/sites";
    private static final String DEFAULT_CAS_PATH = "/cas";
    private final int port;
    private final String path;
    private final String casPath;

    public JSKContainer(String image) {
        this(DockerImageName.parse(image));
    }

    public JSKContainer(DockerImageName dockerImageName) {
        this(dockerImageName, DEFAULT_PATH, DEFAULT_CAS_PATH, DEFAULT_PORT);
    }

    public JSKContainer(String image, String path, String casPath, int port) {
        this(DockerImageName.parse(image), path, casPath, port);
    }

    public JSKContainer(DockerImageName dockerImageName, String path, String casPath, int port) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DOCKER_IMAGE_NAME);
        this.port = port;
        this.path = path;
        this.casPath = casPath;
        withExposedPorts(port)
                .waitingFor(Wait.forHttp(getPath() + "/HelloCS").forStatusCode(200));
    }

    public String getEndpoint() {
        return "http://" + getHost() + ":" + getMappedPort(port);
    }

    public String getPath() {
        return path;
    }

    public String getCasPath() {
        return casPath;
    }

    public String getCasUrl() {
        return getEndpoint() + getCasPath();
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
