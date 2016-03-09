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
import java.util.List;

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.runtime.event.ConcreteViewEvent;
import org.soulwing.prospecto.runtime.accessor.Accessor;

/**
 * A view node that represents a value with a simple textual representation.
 *
 * @author Carl Harris
 */
public class ValueNode implements EventGeneratingViewNode {

  private final String name;
  private final String namespace;

  private Accessor accessor;

  private ValueNode(ValueNode source, String name) {
    this(name, source.namespace, null);
  }

  public ValueNode(String name, String namespace) {
    this(name, namespace, null);
  }

  public ValueNode(String name, String namespace, Accessor accessor) {
    this.name = name;
    this.namespace = namespace;
    this.accessor = accessor;
  }

  @Override
  public void setAccessor(Accessor accessor) {
    this.accessor = accessor;
  }

  @Override
  public List<View.Event> evaluate(Object source, ViewContext context)
      throws Exception {
    return Collections.<View.Event>singletonList(new ConcreteViewEvent(
        View.Event.Type.VALUE, name, namespace, accessor.get(source)));
  }

  @Override
  public EventGeneratingViewNode copy(String name) {
    return new ValueNode(this, name);
  }

}
