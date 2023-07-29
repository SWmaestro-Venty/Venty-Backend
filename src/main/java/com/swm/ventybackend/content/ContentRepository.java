package com.swm.ventybackend.content;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContentRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public Content saveContent(Content content) {
        dynamoDBMapper.save(content);
        return content;
    }

    public Content getContentById(String contentId) {
        return dynamoDBMapper.load(Content.class, contentId);
    }

    public String deleteContentById(String contentId) {
        dynamoDBMapper.delete(dynamoDBMapper.load(Content.class, contentId));
        return "Content Id : " + contentId + " Deleted!";
    }

    public String updateContent(String contentId, Content content) {
        dynamoDBMapper.save(content,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("contentId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(contentId)
                                )));
        return "Content Id : " + contentId + " Updated!";
    }
}
