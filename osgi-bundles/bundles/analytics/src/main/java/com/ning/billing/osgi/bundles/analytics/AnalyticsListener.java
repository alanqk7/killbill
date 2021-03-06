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

package com.ning.billing.osgi.bundles.analytics;

import java.util.UUID;
import java.util.concurrent.Callable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.osgi.service.log.LogService;

import com.ning.billing.beatrix.bus.api.ExtBusEvent;
import com.ning.billing.commons.locker.GlobalLock;
import com.ning.billing.commons.locker.GlobalLocker;
import com.ning.billing.commons.locker.mysql.MySqlGlobalLocker;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessAccountDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessFieldDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessInvoiceDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessInvoicePaymentDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessOverdueStatusDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessSubscriptionTransitionDao;
import com.ning.billing.osgi.bundles.analytics.dao.BusinessTagDao;
import com.ning.billing.util.callcontext.CallContext;
import com.ning.billing.util.callcontext.CallOrigin;
import com.ning.billing.util.callcontext.UserType;
import com.ning.killbill.osgi.libs.killbill.OSGIKillbillAPI;
import com.ning.killbill.osgi.libs.killbill.OSGIKillbillDataSource;
import com.ning.killbill.osgi.libs.killbill.OSGIKillbillEventDispatcher.OSGIKillbillEventHandler;
import com.ning.killbill.osgi.libs.killbill.OSGIKillbillLogService;

public class AnalyticsListener implements OSGIKillbillEventHandler {

    private static final String ANALYTICS_NB_LOCK_TRY_PROPERTY = "killbill.osgi.analytics.lock.count";
    private static final int NB_LOCK_TRY = Integer.parseInt(System.getProperty(ANALYTICS_NB_LOCK_TRY_PROPERTY, "5"));

    private final LogService logService;
    private final BusinessAccountDao bacDao;
    private final BusinessSubscriptionTransitionDao bstDao;
    private final BusinessInvoiceDao binDao;
    private final BusinessInvoicePaymentDao bipDao;
    private final BusinessOverdueStatusDao bosDao;
    private final BusinessFieldDao bFieldDao;
    private final BusinessTagDao bTagDao;
    private final GlobalLocker locker;

    public AnalyticsListener(final OSGIKillbillLogService logService,
                             final OSGIKillbillAPI osgiKillbillAPI,
                             final OSGIKillbillDataSource osgiKillbillDataSource) {
        this.logService = logService;

        this.bacDao = new BusinessAccountDao(logService, osgiKillbillAPI, osgiKillbillDataSource);
        this.bstDao = new BusinessSubscriptionTransitionDao(logService, osgiKillbillAPI, osgiKillbillDataSource);
        this.binDao = new BusinessInvoiceDao(logService, osgiKillbillAPI, osgiKillbillDataSource, bacDao);
        this.bipDao = new BusinessInvoicePaymentDao(logService, osgiKillbillAPI, osgiKillbillDataSource, bacDao, binDao);
        this.bosDao = new BusinessOverdueStatusDao(logService, osgiKillbillAPI, osgiKillbillDataSource);
        this.bFieldDao = new BusinessFieldDao(logService, osgiKillbillAPI, osgiKillbillDataSource);
        this.bTagDao = new BusinessTagDao(logService, osgiKillbillAPI, osgiKillbillDataSource);

        this.locker = new MySqlGlobalLocker(osgiKillbillDataSource.getDataSource());
    }

    @Override
    public void handleKillbillEvent(final ExtBusEvent killbillEvent) {
        final CallContext callContext = new AnalyticsCallContext(killbillEvent);

        switch (killbillEvent.getEventType()) {
            case ACCOUNT_CREATION:
            case ACCOUNT_CHANGE:
                handleAccountEvent(killbillEvent, callContext);
                break;
            case SUBSCRIPTION_CREATION:
            case SUBSCRIPTION_CHANGE:
            case SUBSCRIPTION_CANCEL:
                handleSubscriptionEvent(killbillEvent, callContext);
                break;
            case OVERDUE_CHANGE:
                handleOverdueEvent(killbillEvent, callContext);
                break;
            case INVOICE_CREATION:
            case INVOICE_ADJUSTMENT:
                handleInvoiceEvent(killbillEvent, callContext);
                break;
            case PAYMENT_SUCCESS:
            case PAYMENT_FAILED:
                handlePaymentEvent(killbillEvent, callContext);
                break;
            case TAG_CREATION:
            case TAG_DELETION:
                handleTagEvent(killbillEvent, callContext);
                break;
            case CUSTOM_FIELD_CREATION:
            case CUSTOM_FIELD_DELETION:
                handleFieldEvent(killbillEvent, callContext);
                break;
            default:
                break;
        }
    }

    private void handleAccountEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bacDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private void handleSubscriptionEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bstDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private void handleInvoiceEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                binDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private void handlePaymentEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bipDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private void handleOverdueEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bosDao.update(killbillEvent.getAccountId(), killbillEvent.getObjectType(), callContext);
                return null;
            }
        });
    }

    private void handleTagEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bTagDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private void handleFieldEvent(final ExtBusEvent killbillEvent, final CallContext callContext) {
        updateWithAccountLock(killbillEvent, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                bFieldDao.update(killbillEvent.getAccountId(), callContext);
                return null;
            }
        });
    }

    private static final class AnalyticsCallContext implements CallContext {

        private static final String USER_NAME = AnalyticsListener.class.getName();

        private final ExtBusEvent killbillEvent;
        private final DateTime now;

        private AnalyticsCallContext(final ExtBusEvent killbillEvent) {
            this.killbillEvent = killbillEvent;
            this.now = new DateTime(DateTimeZone.UTC);
        }

        @Override
        public UUID getUserToken() {
            return UUID.randomUUID();
        }

        @Override
        public String getUserName() {
            return USER_NAME;
        }

        @Override
        public CallOrigin getCallOrigin() {
            return CallOrigin.INTERNAL;
        }

        @Override
        public UserType getUserType() {
            return UserType.SYSTEM;
        }

        @Override
        public String getReasonCode() {
            return killbillEvent.getEventType().toString();
        }

        @Override
        public String getComments() {
            return "eventType=" + killbillEvent.getEventType() + ", objectType="
                   + killbillEvent.getObjectType() + ", objectId=" + killbillEvent.getObjectId() + ", accountId="
                   + killbillEvent.getAccountId() + ", tenantId=" + killbillEvent.getTenantId();
        }

        @Override
        public DateTime getCreatedDate() {
            return now;
        }

        @Override
        public DateTime getUpdatedDate() {
            return now;
        }

        @Override
        public UUID getTenantId() {
            return killbillEvent.getTenantId();
        }
    }

    private <T> T updateWithAccountLock(final ExtBusEvent killbillEvent, final Callable<T> task) {
        GlobalLock lock = null;
        try {
            final String lockKey = killbillEvent.getAccountId() == null ? "0" : killbillEvent.getAccountId().toString();
            lock = locker.lockWithNumberOfTries("ACCOUNT_FOR_ANALYTICS", lockKey, NB_LOCK_TRY);
            return task.call();
        } catch (Exception e) {
            logService.log(LogService.LOG_WARNING, "Exception while refreshing analytics tables", e);
        } finally {
            if (lock != null) {
                lock.release();
            }
        }

        return null;
    }
}
