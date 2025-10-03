//package com.example.elasticsearch.config;
//
//import org.apache.http.HttpHost;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient client() {
//        return new RestHighLevelClient(
//                RestClient.builder(HttpHost.create("http://localhost:9200"))
//        );
//    }
//
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestHighLevelClient client) {
//        return new ElasticsearchRestTemplate(client);
//    }
//}
