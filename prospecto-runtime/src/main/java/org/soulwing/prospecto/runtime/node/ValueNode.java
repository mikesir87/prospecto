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

/**
 * A view node that represents a value with a simple textual representation.
 *
 * @author Carl Harris
 */
public class ValueNode extends ValueViewNode {

  /**
   * Constructs a new instance.
   * @param name node name
   * @param namespace namespace for {@code name}
   */
  public ValueNode(String name, String namespace) {
    super(name, namespace);
  }

  /**
   * Constructs a copy of a node, composed with a new name.
   * @param source source node that will be copied
   * @param name name to compose in the new node
   */
  private ValueNode(ValueNode source, String name) {
    super(name, source.getNamespace());
  }

  @Override
  public ValueNode copy(String name) {
    return new ValueNode(this, name);
  }

}
