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

package com.ning.billing.entitlement.api.user;

import java.util.UUID;

import org.joda.time.DateTime;

import com.ning.billing.entitlement.api.SubscriptionTransitionType;
import com.ning.billing.entitlement.events.EntitlementEvent;
import com.ning.billing.util.events.RequestedSubscriptionInternalEvent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultRequestedSubscriptionEvent extends DefaultSubscriptionEvent implements RequestedSubscriptionInternalEvent {
    public DefaultRequestedSubscriptionEvent(final SubscriptionTransitionData in, final DateTime startDate, final UUID userToken, final Long accountRecordId, final Long tenantRecordId) {
        super(in, startDate, userToken, accountRecordId, tenantRecordId);
    }

    @JsonCreator
    public DefaultRequestedSubscriptionEvent(@JsonProperty("eventId") final UUID eventId,
                                             @JsonProperty("subscriptionId") final UUID subscriptionId,
                                             @JsonProperty("bundleId") final UUID bundleId,
                                             @JsonProperty("requestedTransitionTime") final DateTime requestedTransitionTime,
                                             @JsonProperty("effectiveTransitionTime") final DateTime effectiveTransitionTime,
                                             @JsonProperty("previousState") final Subscription.SubscriptionState previousState,
                                             @JsonProperty("previousPlan") final String previousPlan,
                                             @JsonProperty("previousPhase") final String previousPhase,
                                             @JsonProperty("previousPriceList") final String previousPriceList,
                                             @JsonProperty("nextState") final Subscription.SubscriptionState nextState,
                                             @JsonProperty("nextPlan") final String nextPlan,
                                             @JsonProperty("nextPhase") final String nextPhase,
                                             @JsonProperty("nextPriceList") final String nextPriceList,
                                             @JsonProperty("totalOrdering") final Long totalOrdering,
                                             @JsonProperty("userToken") final UUID userToken,
                                             @JsonProperty("transitionType") final SubscriptionTransitionType transitionType,
                                             @JsonProperty("remainingEventsForUserOperation") final Integer remainingEventsForUserOperation,
                                             @JsonProperty("startDate") final DateTime startDate,
                                             @JsonProperty("accountRecordId") final Long accountRecordId,
                                             @JsonProperty("tenantRecordId") final Long tenantRecordId) {
        super(eventId, subscriptionId, bundleId, requestedTransitionTime, effectiveTransitionTime, previousState, previousPlan,
              previousPhase, previousPriceList, nextState, nextPlan, nextPhase, nextPriceList, totalOrdering, userToken,
              transitionType, remainingEventsForUserOperation, startDate, accountRecordId, tenantRecordId);
    }

    public DefaultRequestedSubscriptionEvent(final SubscriptionData subscription, final EntitlementEvent nextEvent, final Long accountRecordId, final Long tenantRecordId) {
        this(nextEvent.getId(), nextEvent.getSubscriptionId(), subscription.getBundleId(), nextEvent.getRequestedDate(), nextEvent.getEffectiveDate(),
             null, null, null, null, null, null, null, null, nextEvent.getTotalOrdering(), null, null, 0, null, accountRecordId, tenantRecordId);
    }
}
