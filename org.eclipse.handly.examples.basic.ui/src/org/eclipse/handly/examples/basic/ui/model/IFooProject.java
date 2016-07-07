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
package org.eclipse.handly.examples.basic.ui.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.handly.internal.examples.basic.ui.model.FooProjectNature;
import org.eclipse.handly.model.IElementExtension;

/**
 * Represents a Foo project.
 */
public interface IFooProject
    extends IElementExtension
{
    /**
     * Foo project nature id.
     */
    String NATURE_ID = FooProjectNature.ID;

    @Override
    default IFooModel getParent()
    {
        return (IFooModel)IElementExtension.super.getParent();
    }

    /**
     * Returns the <code>IProject</code> on which this <code>IFooProject</code>
     * was created. This is handle-only method.
     *
     * @return the <code>IProject</code> on which this <code>IFooProject</code>
     *  was created (never <code>null</code>)
     */
    IProject getProject();
}
