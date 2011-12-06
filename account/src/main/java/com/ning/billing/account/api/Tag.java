/*
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.ning.billing.account.api;

import org.joda.time.DateTime;

import java.util.UUID;

public class Tag extends EntityBase {
    private UUID tagDescriptionId;
    private String description;
    private UUID objectId;
    private String objectType;
    private String addedBy;
    private DateTime dateAdded;

    public Tag(UUID id, UUID tagDescriptionId, String description, UUID objectId, String objectType, String addedBy, DateTime dateAdded) {
        super(id);
        this.tagDescriptionId = tagDescriptionId;
        this.description = description;
        this.objectId = objectId;
        this.objectType = objectType;
        this.addedBy = addedBy;
        this.dateAdded = dateAdded;
    }

    public UUID getTagDescriptionId() {
        return tagDescriptionId;
    }

    public String getDescription() {
        return description;
    }

    public UUID getObjectId() {
        return objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public DateTime getDateAdded() {
        return dateAdded;
    }
}