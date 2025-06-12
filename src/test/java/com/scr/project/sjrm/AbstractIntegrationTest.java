package com.scr.project.sjrm;

import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
@ActiveProfiles("test")
public abstract class AbstractIntegrationTest {

    private static final Network network = Network.newNetwork();

    protected static final ConfluentKafkaContainer kafkaContainer = new ConfluentKafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.8.2"))
            .withNetwork(network)
            .withNetworkAliases("kafka")
            .withEnv("KAFKA_LOG4J_ROOT_LOGLEVEL", "DEBUG")
            .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");

    protected static final GenericContainer<?> schemaRegistryContainer = new GenericContainer<>(
            DockerImageName.parse("confluentinc/cp-schema-registry:7.8.2"))
            .withNetwork(network)
            .withExposedPorts(8081)
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:9093")
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schema-registry")
            .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:8081")
            .withEnv("SCHEMA_REGISTRY_LOG4J_ROOT_LOGLEVEL", "DEBUG")
            .waitingFor(Wait.forHttp("/subjects").forStatusCode(200));

    static {
        kafkaContainer.start();
        schemaRegistryContainer.start();
    }

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.properties.schema.registry.url", () ->
                "http://" + schemaRegistryContainer.getHost() + ":" + schemaRegistryContainer.getMappedPort(8081));
    }
}