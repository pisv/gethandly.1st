/*******************************************************************************
 * Copyright (c) 2014, 2015 1C-Soft LLC and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.internal.examples.basic.ui.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.handly.examples.basic.ui.model.IFooModel;
import org.eclipse.handly.examples.basic.ui.model.IFooProject;
import org.eclipse.handly.model.IHandle;
import org.eclipse.handly.model.impl.Body;
import org.eclipse.handly.model.impl.IBodyCache;

/**
 * The Foo Model cache.
 */
class FooModelCache
    implements IBodyCache
{
    private static final int DEFAULT_PROJECT_SIZE = 5;

    private Body modelBody; // Foo model element's body
    private HashMap<IHandle, Body> projectCache; // cache of open Foo projects

    public FooModelCache()
    {
        projectCache = new HashMap<IHandle, Body>(DEFAULT_PROJECT_SIZE);
    }

    @Override
    public Body get(IHandle handle)
    {
        if (handle instanceof IFooModel)
            return modelBody;
        else if (handle instanceof IFooProject)
            return projectCache.get(handle);
        else
            return null;
    }

    @Override
    public Body peek(IHandle handle)
    {
        if (handle instanceof IFooModel)
            return modelBody;
        else if (handle instanceof IFooProject)
            return projectCache.get(handle);
        else
            return null;
    }

    @Override
    public void put(IHandle handle, Body body)
    {
        if (handle instanceof IFooModel)
            modelBody = body;
        else if (handle instanceof IFooProject)
            projectCache.put(handle, body);
    }

    @Override
    public void putAll(Map<IHandle, Body> elements)
    {
        for (Map.Entry<IHandle, Body> entry : elements.entrySet())
        {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void remove(IHandle handle)
    {
        if (handle instanceof IFooModel)
            modelBody = null;
        else if (handle instanceof IFooProject)
            projectCache.remove(handle);
    }
}
