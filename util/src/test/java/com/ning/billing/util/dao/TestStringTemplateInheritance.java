/*
 * Copyright 2010-2012 Ning, Inc.
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

package com.ning.billing.util.dao;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ning.billing.util.UtilTestSuite;

import com.google.common.collect.ImmutableMap;

public class TestStringTemplateInheritance extends UtilTestSuite {

    InputStream entityStream;
    InputStream kombuchaStream;

    @BeforeMethod(groups = "fast")
    public void setUp() throws Exception {
        entityStream = this.getClass().getResourceAsStream("/com/ning/billing/util/entity/dao/EntitySqlDao.sql.stg");
        kombuchaStream = this.getClass().getResourceAsStream("/com/ning/billing/util/dao/Kombucha.sql.stg");
    }

    @AfterMethod(groups = "fast")
    public void tearDown() throws Exception {
        if (entityStream != null) {
            entityStream.close();
        }
        if (kombuchaStream != null) {
            kombuchaStream.close();
        }
    }

    @Test(groups = "fast")
    public void testCheckQueries() throws Exception {
        // From http://www.antlr.org/wiki/display/ST/ST+condensed+--+Templates+and+groups#STcondensed--Templatesandgroups-Withsupergroupfile:
        //     there is no mechanism for automatically loading a mentioned super-group file
        new StringTemplateGroup(new InputStreamReader(entityStream));

        final StringTemplateGroup kombucha = new StringTemplateGroup(new InputStreamReader(kombuchaStream));

        // Verify non inherited template
        Assert.assertEquals(kombucha.getInstanceOf("isIsTimeForKombucha").toString(), "select hour(current_timestamp()) = 17 as is_time;");

        // Verify inherited templates
        Assert.assertEquals(kombucha.getInstanceOf("getById").toString(), "select\n" +
                                                                          "  t.record_id\n" +
                                                                          ", t.id\n" +
                                                                          ", t.tea\n" +
                                                                          ", t.mushroom\n" +
                                                                          ", t.sugar\n" +
                                                                          ", t.account_record_id\n" +
                                                                          ", t.tenant_record_id\n" +
                                                                          "from kombucha t\n" +
                                                                          "where t.id = :id\n" +
                                                                          "and t.tenant_record_id = :tenantRecordId\n" +
                                                                          ";");
        Assert.assertEquals(kombucha.getInstanceOf("getByRecordId").toString(), "select\n" +
                                                                                "  t.record_id\n" +
                                                                                ", t.id\n" +
                                                                                ", t.tea\n" +
                                                                                ", t.mushroom\n" +
                                                                                ", t.sugar\n" +
                                                                                ", t.account_record_id\n" +
                                                                                ", t.tenant_record_id\n" +
                                                                                "from kombucha t\n" +
                                                                                "where t.record_id = :recordId\n" +
                                                                                "and t.tenant_record_id = :tenantRecordId\n" +
                                                                                ";");
        Assert.assertEquals(kombucha.getInstanceOf("getRecordId").toString(), "select\n" +
                                                                              "  t.record_id\n" +
                                                                              "from kombucha t\n" +
                                                                              "where t.id = :id\n" +
                                                                              "and t.tenant_record_id = :tenantRecordId\n" +
                                                                              ";");
        Assert.assertEquals(kombucha.getInstanceOf("getHistoryRecordId").toString(), "select\n" +
                                                                                     "  max(t.history_record_id)\n" +
                                                                                     "from kombucha t\n" +
                                                                                     "where t.record_id = :recordId\n" +
                                                                                     "and t.tenant_record_id = :tenantRecordId\n" +
                                                                                     ";");
        Assert.assertEquals(kombucha.getInstanceOf("get").toString(), "select\n" +
                                                                      "  t.record_id\n" +
                                                                      ", t.id\n" +
                                                                      ", t.tea\n" +
                                                                      ", t.mushroom\n" +
                                                                      ", t.sugar\n" +
                                                                      ", t.account_record_id\n" +
                                                                      ", t.tenant_record_id\n" +
                                                                      "from kombucha t\n" +
                                                                      "where t.tenant_record_id = :tenantRecordId\n" +
                                                                      ";");
        Assert.assertEquals(kombucha.getInstanceOf("get", ImmutableMap.<String, String>of("limit", "12")).toString(), "select\n" +
                                                                                                                      "  t.record_id\n" +
                                                                                                                      ", t.id\n" +
                                                                                                                      ", t.tea\n" +
                                                                                                                      ", t.mushroom\n" +
                                                                                                                      ", t.sugar\n" +
                                                                                                                      ", t.account_record_id\n" +
                                                                                                                      ", t.tenant_record_id\n" +
                                                                                                                      "from kombucha t\n" +
                                                                                                                      "where t.tenant_record_id = :tenantRecordId\n" +
                                                                                                                      "limit :limit\n" +
                                                                                                                      ";");
        Assert.assertEquals(kombucha.getInstanceOf("test").toString(), "select\n" +
                                                                       "  t.record_id\n" +
                                                                       ", t.id\n" +
                                                                       ", t.tea\n" +
                                                                       ", t.mushroom\n" +
                                                                       ", t.sugar\n" +
                                                                       ", t.account_record_id\n" +
                                                                       ", t.tenant_record_id\n" +
                                                                       "from kombucha t\n" +
                                                                       "where t.tenant_record_id = :tenantRecordId\n" +
                                                                       "limit 1\n" +
                                                                       ";");
        Assert.assertEquals(kombucha.getInstanceOf("addHistoryFromTransaction").toString(), "insert into kombucha_history (\n" +
                                                                                            "  record_id\n" +
                                                                                            ", id\n" +
                                                                                            ", tea\n" +
                                                                                            ", mushroom\n" +
                                                                                            ", sugar\n" +
                                                                                            ", account_record_id\n" +
                                                                                            ", tenant_record_id\n" +
                                                                                            ")\n" +
                                                                                            "values (\n" +
                                                                                            "  :recordId\n" +
                                                                                            ", :id\n" +
                                                                                            ",   :tea\n" +
                                                                                            ", :mushroom\n" +
                                                                                            ", :sugar\n" +
                                                                                            ", :accountRecordId\n" +
                                                                                            ", :tenantRecordId\n" +
                                                                                            ")\n" +
                                                                                            ";");
        Assert.assertEquals(kombucha.getInstanceOf("insertAuditFromTransaction").toString(), "insert into audit_log (\n" +
                                                                                             "table_name\n" +
                                                                                             ", record_id\n" +
                                                                                             ", change_type\n" +
                                                                                             ", change_date\n" +
                                                                                             ", changed_by\n" +
                                                                                             ", reason_code\n" +
                                                                                             ", comments\n" +
                                                                                             ", user_token\n" +
                                                                                             ", account_record_id\n" +
                                                                                             ", tenant_record_id\n" +
                                                                                             ")\n" +
                                                                                             "values (\n" +
                                                                                             "  :table_name\n" +
                                                                                             ", :record_id\n" +
                                                                                             ", :change_type\n" +
                                                                                             ", :change_date\n" +
                                                                                             ", :changed_by\n" +
                                                                                             ", :reason_code\n" +
                                                                                             ", :comments\n" +
                                                                                             ", :user_token\n" +
                                                                                             ", :accountRecordId\n" +
                                                                                             ", :tenantRecordId\n" +
                                                                                             ")\n" +
                                                                                             ";");
    }
}