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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.msf4j.Microservice;
import org.wso2.msf4j.Request;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Checklist service to be called by widget
 */
public class ChecklistService implements Microservice {
    public static final String API_CONTEXT_PATH = "/apis/checklist";
    private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistService.class);

    private ChecklistServiceProvider checklistServiceProvider = new ChecklistServiceProvider();
    
    @GET
    @Path("/jiraIssues/{productName}")
    @Produces({ "application/json" })

    public Response retrieveAllIssuesByProduct(@Context Request request, @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.retrieveAllIssuesByProduct(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occurred while retrieving the response from the server");
        }
    }

    @GET
    @Path("/versions/{productName}")
    @Produces({ "application/json" })

    public Response retrieveProductVersions(@Context Request request, @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.retrieveProductVersions(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    @GET
    @Path("/productNames")
    @Produces({ "application/json" })

    public Response retrieveProductNames(@Context Request request) {
        try {
            return okResponse(checklistServiceProvider.retrieveProductNames());
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    @GET
    @Path("/mprCount/{productName}")
    @Produces({ "application/json" })

    public Response getPendingDocTaskCount(@Context Request request, @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.getPendingDocTaskCount(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    @GET
    @Path("/dependancy/{productName}")
    @Produces({ "application/json" })

    public Response getDependancySummaryDetails(@Context Request request,
            @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.getDependancySummaryDetails(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    @GET
    @Path("/codeCoverage/{productName}")
    @Produces({ "application/json" })

    public Response getProductCodeCoverage(@Context Request request, @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.getProductCodeCoverage(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    @GET
    @Path("/gitIssues/{productName")
    @Produces({ "application/json" })

    public Response getGitIssues(@Context Request request, @PathParam("productName") String productName) {
        try {
            return okResponse(checklistServiceProvider.getGitIssues(productName));
        } catch (Throwable throwable) {
            LOGGER.error("Error occured while " + throwable.getMessage(), throwable);
            return serverErrorResponse("Error occured while retrieving the response from the server");
        }
    }

    private static Response okResponse(Object content) {
        return Response.ok().entity(content).build();
    }

    private static Response serverErrorResponse(String message) {
        return Response.serverError().entity(message).build();
    }

}
