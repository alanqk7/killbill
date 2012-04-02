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

package com.ning.billing.invoice;

import org.skife.config.ConfigurationObjectFactory;
import org.skife.jdbi.v2.IDBI;

import com.google.inject.AbstractModule;
import com.ning.billing.account.api.AccountUserApi;
import com.ning.billing.catalog.glue.CatalogModule;
import com.ning.billing.dbi.DBIProvider;
import com.ning.billing.dbi.DbiConfig;
import com.ning.billing.dbi.MysqlTestingHelper;
import com.ning.billing.entitlement.glue.EntitlementModule;
import com.ning.billing.invoice.glue.InvoiceModule;
import com.ning.billing.mock.BrainDeadProxyFactory;
import com.ning.billing.util.clock.Clock;
import com.ning.billing.util.clock.ClockMock;
import com.ning.billing.util.glue.BusModule;
import com.ning.billing.util.glue.GlobalLockerModule;
import com.ning.billing.util.glue.NotificationQueueModule;


public class MockModule extends AbstractModule {


    public static final String PLUGIN_NAME = "yoyo";

    @Override
    protected void configure() {

        bind(Clock.class).to(ClockMock.class).asEagerSingleton();
        bind(ClockMock.class).asEagerSingleton();

        final MysqlTestingHelper helper = new MysqlTestingHelper();
        bind(MysqlTestingHelper.class).toInstance(helper);
        if (helper.isUsingLocalInstance()) {
            bind(IDBI.class).toProvider(DBIProvider.class).asEagerSingleton();
            final DbiConfig config = new ConfigurationObjectFactory(System.getProperties()).build(DbiConfig.class);
            bind(DbiConfig.class).toInstance(config);
        } else {
            final IDBI dbi = helper.getDBI(); 
            bind(IDBI.class).toInstance(dbi);
        }

        install(new GlobalLockerModule());
        install(new NotificationQueueModule());
//        install(new AccountModule());
        bind(AccountUserApi.class).toInstance(BrainDeadProxyFactory.createBrainDeadProxyFor(AccountUserApi.class));

        installEntitlementModule();
        install(new CatalogModule());
        install(new BusModule());
        installInvoiceModule();

    }
    
    protected void installEntitlementModule() {
    	install(new EntitlementModule());
    }

    protected void installInvoiceModule() {
    	install(new InvoiceModule());
    }

}