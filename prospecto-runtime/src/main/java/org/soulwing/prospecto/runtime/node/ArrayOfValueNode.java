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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.listener.ViewNodePropertyEvent;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.accessor.MultiValuedAccessor;
import org.soulwing.prospecto.runtime.accessor.MultiValuedAccessorFactory;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.converter.ConverterSupport;
import org.soulwing.prospecto.runtime.converter.Convertible;

/**
 * A view node that represents an array of values.
 *
 * @author Carl Harris
 */
public class ArrayOfValueNode extends AbstractViewNode implements Convertible {

  private final String elementName;

  private MultiValuedAccessor accessor;

  /**
   * Constructs a new instance.
   * @param name node name
   * @param elementName name for the elements of the array
   * @param namespace namespace for {@code name} and {@code elementName}
   */
  public ArrayOfValueNode(String name, String elementName, String namespace) {
    super(name, namespace, null);
    this.elementName = elementName;
  }

  /**
   * Gets the {@code elementName} property.
   * @return property value
   */
  public String getElementName() {
    return elementName;
  }

  @Override
  public void setAccessor(Accessor accessor) {
    super.setAccessor(accessor);
    this.accessor = MultiValuedAccessorFactory.newAccessor(accessor);
  }

  @Override
  public List<View.Event> onEvaluate(Object model, ScopedViewContext context)
      throws Exception {
    final List<View.Event> events = new LinkedList<>();
    final Iterator<Object> i = getModelIterator(model);

    events.add(newEvent(View.Event.Type.BEGIN_ARRAY));
    while (i.hasNext()) {
      final Object elementModel = i.next();
      final ViewNodePropertyEvent elementEvent = new ViewNodePropertyEvent(this,
          model, elementModel, context);
      if (context.getListeners().shouldVisitProperty(elementEvent)) {
        events.add(newEvent(View.Event.Type.VALUE, elementName,
            toViewValue(
                context.getListeners().didExtractValue(elementEvent),
                context)));
      }
    }
    events.add(newEvent(View.Event.Type.END_ARRAY));

    return events;
  }

  protected Iterator<Object> getModelIterator(Object source) throws Exception {
    return accessor.iterator(source);
  }

  private Object toViewValue(Object model, ScopedViewContext context)
      throws Exception {
    return ConverterSupport.toViewValue(model, this, context);
  }

}
