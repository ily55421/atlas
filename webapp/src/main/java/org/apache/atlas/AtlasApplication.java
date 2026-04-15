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
package org.apache.atlas;

import org.apache.atlas.web.listeners.LoginProcessor;
import org.apache.hadoop.util.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Spring Boot main application class for Apache Atlas.
 */
@SpringBootApplication(exclude = {JerseyAutoConfiguration.class})
@ImportResource({"classpath*:applicationContext.xml", "classpath*:spring-security.xml"})
@ServletComponentScan(basePackages = "org.apache.atlas.web")
public class AtlasApplication extends SpringBootServletInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AtlasApplication.class);

    private static final String ATLAS_HOME    = "atlas.home";
    private static final String ATLAS_DATA    = "atlas.data";
    private static final String ATLAS_LOG_DIR = "atlas.log.dir";
    private static final String ATLAS_CONF    = "atlas.conf";

    public static void main(String[] args) {
        // Set application home if not already set
        setApplicationHome();

        // Initialize login (Kerberos, etc.)
        LoginProcessor loginProcessor = new LoginProcessor();
        loginProcessor.login();

        // Install SLF4J bridge
        installLogBridge();

        SpringApplication application = new SpringApplication(AtlasApplication.class);
        StopWatch  stopWatch    = new StopWatch();
        stopWatch.start();
        LOG.info("############################################");
        LOG.info("############################################");
        LOG.info("           Atlas Server (Spring Boot)");
        LOG.info("############################################");
        LOG.info("############################################");

        application.run(args);
        stopWatch.stop();
        LOG.info("Atlas Server started in {} ms", stopWatch.now(TimeUnit.MILLISECONDS));
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // Set application home if not already set
        setApplicationHome();

        // Initialize login (Kerberos, etc.)
        LoginProcessor loginProcessor = new LoginProcessor();
        loginProcessor.login();

        installLogBridge();

        return builder.sources(AtlasApplication.class);
    }

    private static void setApplicationHome() {
        String userDir = System.getProperty("user.dir");

        if (System.getProperty(ATLAS_HOME) == null) {
            System.setProperty(ATLAS_HOME, userDir + "/target");
        }

        if (System.getProperty(ATLAS_DATA) == null) {
            System.setProperty(ATLAS_DATA, userDir + "/target/data");
        }

        if (System.getProperty(ATLAS_LOG_DIR) == null) {
            System.setProperty(ATLAS_LOG_DIR, userDir + "/target/logs");
        }

        // Set atlas.conf to point to resources directory for config files
        if (System.getProperty(ATLAS_CONF) == null) {
            String confPath = userDir + "/src/main/resources";
            File confDir = new File(confPath);
            if (confDir.exists()) {
                System.setProperty(ATLAS_CONF, confPath);
                LOG.info("Setting atlas.conf to: {}", confPath);
            }
        }

        // Create data directory if not exists
        File dataDir = new File(System.getProperty(ATLAS_DATA));
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    private static void installLogBridge() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
