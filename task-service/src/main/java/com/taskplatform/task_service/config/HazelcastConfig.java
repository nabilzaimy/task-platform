package com.taskplatform.task_service.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.EvictionPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {

    @Bean
    public Config hazelcastConfiguration() {
        Config config = new Config();
        config.setInstanceName("task-service-hazelcast");

        // Define a cache map named "projects"
        MapConfig projectMapConfig = new MapConfig("projects")
                .setTimeToLiveSeconds(300) // Cache entries expire after 5 minutes
                .setEvictionConfig(new EvictionConfig()
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                        .setSize(1000));

        config.addMapConfig(projectMapConfig);
        return config;
    }
}