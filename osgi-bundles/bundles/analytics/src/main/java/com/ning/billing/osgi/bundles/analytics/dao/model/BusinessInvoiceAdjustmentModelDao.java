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

package com.ning.billing.osgi.bundles.analytics.dao.model;

import javax.annotation.Nullable;

import com.ning.billing.account.api.Account;
import com.ning.billing.catalog.api.Plan;
import com.ning.billing.catalog.api.PlanPhase;
import com.ning.billing.entitlement.api.user.SubscriptionBundle;
import com.ning.billing.invoice.api.Invoice;
import com.ning.billing.invoice.api.InvoiceItem;
import com.ning.billing.util.audit.AuditLog;

public class BusinessInvoiceAdjustmentModelDao extends BusinessInvoiceItemBaseModelDao {

    public BusinessInvoiceAdjustmentModelDao() { /* When reading from the database */ }

    public BusinessInvoiceAdjustmentModelDao(final Account account,
                                             final Long accountRecordId,
                                             final Invoice invoice,
                                             final InvoiceItem invoiceItem,
                                             final Boolean revenueRecognizable,
                                             final Long invoiceItemRecordId,
                                             final Long secondInvoiceItemRecordId,
                                             @Nullable final SubscriptionBundle bundle,
                                             @Nullable final Plan plan,
                                             @Nullable final PlanPhase planPhase,
                                             final AuditLog creationAuditLog,
                                             final Long tenantRecordId,
                                             @Nullable final ReportGroup reportGroup) {
        super(account,
              accountRecordId,
              invoice,
              invoiceItem,
              revenueRecognizable,
              invoiceItemRecordId,
              secondInvoiceItemRecordId,
              bundle,
              plan,
              planPhase,
              creationAuditLog,
              tenantRecordId,
              reportGroup);
    }

    @Override
    public String getTableName() {
        return INVOICE_ADJUSTMENTS_TABLE_NAME;
    }
}
