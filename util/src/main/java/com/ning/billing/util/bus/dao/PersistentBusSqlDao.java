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
package com.ning.billing.util.bus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.DateTime;
import org.skife.jdbi.v2.SQLStatement;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.Binder;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.mixins.CloseMe;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.ExternalizedSqlViaStringTemplate3;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.ning.billing.util.dao.BinderBase;
import com.ning.billing.util.dao.MapperBase;
import com.ning.billing.util.notificationq.NotificationLifecycle.NotificationLifecycleState;

@ExternalizedSqlViaStringTemplate3()
public interface PersistentBusSqlDao extends Transactional<PersistentBusSqlDao>, CloseMe {

    
    @SqlQuery
    @Mapper(PersistentBusSqlMapper.class)
    public BusEventEntry getNextBusEventEntry(@Bind("max") int max, @Bind("now") Date now);
    
    @SqlUpdate
    public int claimBusEvent(@Bind("owner") String owner, @Bind("next_available") Date nextAvailable, @Bind("id") long id, @Bind("now") Date now);

    @SqlUpdate
    public void clearBusEvent(@Bind("id") long id, @Bind("owner") String owner);

    @SqlUpdate
    public void removeBusEventsById(@Bind("id") long id);
    
    @SqlUpdate
    public void insertBusEvent(@Bind(binder = PersistentBusSqlBinder.class) BusEventEntry evt);

    @SqlUpdate
    public void insertClaimedHistory(@Bind("owner_id") String owner, @Bind("claimed_dt") Date claimedDate, @Bind("bus_event_id") long id);

    
    public static class PersistentBusSqlBinder extends BinderBase implements Binder<Bind, BusEventEntry> {

        @Override
        public void bind(@SuppressWarnings("rawtypes") SQLStatement stmt, Bind bind, BusEventEntry evt) {
            stmt.bind("class_name", evt.getBusEventClass());
            stmt.bind("event_json", evt.getBusEventJson()); 
            stmt.bind("created_dt", getDate(new DateTime()));
            stmt.bind("processing_available_dt", getDate(evt.getNextAvailableDate()));
            stmt.bind("processing_owner", evt.getOwner());
            stmt.bind("processing_state", NotificationLifecycleState.AVAILABLE.toString());
        }
    }
    
    public static class PersistentBusSqlMapper extends MapperBase implements ResultSetMapper<BusEventEntry> {

        @Override
        public BusEventEntry map(int index, ResultSet r, StatementContext ctx)
                throws SQLException {

            final long id = r.getLong("id");
            final String className = r.getString("class_name"); 
            final String eventJson = r.getString("event_json"); 
            final DateTime nextAvailableDate = getDate(r, "processing_available_dt");
            final String processingOwner = r.getString("processing_owner");
            final NotificationLifecycleState processingState = NotificationLifecycleState.valueOf(r.getString("processing_state"));
            
            return new BusEventEntry(id, processingOwner, nextAvailableDate, processingState, className, eventJson);
        }
    }
}