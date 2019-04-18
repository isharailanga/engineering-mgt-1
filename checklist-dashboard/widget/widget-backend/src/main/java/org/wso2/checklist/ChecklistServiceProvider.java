/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.checklist;



import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.config.ConfigurationException;
import org.wso2.checklist.utils.DataHolder;
import org.wso2.checklist.utils.RRMConfigurations;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * Checklist service provider
 */
public class ChecklistServiceProvider {
    private static final Logger log = LoggerFactory.getLogger(ChecklistServiceProvider.class);
    private static String hostUrl = "";

    static {
        try {
            hostUrl = DataHolder.getInstance().getConfigProvider()
                .getConfigurationObject(RRMConfigurations.class).getChecklistBackendUrl();

            if (StringUtils.isEmpty(hostUrl)) {
                log.info("No MPR dashboard URL defined.");
            }
        } catch (ConfigurationException e) {
            log.error("Error occured while reading Host Url for git issue backend ", e);
        }
    }

    public Object retrieveAllIssuesByProduct(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "jiraIssues/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpClient.execute(httpGet)) {
                HttpEntity httpEntity = response1.getEntity();
                response = EntityUtils.toString(httpEntity, "UTF-8");
            }
        }
        return response;
    }

    public Object retrieveProductVersions(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "versions/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpClient.execute(httpGet)) {
                HttpEntity httpEntity = response1.getEntity();
                response = EntityUtils.toString(httpEntity, "UTF-8");
            }
        }
        return response;
    }

    public Object retrieveProductNames() throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "productNames");

            URIBuilder uriBuilder = new URIBuilder(uri);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                response = EntityUtils.toString(entity1, "UTF-8");
            }
        }
        return response;
    }

    public Object getPendingDocTaskCount(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "mprCount/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                response = EntityUtils.toString(entity1, "UTF-8");
            }
        }
        return response;
    }

    public Object getDependancySummaryDetails(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "dependency/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                response = EntityUtils.toString(entity1, "UTF-8");
            }
        }
        return response;
    }

    public Object getProductCodeCoverage(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "codeCoverage/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                response = EntityUtils.toString(entity1, "UTF-8");
            }
        }
        return response;
    }

    public Object getGitIssues(String productName) throws IOException, URISyntaxException {
        String response;
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            URI uri = new URI(hostUrl + "gitIssues/");

            URIBuilder uriBuilder = new URIBuilder(uri);
            uriBuilder.addParameter("productName", productName);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response1 = httpclient.execute(httpGet)) {
                HttpEntity entity1 = response1.getEntity();
                response = EntityUtils.toString(entity1, "UTF-8");
            }
        }
        return response;
    }

}
