/*******************************************************************************
 * Copyright (c) 2014, 2017 1C-Soft LLC and others.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.internal.examples.basic.ui.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.handly.examples.basic.ui.model.FooModelCore;
import org.eclipse.handly.examples.basic.ui.model.IFooProject;
import org.eclipse.handly.model.Elements;
import org.eclipse.handly.model.IElement;
import org.eclipse.handly.model.impl.support.Body;
import org.eclipse.handly.model.impl.support.Element;

/**
 * This class is used by the <code>FooModelManager</code> to process 
 * resource deltas and update the Foo Model accordingly.
 */
class FooDeltaProcessor
    implements IResourceDeltaVisitor
{
    private Set<String> oldFooProjectNames = new HashSet<String>();

    @Override
    public boolean visit(IResourceDelta delta) throws CoreException
    {
        switch (delta.getResource().getType())
        {
        case IResource.ROOT:
            return processRoot(delta);

        case IResource.PROJECT:
            return processProject(delta);

        default:
            return true;
        }
    }

    private boolean processRoot(IResourceDelta delta) throws CoreException
    {
        initOldFooProjectNames();
        return true;
    }

    private boolean processProject(IResourceDelta delta) throws CoreException
    {
        switch (delta.getKind())
        {
        case IResourceDelta.ADDED:
            return processAddedProject(delta);

        case IResourceDelta.REMOVED:
            return processRemovedProject(delta);

        case IResourceDelta.CHANGED:
            return processChangedProject(delta);

        default:
            return true;
        }
    }

    private boolean processAddedProject(IResourceDelta delta)
        throws CoreException
    {
        IProject project = (IProject)delta.getResource();
        if (project.hasNature(IFooProject.NATURE_ID))
        {
            IFooProject fooProject = FooModelCore.create(project);
            addToModel(fooProject);
        }
        return false;
    }

    private boolean processRemovedProject(IResourceDelta delta)
        throws CoreException
    {
        IProject project = (IProject)delta.getResource();
        if (wasFooProject(project))
        {
            IFooProject fooProject = FooModelCore.create(project);
            removeFromModel(fooProject);
        }
        return false;
    }

    private boolean processChangedProject(IResourceDelta delta)
        throws CoreException
    {
        IProject project = (IProject)delta.getResource();
        IFooProject fooProject = FooModelCore.create(project);

        if ((delta.getFlags() & IResourceDelta.OPEN) != 0)
        {
            if (project.isOpen())
            {
                if (project.hasNature(IFooProject.NATURE_ID))
                {
                    addToModel(fooProject);
                }
            }
            else
            {
                if (wasFooProject(project))
                {
                    removeFromModel(fooProject);
                }
            }
            return false;
        }

        boolean isFooProject = project.hasNature(IFooProject.NATURE_ID);
        if ((delta.getFlags() & IResourceDelta.DESCRIPTION) != 0)
        {
            boolean wasFooProject = wasFooProject(project);
            if (wasFooProject != isFooProject)
            {
                // Foo nature has been added or removed
                if (isFooProject)
                {
                    addToModel(fooProject);
                }
                else
                {
                    removeFromModel(fooProject);
                }
                return false; // when Foo nature is added/removed don't process children
            }
        }

        if (isFooProject)
        {
            Body parentBody = findBody(fooProject.getParent());
            IElement[] children = parentBody.getChildren();
            if (!Arrays.asList(children).contains(fooProject))
                addToModel(fooProject); // in case the project was removed then added then changed

            return true;
        }
        else
        {
            return false;
        }
    }

    private void initOldFooProjectNames() throws CoreException
    {
        IFooProject[] fooProjects = FooModelCore.getFooModel().getFooProjects();
        for (IFooProject fooProject : fooProjects)
        {
            oldFooProjectNames.add(fooProject.getName());
        }
    }

    private boolean wasFooProject(IProject project)
    {
        return oldFooProjectNames.contains(project.getName());
    }

    private void addToModel(IElement element)
    {
        Body parentBody = findBody(Elements.getParent(element));
        if (parentBody != null)
            parentBody.addChild(element);
        close(element);
    }

    private void removeFromModel(IElement element)
    {
        Body parentBody = findBody(Elements.getParent(element));
        if (parentBody != null)
            parentBody.removeChild(element);
        close(element);
    }

    private static Body findBody(IElement element)
    {
        return (Body)((Element)element).findBody_();
    }

    private static void close(IElement element)
    {
        ((Element)element).close_();
    }
}
