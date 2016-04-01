/*
 * File created on Mar 9, 2016
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
package org.soulwing.prospecto.runtime.node;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.soulwing.prospecto.api.UndefinedValue;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewEntity;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.converter.Convertible;

/**
 * A view node that represents a value with a simple textual representation.
 *
 * @author Carl Harris
 */
public class ValueNode extends AbstractViewNode
    implements Convertible, UpdatableViewNode {

  private final TransformationService transformationService;
  private final UpdatableViewNodeTemplate template;

  private Accessor accessor;

  /**
   * Constructs a new instance.
   * @param name node name
   * @param namespace namespace for {@code name}
   */
  public ValueNode(String name, String namespace) {
    this(name, namespace, ConcreteTransformationService.INSTANCE,
        ConcreteUpdatableViewNodeTemplate.INSTANCE);
  }

  ValueNode(String name, String namespace,
      TransformationService transformationService,
      UpdatableViewNodeTemplate template) {
    super(name, namespace, null);
    this.transformationService = transformationService;
    this.template = template;
  }

  @Override
  public Accessor getAccessor() {
    return accessor;
  }

  @Override
  public void setAccessor(Accessor accessor) {
    this.accessor = accessor;
  }

  @Override
  protected List<View.Event> onEvaluate(Object source,
      ScopedViewContext context) throws Exception {

    final Object modelValue = accessor.get(source);

    final Object transformedValue =
        transformationService.valueToExtract(source, modelValue, this, context);

    if (transformedValue == UndefinedValue.INSTANCE) {
      return Collections.emptyList();
    }
    return Collections.singletonList(
        newEvent(View.Event.Type.VALUE, getName(), transformedValue));
  }

  @Override
  public Object toModelValue(final ViewEntity parentEntity,
      final View.Event triggerEvent, Deque<View.Event> events,
      final ScopedViewContext context) throws Exception {

    return template.toModelValue(this, parentEntity,
        context, new Method(parentEntity, triggerEvent, context));
  }

  @Override
  public void inject(Object target, Object value, ScopedViewContext context)
      throws Exception {
    accessor.forSubtype(target.getClass()).set(target, value);
  }

  class Method implements UpdatableViewNodeTemplate.Method {

    private final ViewEntity parentEntity;
    private final View.Event triggerEvent;
    private final ScopedViewContext context;

    Method(ViewEntity parentEntity, View.Event triggerEvent,
        ScopedViewContext context) {
      this.parentEntity = parentEntity;
      this.triggerEvent = triggerEvent;
      this.context = context;
    }

    @Override
    public Object toModelValue() throws Exception {
      return transformationService.valueToInject(
          parentEntity, accessor.getDataType(),
          triggerEvent.getValue(), ValueNode.this, context);
    }

  }

}
