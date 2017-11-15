/*******************************************************************************
 * Copyright (c) 2014, 2017 1C-Soft LLC and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Vladimir Piskarev (1C) - initial API and implementation
 *******************************************************************************/
package org.eclipse.handly.internal.examples.basic.ui.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.handly.context.IContext;
import org.eclipse.handly.examples.basic.ui.model.IFooProject;
import org.eclipse.handly.model.impl.support.Element;

/**
 * Represents a Foo project.
 */
public class FooProject
    extends Element
    implements IFooProject, IFooElementInternal
{
    private final IProject project;

    /**
     * Constructs a handle for a Foo project with the given parent element 
     * and the given underlying workspace project.
     * 
     * @param parent the parent of the element (not <code>null</code>)
     * @param project the workspace project underlying the element 
     *  (not <code>null</code>)
     */
    public FooProject(FooModel parent, IProject project)
    {
        super(parent, project.getName());
        if (parent == null)
            throw new IllegalArgumentException();
        this.project = project;
    }

    @Override
    public IProject getProject()
    {
        return project;
    }

    @Override
    public IResource getResource_()
    {
        return project;
    }

    @Override
	public void validateExistence_(IContext context) throws CoreException
    {
        if (!project.hasNature(NATURE_ID))
            throw newDoesNotExistException_();
    }

    @Override
	public void buildStructure_(IContext context, IProgressMonitor monitor)
        throws CoreException
    {
    }
}
