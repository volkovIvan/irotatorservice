package com.ivanvolkov.imagerotatorservice.data;

import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

public class TaskStateEnumDynamoConverter implements AttributeConverter<TaskState> {
    @Override
    public AttributeValue transformFrom(TaskState input) {
        return AttributeValue.fromS(input.name());
    }

    @Override
    public TaskState transformTo(AttributeValue input) {
        return TaskState.valueOf(input.s());
    }

    @Override
    public EnhancedType<TaskState> type() {
        return EnhancedType.of(TaskState.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}
