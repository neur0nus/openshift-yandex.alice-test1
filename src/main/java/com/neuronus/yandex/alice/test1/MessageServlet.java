/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.neuronus.yandex.alice.test1;

import java.io.IOException;import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neuronus.yandex.alice.DialogProcessor;
import com.neuronus.yandex.alice.protocol.YARequest;
import com.neuronus.yandex.alice.protocol.YAResponse;

public class MessageServlet extends HttpServlet {

	private static final long serialVersionUID = -6851106130642482976L;
	private static final Logger LOG = Logger.getLogger(MessageServlet.class.getName());
	private static final Gson GSON = new GsonBuilder().create();
	
	private DialogProcessor dpoc;
	
    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        this.dpoc = new DialogProcessor();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    	resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Please use HTTP POST method.");
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
    	try {
	    	ServletInputStream inp = req.getInputStream();
	    	String reqJSON = this.dpoc.loadRequestContentAsString(inp, "UTF-8");
	    	LOG.log(Level.SEVERE, "Received request: "+reqJSON);
	    	YARequest yareq = GSON.fromJson(reqJSON,YARequest.class);
	    	YAResponse yares = this.dpoc.handleRequest(yareq);
	    	resp.setCharacterEncoding("UTF-8");
	    	resp.setContentType("application/json");
	    	String resJSON = GSON.toJson(yares);
	    	LOG.log(Level.SEVERE, "Created response: "+resJSON);
	    	resp.getWriter().println(resJSON);
    	} catch(Throwable err) {
    		LOG.warning("Error request handling: "+err.getMessage());
    		throw new ServletException(err);
    	}
    }
}
