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
package com.ning.billing.entitlement.api;

/**
 * The {@code SubscriptionTransitionType}
 */
public enum SubscriptionTransitionType {
    /**
     * Occurs when a {@code Subscription} got migrated to mark the start of the entitlement
     */
    MIGRATE_ENTITLEMENT,
    /**
     * Occurs when a a user created a {@code Subscription} (not migrated)
     */
    CREATE,
    /**
     * Occurs when a {@code Subscription} got migrated to mark the start of the billing
     */
    MIGRATE_BILLING,
    /**
     * Occurs when a {@code Subscription} got transferred to mark the start of the entitlement
     */
    TRANSFER,
    /**
     * Occurs when a user changed the current {@code Plan} of the {@code Subscription}
     */
    CHANGE,
    /**
     * Occurs when a user restarted a {@code Subscription} after it had been cancelled
     */
    RE_CREATE,
    /**
     * Occurs when a user cancelled the {@code Subscription}
     */
    CANCEL,
    /**
     * Occurs when a user uncancelled the {@code Subscription} before it reached its cancellation date
     */
    UNCANCEL,
    /**
     * Generated by the system to mark a change of phase
     */
    PHASE,
    /**
     * Generated by the system to mark the start of blocked billing overdue state
     */
    START_BILLING_DISABLED,
    /**
     * Generated by the system to mark the end of blocked billing overdue state
     */
    END_BILLING_DISABLED
}
