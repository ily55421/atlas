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

import org.apache.atlas.web.filters.AuditFilter;
import org.apache.atlas.web.filters.AtlasHeaderFilter;
import org.apache.atlas.web.servlets.AtlasErrorServlet;
import org.apache.atlas.web.servlets.AtlasLoginServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Web configuration for Atlas Spring Boot.
 */
@Configuration
public class AtlasWebConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Bean
    public ServletRegistrationBean<AtlasLoginServlet> loginServlet() {
        ServletRegistrationBean<AtlasLoginServlet> registration = new ServletRegistrationBean<>(
            new AtlasLoginServlet(),
            "/login.jsp"
        );
        registration.setName("LoginServlet");
        return registration;
    }

    @Bean
    public ServletRegistrationBean<AtlasErrorServlet> errorServlet() {
        ServletRegistrationBean<AtlasErrorServlet> registration = new ServletRegistrationBean<>(
            new AtlasErrorServlet(),
            "/error.jsp"
        );
        registration.setName("ErrorServlet");
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AuditFilter> auditFilter() {
        FilterRegistrationBean<AuditFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuditFilter());
        registration.addUrlPatterns("/*");
        registration.setName("AuditFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AtlasHeaderFilter> headerFilter() {
        FilterRegistrationBean<AtlasHeaderFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AtlasHeaderFilter());
        registration.addUrlPatterns("/api/atlas/admin/metrics", "/api/atlas/admin/status");
        registration.setName("HeaderFilter");
        registration.setOrder(2);
        return registration;
    }
}
