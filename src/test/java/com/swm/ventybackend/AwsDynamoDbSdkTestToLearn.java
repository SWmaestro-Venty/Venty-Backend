package com.swm.ventybackend;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class AwsDynamoDbSdkTestToLearn {
    private AmazonDynamoDB amazonDynamoDb;

    @BeforeEach
    void setup() {
        AWSCredentials awsCredentials = new BasicAWSCredentials("key1", "key2");
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "ap-northeast-2");

        amazonDynamoDb = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(endpointConfiguration).build();;
    }

    @Test
    @Disabled
    void createTable_ValidInput_TableHasBeenCreated() {
        CreateTableRequest createTableRequest = (new CreateTableRequest())
                .withAttributeDefinitions(
                        new AttributeDefinition("id", ScalarAttributeType.S),
                        new AttributeDefinition("mentionId", ScalarAttributeType.N),
                        new AttributeDefinition("createdAt", ScalarAttributeType.S)
                ).withTableName("Comment").withKeySchema(
                        new KeySchemaElement("id", KeyType.HASH)
                ).withGlobalSecondaryIndexes(
                        (new GlobalSecondaryIndex())
                                .withIndexName("byMentionId")
                                .withKeySchema(
                                        new KeySchemaElement("mentionId", KeyType.HASH),
                                        new KeySchemaElement("createdAt", KeyType.RANGE))
                                .withProjection(
                                        (new Projection()).withProjectionType(ProjectionType.ALL))
                                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L))
                ).withProvisionedThroughput(
                        new ProvisionedThroughput(1L, 1L)
                );

        boolean hasTableBeenCreated = TableUtils.createTableIfNotExists(amazonDynamoDb, createTableRequest);
        then(hasTableBeenCreated).isTrue();
    }

}