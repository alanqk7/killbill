/*
 * Copyright 2010-2013 Ning, Inc.
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

package com.ning.billing.analytics.model;

import java.util.UUID;

public class BusinessInvoicePaymentFieldModelDao extends BusinessFieldModelDao {

    private final UUID paymentId;

    public BusinessInvoicePaymentFieldModelDao(final UUID paymentId, final String name, final String value) {
        super(paymentId, name, value);
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BusinessPaymentField");
        sb.append("{paymentId='").append(paymentId).append('\'');
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", value='").append(getValue()).append('\'');
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

        final BusinessInvoicePaymentFieldModelDao that = (BusinessInvoicePaymentFieldModelDao) o;

        if (paymentId != null ? !paymentId.equals(that.paymentId) : that.paymentId != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }
}
