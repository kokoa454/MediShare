package com.medishare.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@ConditionalOnProperty(name = "search.engine", havingValue = "elasticsearch")
@EnableElasticsearchRepositories(basePackages = "com.medishare.repository.elasticsearch")
public class ElasticsearchConfig {}
