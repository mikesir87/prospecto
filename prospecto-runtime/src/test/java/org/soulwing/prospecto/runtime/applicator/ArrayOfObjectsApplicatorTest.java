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

import java.util.Collections;

import org.soulwing.prospecto.api.template.ArrayOfObjectsNode;

/**
 * Unit tests for {@link ArrayOfValuesApplicator}.
 *
 * @author Carl Harris
 */
public class ArrayOfObjectsApplicatorTest
    extends AbstractArrayOfObjectsApplicatorTest<ArrayOfObjectsNode> {

  @Override
  ArrayOfObjectsNode newNode() {
    return context.mock(ArrayOfObjectsNode.class);
  }

  @Override
  AbstractViewEventApplicator<ArrayOfObjectsNode> newApplicator(
      ArrayOfObjectsNode node) {
    return new ArrayOfObjectsApplicator(node, Collections.singletonList(child),
        entityFactory, transformationService, associationUpdater,
        applicatorLocator);
  }

}
