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

package com.ning.billing.invoice.tests.inAdvance.annual;

import static com.ning.billing.invoice.TestInvoiceHelper.*;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.testng.annotations.Test;

import com.ning.billing.catalog.api.BillingPeriod;
import com.ning.billing.invoice.model.InvalidDateSequenceException;
import com.ning.billing.invoice.tests.inAdvance.ProRationInAdvanceTestBase;

public class TestDoubleProRation extends ProRationInAdvanceTestBase {

    @Override
    protected BillingPeriod getBillingPeriod() {
        return BillingPeriod.ANNUAL;
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateOnStartDate() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        final BigDecimal expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateInFirstProRationPeriod() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2011, 1, 7);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        final BigDecimal expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateOnFirstBillingCycleDate() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2011, 1, 15);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        final BigDecimal expectedValue = ONE.add(FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, NUMBER_OF_DECIMALS, ROUNDING_METHOD));
        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateInFullBillingPeriod() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2011, 1, 22);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(ONE);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateOnSecondBillingCycleDate() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2012, 1, 15);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(ONE);
        expectedValue = expectedValue.add(TWELVE.divide(THREE_HUNDRED_AND_SIXTY_SIX, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD));
        expectedValue = expectedValue.setScale(NUMBER_OF_DECIMALS, ROUNDING_METHOD);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateInSecondProRationPeriod() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2012, 1, 17);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(ONE);
        expectedValue = expectedValue.add(TWELVE.divide(THREE_HUNDRED_AND_SIXTY_SIX, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD));
        expectedValue = expectedValue.setScale(NUMBER_OF_DECIMALS, ROUNDING_METHOD);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateOnEndDate() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2012, 1, 27);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(ONE);
        expectedValue = expectedValue.add(TWELVE.divide(THREE_HUNDRED_AND_SIXTY_SIX, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD));
        expectedValue = expectedValue.setScale(NUMBER_OF_DECIMALS, ROUNDING_METHOD);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRation_TargetDateAfterEndDate() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2012, 3, 7);
        final LocalDate endDate = invoiceUtil.buildDate(2012, 1, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(ONE);
        expectedValue = expectedValue.add(TWELVE.divide(THREE_HUNDRED_AND_SIXTY_SIX, 2 * NUMBER_OF_DECIMALS, ROUNDING_METHOD));
        expectedValue = expectedValue.setScale(NUMBER_OF_DECIMALS, ROUNDING_METHOD);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }

    @Test(groups = "fast")
    public void testDoubleProRationWithMultiplePeriods_TargetDateInSecondFullBillingPeriod() throws InvalidDateSequenceException {
        final LocalDate startDate = invoiceUtil.buildDate(2011, 1, 1);
        final LocalDate targetDate = invoiceUtil.buildDate(2012, 2, 26);
        final LocalDate endDate = invoiceUtil.buildDate(2013, 4, 27);

        BigDecimal expectedValue;
        expectedValue = FOURTEEN.divide(THREE_HUNDRED_AND_SIXTY_FIVE, NUMBER_OF_DECIMALS, ROUNDING_METHOD);
        expectedValue = expectedValue.add(TWO);

        testCalculateNumberOfBillingCycles(startDate, endDate, targetDate, 15, expectedValue);
    }
}
