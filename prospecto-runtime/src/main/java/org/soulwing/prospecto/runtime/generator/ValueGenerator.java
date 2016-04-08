/*
 * File created on Apr 6, 2016
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
package org.soulwing.prospecto.runtime.generator;

import java.util.Collections;
import java.util.List;

import org.soulwing.prospecto.api.UndefinedValue;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.template.ValueNode;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.event.ConcreteViewEvent;
import org.soulwing.prospecto.runtime.listener.ConcreteTransformationService;
import org.soulwing.prospecto.runtime.listener.TransformationService;

/**
 * A generator for the event associated with a value node.
 *
 * @author Carl Harris
 */
class ValueGenerator extends AbstractViewEventGenerator<ValueNode> {

  private final TransformationService transformationService;

  ValueGenerator(ValueNode node) {
    this(node, ConcreteTransformationService.INSTANCE);
  }

  ValueGenerator(ValueNode node, TransformationService transformationService) {
    super(node);
    this.transformationService = transformationService;
  }

  @Override
  List<View.Event> onGenerate(Object owner, ScopedViewContext context)
      throws Exception {

    final Object model = node.getValue(owner);

    final Object transformedValue =
        transformationService.valueToExtract(owner, model, node, context);

    if (transformedValue == UndefinedValue.INSTANCE) {
      return Collections.emptyList();
    }

    return Collections.singletonList((View.Event)
        new ConcreteViewEvent(View.Event.Type.VALUE, node.getName(),
            node.getNamespace(), transformedValue));
  }

}
