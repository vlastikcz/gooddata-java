/*
 * Copyright (C) 2004-2017, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gooddata.gdc.LinkEntries;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * MAQL DDL links (result from POSTing to /ldm/manage2).
 * Deserialization only.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class MaqlDdlLinks extends LinkEntries {

    private static final String TASKS_STATUS = "tasks-status";

    @JsonCreator
    private MaqlDdlLinks(@JsonProperty("entries") List<LinkEntry> entries) {
        super(entries);
    }

    /**
     * @return status URI string
     * @deprecated use {@link #getStatusUri()} instead
     */
    @Deprecated
    public String getStatusLink() {
        return getStatusUri();
    }

    public String getStatusUri() {
        for (LinkEntry linkEntry : getEntries()) {
            if (TASKS_STATUS.equals(linkEntry.getCategory())) {
                return linkEntry.getUri();
            }
        }
        return null;
    }
}
