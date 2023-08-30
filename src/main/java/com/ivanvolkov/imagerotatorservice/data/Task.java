package com.ivanvolkov.imagerotatorservice.data;

import lombok.*;;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@DynamoDbBean
public class Task {

    @Getter(onMethod = @__({@DynamoDbPartitionKey}))
    private String id;

    @Getter(onMethod = @__({@DynamoDbAttribute("fileName")}))
    private String fileName;

    @Getter(onMethod = @__({@DynamoDbAttribute("originalFilePath")}))
    private String originalFilePath;

    @Getter(onMethod = @__({@DynamoDbAttribute("processedFilePath")}))
    private String processedFilePath;

    @Getter(onMethod = @__({@DynamoDbAttribute("state"), @DynamoDbConvertedBy(TaskStateEnumDynamoConverter.class)}))
    private TaskState state;
}
