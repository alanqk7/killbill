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

package com.ning.billing.analytics.api;

import java.math.BigDecimal;
import java.util.UUID;

import org.joda.time.DateTime;

import com.ning.billing.analytics.model.BusinessInvoicePaymentModelDao;
import com.ning.billing.catalog.api.Currency;
import com.ning.billing.util.entity.EntityBase;

public class DefaultBusinessInvoicePayment extends EntityBase implements BusinessInvoicePayment {

    private final UUID paymentId;
    private final String extFirstPaymentRefId;
    private final String extSecondPaymentRefId;
    private final String accountKey;
    private final UUID invoiceId;
    private final DateTime effectiveDate;
    private final BigDecimal amount;
    private final Currency currency;
    private final String paymentError;
    private final String processingStatus;
    private final BigDecimal requestedAmount;
    private final String pluginName;
    private final String paymentType;
    private final String paymentMethod;
    private final String cardType;
    private final String cardCountry;
    private final String invoicePaymentType;
    private final UUID linkedInvoicePaymentId;

    public DefaultBusinessInvoicePayment(final BusinessInvoicePaymentModelDao businessInvoicePaymentModelDao) {
        this.paymentId = businessInvoicePaymentModelDao.getPaymentId();
        this.extFirstPaymentRefId = businessInvoicePaymentModelDao.getExtFirstPaymentRefId();
        this.extSecondPaymentRefId = businessInvoicePaymentModelDao.getExtSecondPaymentRefId();
        this.accountKey = businessInvoicePaymentModelDao.getAccountKey();
        this.invoiceId = businessInvoicePaymentModelDao.getInvoiceId();
        this.effectiveDate = businessInvoicePaymentModelDao.getEffectiveDate();
        this.amount = businessInvoicePaymentModelDao.getAmount();
        this.currency = businessInvoicePaymentModelDao.getCurrency();
        this.paymentError = businessInvoicePaymentModelDao.getPaymentError();
        this.processingStatus = businessInvoicePaymentModelDao.getProcessingStatus();
        this.requestedAmount = businessInvoicePaymentModelDao.getRequestedAmount();
        this.pluginName = businessInvoicePaymentModelDao.getPluginName();
        this.paymentType = businessInvoicePaymentModelDao.getPaymentType();
        this.paymentMethod = businessInvoicePaymentModelDao.getPaymentMethod();
        this.cardType = businessInvoicePaymentModelDao.getCardType();
        this.cardCountry = businessInvoicePaymentModelDao.getCardCountry();
        this.invoicePaymentType = businessInvoicePaymentModelDao.getInvoicePaymentType();
        this.linkedInvoicePaymentId = businessInvoicePaymentModelDao.getLinkedInvoicePaymentId();
    }

    @Override
    public UUID getPaymentId() {
        return paymentId;
    }

    @Override
    public String getExtFirstPaymentRefId() {
        return extFirstPaymentRefId;
    }

    @Override
    public String getExtSecondPaymentRefId() {
        return extSecondPaymentRefId;
    }

    @Override
    public String getAccountKey() {
        return accountKey;
    }

    @Override
    public UUID getInvoiceId() {
        return invoiceId;
    }

    @Override
    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getPaymentError() {
        return paymentError;
    }

    @Override
    public String getProcessingStatus() {
        return processingStatus;
    }

    @Override
    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    @Override
    public String getPluginName() {
        return pluginName;
    }

    @Override
    public String getPaymentType() {
        return paymentType;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String getCardType() {
        return cardType;
    }

    @Override
    public String getCardCountry() {
        return cardCountry;
    }

    @Override
    public String getInvoicePaymentType() {
        return invoicePaymentType;
    }

    @Override
    public UUID getLinkedInvoicePaymentId() {
        return linkedInvoicePaymentId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DefaultBusinessInvoicePayment");
        sb.append("{paymentId=").append(paymentId);
        sb.append(", extFirstPaymentRefId='").append(extFirstPaymentRefId).append('\'');
        sb.append(", extSecondPaymentRefId='").append(extSecondPaymentRefId).append('\'');
        sb.append(", accountKey='").append(accountKey).append('\'');
        sb.append(", invoiceId=").append(invoiceId);
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", amount=").append(amount);
        sb.append(", currency=").append(currency);
        sb.append(", paymentError='").append(paymentError).append('\'');
        sb.append(", processingStatus='").append(processingStatus).append('\'');
        sb.append(", requestedAmount=").append(requestedAmount);
        sb.append(", pluginName='").append(pluginName).append('\'');
        sb.append(", paymentType='").append(paymentType).append('\'');
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
        sb.append(", cardType='").append(cardType).append('\'');
        sb.append(", cardCountry='").append(cardCountry).append('\'');
        sb.append(", invoicePaymentType='").append(invoicePaymentType).append('\'');
        sb.append(", linkedInvoicePaymentId=").append(linkedInvoicePaymentId);
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

        final DefaultBusinessInvoicePayment that = (DefaultBusinessInvoicePayment) o;

        if (accountKey != null ? !accountKey.equals(that.accountKey) : that.accountKey != null) {
            return false;
        }
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) {
            return false;
        }
        if (cardCountry != null ? !cardCountry.equals(that.cardCountry) : that.cardCountry != null) {
            return false;
        }
        if (cardType != null ? !cardType.equals(that.cardType) : that.cardType != null) {
            return false;
        }
        if (currency != that.currency) {
            return false;
        }
        if (effectiveDate != null ? !effectiveDate.equals(that.effectiveDate) : that.effectiveDate != null) {
            return false;
        }
        if (extFirstPaymentRefId != null ? !extFirstPaymentRefId.equals(that.extFirstPaymentRefId) : that.extFirstPaymentRefId != null) {
            return false;
        }
        if (extSecondPaymentRefId != null ? !extSecondPaymentRefId.equals(that.extSecondPaymentRefId) : that.extSecondPaymentRefId != null) {
            return false;
        }
        if (invoiceId != null ? !invoiceId.equals(that.invoiceId) : that.invoiceId != null) {
            return false;
        }
        if (invoicePaymentType != null ? !invoicePaymentType.equals(that.invoicePaymentType) : that.invoicePaymentType != null) {
            return false;
        }
        if (linkedInvoicePaymentId != null ? !linkedInvoicePaymentId.equals(that.linkedInvoicePaymentId) : that.linkedInvoicePaymentId != null) {
            return false;
        }
        if (paymentError != null ? !paymentError.equals(that.paymentError) : that.paymentError != null) {
            return false;
        }
        if (paymentId != null ? !paymentId.equals(that.paymentId) : that.paymentId != null) {
            return false;
        }
        if (paymentMethod != null ? !paymentMethod.equals(that.paymentMethod) : that.paymentMethod != null) {
            return false;
        }
        if (paymentType != null ? !paymentType.equals(that.paymentType) : that.paymentType != null) {
            return false;
        }
        if (pluginName != null ? !pluginName.equals(that.pluginName) : that.pluginName != null) {
            return false;
        }
        if (processingStatus != null ? !processingStatus.equals(that.processingStatus) : that.processingStatus != null) {
            return false;
        }
        if (requestedAmount != null ? !requestedAmount.equals(that.requestedAmount) : that.requestedAmount != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = paymentId != null ? paymentId.hashCode() : 0;
        result = 31 * result + (extFirstPaymentRefId != null ? extFirstPaymentRefId.hashCode() : 0);
        result = 31 * result + (extSecondPaymentRefId != null ? extSecondPaymentRefId.hashCode() : 0);
        result = 31 * result + (accountKey != null ? accountKey.hashCode() : 0);
        result = 31 * result + (invoiceId != null ? invoiceId.hashCode() : 0);
        result = 31 * result + (effectiveDate != null ? effectiveDate.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (paymentError != null ? paymentError.hashCode() : 0);
        result = 31 * result + (processingStatus != null ? processingStatus.hashCode() : 0);
        result = 31 * result + (requestedAmount != null ? requestedAmount.hashCode() : 0);
        result = 31 * result + (pluginName != null ? pluginName.hashCode() : 0);
        result = 31 * result + (paymentType != null ? paymentType.hashCode() : 0);
        result = 31 * result + (paymentMethod != null ? paymentMethod.hashCode() : 0);
        result = 31 * result + (cardType != null ? cardType.hashCode() : 0);
        result = 31 * result + (cardCountry != null ? cardCountry.hashCode() : 0);
        result = 31 * result + (invoicePaymentType != null ? invoicePaymentType.hashCode() : 0);
        result = 31 * result + (linkedInvoicePaymentId != null ? linkedInvoicePaymentId.hashCode() : 0);
        return result;
    }
}
