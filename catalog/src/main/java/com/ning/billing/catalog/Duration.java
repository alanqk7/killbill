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

package com.ning.billing.catalog;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.ning.billing.catalog.api.IDuration;
import com.ning.billing.catalog.api.TimeUnit;
import com.ning.billing.util.config.ValidatingConfig;
import com.ning.billing.util.config.ValidationErrors;

@XmlAccessorType(XmlAccessType.NONE)
public class Duration extends ValidatingConfig<Catalog> implements IDuration {
	@XmlElement(required=true)
    private TimeUnit unit;

	@XmlElement(required=false)
    private Integer number = -1;
	
    /* (non-Javadoc)
	 * @see com.ning.billing.catalog.IDuration#getUnit()
	 */
    @Override
	public TimeUnit getUnit() {
        return unit;
    }

    /* (non-Javadoc)
	 * @see com.ning.billing.catalog.IDuration#getLength()
	 */
    @Override
	public int getNumber() {
        return number;
    }

	@Override
	public ValidationErrors validate(Catalog catalog, ValidationErrors errors) {
		//TODO MDW - Validation TimeUnit UNLIMITED iff number == -1
		return errors;
	}

	protected Duration setUnit(TimeUnit unit) {
		this.unit = unit;
		return this;
	}

	protected Duration setNumber(Integer number) {
		this.number = number;
		return this;
	}
	
	
}