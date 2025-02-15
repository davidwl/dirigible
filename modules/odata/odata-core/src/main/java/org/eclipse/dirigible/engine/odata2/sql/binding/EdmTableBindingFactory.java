/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.dirigible.engine.odata2.sql.binding;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class EdmTableBindingFactory {

    private static final Logger LOG = LoggerFactory.getLogger(EdmTableBindingFactory.class);

    public EdmTableBinding createTableBinding(ClassLoader loader, String resource) {
        if (loader == null) {
            throw new IllegalArgumentException("No classloader provided. Cannot load table mappings for resource" + resource);
        }
       try(InputStream stream = loader.getResourceAsStream(resource)){
            if (stream == null) {
                //not found 
                throw new IllegalArgumentException("Unable to find table mapping for resource " + resource);
            }
            return createTableBinding(stream);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse the table mapping for resource " + resource, e);
        }
    }
    
    public EdmTableBinding createTableBinding(InputStream stream) {
        Map<String, Object> bindingData = loadTableBindings(stream);
        EdmTableBinding mapping = new EdmTableBinding(bindingData);
        return mapping;
    }

    
    public static String simpleName(String fqn) {
        String[] res = fqn.split("\\.");
        return res[res.length - 1];
    }
    
    @SuppressWarnings("unchecked")
    protected Map<String, Object> loadTableBindings(InputStream stream) {
        try {
            return new Gson().fromJson(new InputStreamReader(stream), Map.class);
        } catch (Exception e) {
            LOG.error("Unable to parse the input stream", e);
            throw e;
        } 
    }
    
}