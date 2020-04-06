//package com.tom.autobetter.dynamodb;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicSessionCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.document.DynamoDB;
//import com.amazonaws.services.dynamodbv2.document.Item;
//import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
//import com.amazonaws.services.dynamodbv2.document.Table;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//
//public class AddBet {
//
//
//    public void add(Integer raceId) {
//
//        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(
//                session_creds.getAccessKeyId(),
//                session_creds.getSecretAccessKey(),
//                session_creds.getSessionToken());
//
//        AmazonDynamoDBClient amazonDynamoDBClient = AmazonDynamoDBClient.builder()
//                .withRegion(Regions.US_WEST_2)
//                .withCredentials(new AWSStaticCredentialsProvider(creds))
//                .build();
//
//        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//        DynamoDB dynamoDB = new DynamoDB(client);
//
//        Table table = dynamoDB.getTable("autobetter");
//
//        Item item = new Item()
//                .withPrimaryKey("RACE_ID", raceId);
//
//// Write the item to the table
//        PutItemOutcome outcome = table.putItem(item);
//    }
//}