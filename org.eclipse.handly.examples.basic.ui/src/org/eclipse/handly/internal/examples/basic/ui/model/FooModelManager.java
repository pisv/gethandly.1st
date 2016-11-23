/*******************************************************************************
 * Copyright (c) 2014, 2016 1C-Soft LLC and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.internal.examples.basic.ui.model;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.handly.internal.examples.basic.ui.Activator;
import org.eclipse.handly.model.impl.ElementManager;
import org.eclipse.handly.model.impl.IModelManager;

/**
 * The manager for the Foo Model. 
 *
 * @threadsafe This class is intended to be thread-safe
 */
public class FooModelManager
    implements IModelManager, IResourceChangeListener
{
    /**
     * The sole instance of the manager. 
     */
    public static final FooModelManager INSTANCE = new FooModelManager();

    private FooModel fooModel;
    private ElementManager elementManager;

    public void startup() throws Exception
    {
        fooModel = new FooModel();
        elementManager = new ElementManager(new FooModelCache());
        fooModel.getWorkspace().addResourceChangeListener(this,
            IResourceChangeEvent.POST_CHANGE);
    }

    public void shutdown() throws Exception
    {
        fooModel.getWorkspace().removeResourceChangeListener(this);
        elementManager = null;
        fooModel = null;
    }

    @Override
    public void resourceChanged(IResourceChangeEvent event)
    {
        if (event.getType() != IResourceChangeEvent.POST_CHANGE)
            return;
        FooDeltaProcessor deltaProcessor = new FooDeltaProcessor();
        try
        {
            event.getDelta().accept(deltaProcessor);
        }
        catch (CoreException e)
        {
            Activator.log(e.getStatus());
        }
    }

    @Override
    public FooModel getModel()
    {
        if (fooModel == null)
            throw new IllegalStateException();
        return fooModel;
    }

    @Override
    public ElementManager getElementManager()
    {
        if (elementManager == null)
            throw new IllegalStateException();
        return elementManager;
    }

    private FooModelManager()
    {
    }
}
