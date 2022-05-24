package br.com.tokunaga.adapter.datastore.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CassandraConfig {

    @Value("${desafio2.datastore.hostname}")
    private String hostname;

    @Value("${desafio2.datastore.port}")
    private Integer port;

    @Value("${desafio2.datastore.keyspace-name}")
    private String keyspaceName;

    @Value("${desafio2.datastore.local-datacenter}")
    private String localDatacenter;

    @Bean
    public CqlSession session() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(hostname, port))
                .withKeyspace(keyspaceName)
                .withLocalDatacenter(localDatacenter)
                .build();
    }
}