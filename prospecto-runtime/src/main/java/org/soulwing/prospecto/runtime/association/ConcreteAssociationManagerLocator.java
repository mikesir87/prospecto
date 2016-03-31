/*
 * File created on Mar 31, 2016
 *
 * Copyright (c) 2016 Carl Harris, Jr
 * and others as noted
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.soulwing.prospecto.runtime.association;

import org.soulwing.prospecto.api.ModelEditorException;
import org.soulwing.prospecto.api.association.AssociationManager;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.node.AbstractViewNode;

/**
 * An {@link AssociationManager} implementation.
 *
 * @author Carl Harris
 */
class ConcreteAssociationManagerLocator
    implements AssociationManagerLocator {

  @Override
  @SuppressWarnings("unchecked")
  public <M extends AssociationManager> M findManager(
      Class<M> managerClass, M defaultManager,
      AbstractViewNode node, ScopedViewContext context) {

    assert node.getParent() != null;
    final Class<?> ownerType = node.getParent().getModelType();
    final Class<?> associateType = node.getModelType();

    AssociationManager manager = node.get(managerClass);

    if (manager != null) {
      if (!managerClass.isInstance(manager)
          || !manager.supports(ownerType, associateType)) {
        throw new ModelEditorException(
            "association manager does not support expected types");
      }
      return (M) manager;
    }

    manager = context.getAssociationManagers().findManager(managerClass,
        ownerType, associateType);

    if (manager != null) return (M) manager;

    return defaultManager;
  }

}
