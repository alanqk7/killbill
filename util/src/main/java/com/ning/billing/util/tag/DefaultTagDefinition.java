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

package com.ning.billing.util.tag;

import java.util.List;
import java.util.UUID;

import com.ning.billing.ObjectType;
import com.ning.billing.util.entity.EntityBase;
import com.ning.billing.util.tag.dao.TagDefinitionModelDao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

public class DefaultTagDefinition extends EntityBase implements TagDefinition {

    private final String name;
    private final String description;
    private final Boolean controlTag;
    private final List<ObjectType> applicableObjectTypes;

    public DefaultTagDefinition(final TagDefinitionModelDao tagDefinitionModelDao, final boolean isControlTag) {
        this(tagDefinitionModelDao.getId(), tagDefinitionModelDao.getName(), tagDefinitionModelDao.getDescription(), isControlTag);
    }

    public DefaultTagDefinition(final String name, final String description, final Boolean isControlTag) {
        this(UUID.randomUUID(), name, description, isControlTag);
    }

    public DefaultTagDefinition(final UUID id, final String name, final String description, final Boolean isControlTag) {
        this(id, name, description, isControlTag, ImmutableList.<ObjectType>copyOf(ObjectType.values()));
    }

    public DefaultTagDefinition(final ControlTagType controlTag) {
        this(controlTag.getId(), controlTag.toString(), controlTag.getDescription(), true, controlTag.getApplicableObjectTypes());
    }

    @JsonCreator
    public DefaultTagDefinition(@JsonProperty("id") final UUID id,
                                @JsonProperty("name") final String name,
                                @JsonProperty("description") final String description,
                                @JsonProperty("controlTag") final Boolean controlTag,
                                @JsonProperty("applicableObjectTypes") final List<ObjectType> applicableObjectTypes) {
        super(id);
        this.name = name;
        this.description = description;
        this.controlTag = controlTag;
        this.applicableObjectTypes = applicableObjectTypes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Boolean isControlTag() {
        return controlTag;
    }

    @Override
    public List<ObjectType> getApplicableObjectTypes() {
        return applicableObjectTypes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DefaultTagDefinition");
        sb.append("{name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", controlTag=").append(controlTag);
        sb.append(", applicableObjectTypes=").append(applicableObjectTypes);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DefaultTagDefinition that = (DefaultTagDefinition) o;

        if (applicableObjectTypes != null ? !applicableObjectTypes.equals(that.applicableObjectTypes) : that.applicableObjectTypes != null) {
            return false;
        }
        if (controlTag != null ? !controlTag.equals(that.controlTag) : that.controlTag != null) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (controlTag != null ? controlTag.hashCode() : 0);
        result = 31 * result + (applicableObjectTypes != null ? applicableObjectTypes.hashCode() : 0);
        return result;
    }
}
