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

package com.ning.billing.analytics.dao;

import java.util.List;

import com.ning.billing.analytics.api.TimeSeriesData;
import com.ning.billing.analytics.model.BusinessAccountModelDao;
import com.ning.billing.analytics.model.BusinessAccountTagModelDao;
import com.ning.billing.analytics.model.BusinessInvoiceItemModelDao;
import com.ning.billing.analytics.model.BusinessInvoiceModelDao;
import com.ning.billing.analytics.model.BusinessInvoicePaymentModelDao;
import com.ning.billing.analytics.model.BusinessOverdueStatusModelDao;
import com.ning.billing.analytics.model.BusinessSubscriptionTransitionModelDao;
import com.ning.billing.util.callcontext.InternalTenantContext;

public interface AnalyticsDao {

    TimeSeriesData getAccountsCreatedOverTime(InternalTenantContext context);

    TimeSeriesData getSubscriptionsCreatedOverTime(String productType, String slug, InternalTenantContext context);

    BusinessAccountModelDao getAccountByKey(String accountKey, InternalTenantContext context);

    List<BusinessSubscriptionTransitionModelDao> getTransitionsByKey(String externalKey, InternalTenantContext context);

    List<BusinessSubscriptionTransitionModelDao> getTransitionsForAccount(String accountKey, InternalTenantContext context);

    List<BusinessInvoiceModelDao> getInvoicesByKey(String accountKey, InternalTenantContext context);

    List<BusinessInvoiceItemModelDao> getInvoiceItemsForInvoice(String invoiceId, InternalTenantContext context);

    List<BusinessInvoicePaymentModelDao> getInvoicePaymentsForAccountByKey(String accountKey, InternalTenantContext context);

    List<BusinessOverdueStatusModelDao> getOverdueStatusesForBundleByKey(String externalKey, InternalTenantContext context);

    List<BusinessAccountTagModelDao> getTagsForAccount(String accountKey, InternalTenantContext context);
}
