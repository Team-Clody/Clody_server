package com.clody.clodybatch.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@ConditionalOnMissingBean(value = DefaultBatchConfiguration.class, annotation = EnableBatchProcessing.class)
public class BatchAutoConfiguration {
}
