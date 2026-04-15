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
package org.apache.atlas.web.config;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Jersey configuration for Spring Boot.
 * Registers the Jersey servlet with Spring Boot.
 */
@Configuration
public class JerseyConfig {

    @Bean
    public ServletRegistrationBean<SpringServlet> jerseyServlet() {
        SpringServlet jerseyServlet = new SpringServlet();

        Map<String, String> initParams = new HashMap<>();
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

        ServletRegistrationBean<SpringServlet> registration = new ServletRegistrationBean<>(
            jerseyServlet,
            "/api/atlas/*"
        );
        registration.setInitParameters(initParams);
        registration.setLoadOnStartup(1);
        registration.setName("jersey-servlet");

        return registration;
    }
}
