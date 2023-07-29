package com.swm.ventybackend.content;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Setter
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


    public List<Content> getContent() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withLimit(10);
        List<Content> scannedContentList = dynamoDBMapper.scan(Content.class, scanExpression);
        List<Content> resultContentList = new ArrayList<>();
        scannedContentList.forEach(scannedContent -> {
            if (!scannedContent.getContentId().startsWith("thumbnails_")) {
                resultContentList.add(scannedContent);
            }
        });
        return resultContentList;
    }

}
