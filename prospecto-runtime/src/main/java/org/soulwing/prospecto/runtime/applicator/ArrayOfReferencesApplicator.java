/*
 * File created on Apr 7, 2016
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
package org.soulwing.prospecto.runtime.applicator;

import java.util.List;

import org.soulwing.prospecto.api.node.ArrayOfReferencesNode;
import org.soulwing.prospecto.runtime.association.ReferenceCollectionToManyAssociationUpdater;
import org.soulwing.prospecto.runtime.association.ToManyAssociationUpdater;
import org.soulwing.prospecto.runtime.entity.ConcreteViewEntityFactory;
import org.soulwing.prospecto.runtime.entity.ViewEntityFactory;
import org.soulwing.prospecto.runtime.listener.ConcreteTransformationService;
import org.soulwing.prospecto.runtime.listener.TransformationService;

/**
 * An applicator for an {@link ArrayOfReferencesNode}.
 *
 * @author Carl Harris
 */
class ArrayOfReferencesApplicator
    extends AbstractArrayOfObjectsApplicator<ArrayOfReferencesNode> {

  ArrayOfReferencesApplicator(ArrayOfReferencesNode node,
      List<ViewEventApplicator> children) {
    this(node, children,
        ConcreteViewEntityFactory.INSTANCE,
        ConcreteTransformationService.INSTANCE,
        ReferenceCollectionToManyAssociationUpdater.INSTANCE);
  }

  ArrayOfReferencesApplicator(ArrayOfReferencesNode node,
      List<ViewEventApplicator> children, ViewEntityFactory entityFactory,
      TransformationService transformationService,
      ToManyAssociationUpdater associationUpdater) {
    super(node, children, entityFactory, transformationService,
        associationUpdater);
  }

}
