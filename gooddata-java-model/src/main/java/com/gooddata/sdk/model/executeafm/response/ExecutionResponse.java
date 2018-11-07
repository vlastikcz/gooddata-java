/*
 * Copyright (C) 2004-2019, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.sdk.model.executeafm.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.gooddata.sdk.model.executeafm.Execution;
import com.gooddata.sdk.model.executeafm.result.ExecutionResult;
import com.gooddata.util.GoodDataToStringBuilder;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.gooddata.util.Validate.notEmpty;
import static com.gooddata.util.Validate.notNull;
import static com.gooddata.util.Validate.notNullState;
import static org.apache.commons.lang3.ArrayUtils.toObject;

/**
 * Represents response on {@link Execution} request.
 * Provides the dimensions with headers and the (polling) uri to {@link ExecutionResult}
 * (so called dataResult).
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("executionResponse")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutionResponse {

    private static final String EXECUTION_RESULT_LINK = "executionResult";

    private final List<ResultDimension> dimensions;
    private final Map<String, String> links;
    private final List<SimilarVisualizations> similarVisualizations;

    /**
     * Creates new instance of given dimensions and execution result uri.
     * @param dimensions dimensions
     * @param executionResultUri execution result uri
     */
    public ExecutionResponse(final List<ResultDimension> dimensions, final String executionResultUri, final List<SimilarVisualizations> similarVisualizations) {
        this(dimensions, new LinkedHashMap<>(), similarVisualizations);
        links.put(EXECUTION_RESULT_LINK, notEmpty(executionResultUri, "executionResultUri"));
    }

    @JsonCreator
    private ExecutionResponse(@JsonProperty("dimensions") final List<ResultDimension> dimensions,
                              @JsonProperty("links") final Map<String, String> links,
                              @JsonProperty("similarVisualizations")  List<SimilarVisualizations> similarVisualizations) {
        this.dimensions = notNull(dimensions, "dimensions");
        this.links = notNull(links, "links");
        this.similarVisualizations = similarVisualizations;
    }

    /**
     * List of dimensions describing the result.
     * @return dimensions
     */
    public List<ResultDimension> getDimensions() {
        return dimensions;
    }

    /**
     * Map of response's links.
     * @return links
     */
    public Map<String, String> getLinks() {
        return links;
    }

    public List<SimilarVisualizations> getSimilarVisualizations() {
        return similarVisualizations;
    }

    /**
     * Uri referencing the data result location.
     * @return execution result uri or throws exception in case the link doesn't exist
     */
    @JsonIgnore
    public String getExecutionResultUri() {
        return notNullState(notNullState(links, "links").get(EXECUTION_RESULT_LINK), EXECUTION_RESULT_LINK);
    }

    @Override
    public String toString() {
        return GoodDataToStringBuilder.defaultToString(this);
    }

    public static final class SimilarVisualizations {
        private BigDecimal score;
        private String uri;

        @JsonCreator
        public SimilarVisualizations(@JsonProperty("score") BigDecimal score, @JsonProperty("uri") String uri) {
            this.score = score;
            this.uri = uri;
        }

        public BigDecimal getScore() {
            return score;
        }

        public void setScore(BigDecimal score) {
            this.score = score;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        @Override
        public String toString() {
            return "SimilarVisualizations{" +
                    "score='" + score + '\'' +
                    ", uri='" + uri + '\'' +
                    '}';
        }
    }

}
