/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.atlas.web.setup;

import org.apache.atlas.web.listeners.LoginProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet context listener for Atlas initialization.
 * Handles Kerberos login and other startup tasks.
 */
@WebListener
@Component
public class AtlasServletContextListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(AtlasServletContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("==> AtlasServletContextListener.contextInitialized()");
        
        // Login processor is already called in AtlasApplication.main()
        // This is a fallback for WAR deployment
        
        LOG.info("<== AtlasServletContextListener.contextInitialized()");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("==> AtlasServletContextListener.contextDestroyed()");
        LOG.info("<== AtlasServletContextListener.contextDestroyed()");
    }
}
