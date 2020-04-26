package com.tom.autobetter.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tom.autobetter.util.CommonConstants.*;

@Configuration
public class DynamoDBConfigProd {

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials((ACCESS_KEY.equals("") ? System.getenv("ACCESS_KEY") : ACCESS_KEY), (SECRET_KEY.equals("") ? System.getenv("SECRET_KEY") : SECRET_KEY));
    }
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(amazonAWSCredentialsProvider())
                .withRegion(Regions.US_WEST_2)
                .build();
    }
    public AWSCredentialsProvider amazonAWSCredentialsProvider() {
        return new AWSStaticCredentialsProvider(amazonAWSCredentials());
    }
}