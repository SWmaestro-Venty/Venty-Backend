package com.swm.ventybackend.content_dynamo;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Setter
public class ContentDynamoRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public ContentDynamo saveContent(ContentDynamo contentDynamo) {
        dynamoDBMapper.save(contentDynamo);
        return contentDynamo;
    }

    public ContentDynamo getContentById(String contentId) {
        return dynamoDBMapper.load(ContentDynamo.class, contentId);
    }

    public String deleteContentById(String contentId) {
        dynamoDBMapper.delete(dynamoDBMapper.load(ContentDynamo.class, contentId));
        return "Content Id : " + contentId + " Deleted!";
    }

    public String updateContent(String contentId, ContentDynamo contentDynamo) {
        dynamoDBMapper.save(contentDynamo,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("contentId",
                                new ExpectedAttributeValue(
                                        new AttributeValue().withS(contentId)
                                )));
        return "Content Id : " + contentId + " Updated!";
    }


    public List<ContentDynamo> getContent() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withLimit(10);
        List<ContentDynamo> scannedContentListDynamo = dynamoDBMapper.scan(ContentDynamo.class, scanExpression);
        List<ContentDynamo> resultContentListDynamo = new ArrayList<>();
        scannedContentListDynamo.forEach(scannedContentDynamo -> {
            if (!scannedContentDynamo.getContentId().startsWith("thumbnails_")) {
                resultContentListDynamo.add(scannedContentDynamo);
            }
        });
        return resultContentListDynamo;
    }

}
