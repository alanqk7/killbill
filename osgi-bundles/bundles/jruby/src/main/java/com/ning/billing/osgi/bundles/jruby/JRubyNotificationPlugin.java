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

package com.ning.billing.osgi.bundles.jruby;

import org.jruby.embed.ScriptingContainer;
import org.jruby.javasupport.JavaEmbedUtils;
import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;

import com.ning.billing.beatrix.bus.api.ExtBusEvent;
import com.ning.billing.osgi.api.config.PluginRubyConfig;
import com.ning.killbill.osgi.libs.killbill.OSGIKillbillEventDispatcher.OSGIKillbillEventHandler;

public class JRubyNotificationPlugin extends JRubyPlugin implements OSGIKillbillEventHandler {

    public JRubyNotificationPlugin(final PluginRubyConfig config, final ScriptingContainer container,
                                   final BundleContext bundleContext, final LogService logger) {
        super(config, container, bundleContext, logger);
    }

    @Override
    public void startPlugin(final BundleContext context) {
        super.startPlugin(context);
    }

    @Override
    public void handleKillbillEvent(final ExtBusEvent killbillEvent) {
        checkValidNotificationPlugin();
        checkPluginIsRunning();
        pluginInstance.callMethod("on_event", JavaEmbedUtils.javaToRuby(getRuntime(), killbillEvent));
    }
}
