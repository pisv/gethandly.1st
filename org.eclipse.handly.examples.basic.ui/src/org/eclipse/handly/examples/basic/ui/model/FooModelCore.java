/*******************************************************************************
 * Copyright (c) 2014 1C LLC.
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
package org.eclipse.handly.examples.basic.ui.model;

import org.eclipse.core.resources.IProject;
import org.eclipse.handly.internal.examples.basic.ui.model.FooModelManager;

/**
 * Facade to the Foo Model.
 */
public class FooModelCore
{
    /**
     * Returns the root Foo element.
     *
     * @return the root Foo element (never <code>null</code>)
     */
    public static IFooModel getFooModel()
    {
        return FooModelManager.INSTANCE.getModel();
    }
    
    /**
     * Returns the Foo project corresponding to the given project.
     * <p>
     * Note that no check is done at this time on the existence 
     * or the nature of this project.
     * </p>
     *
     * @param project the given project (maybe <code>null</code>)
     * @return the Foo project corresponding to the given project, 
     *  or <code>null</code> if the given project is <code>null</code>
     */
    public static IFooProject create(IProject project)
    {
        if (project == null)
            return null;
        return getFooModel().getFooProject(project.getName());
    }
    
    private FooModelCore()
    {
    }
}
