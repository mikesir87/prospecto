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

import org.soulwing.prospecto.api.MetadataHandler;
import org.soulwing.prospecto.api.node.UrlNode;
import org.soulwing.prospecto.api.node.ViewNodeVisitor;

/**
 * A view node that represents a value that is resolved as a URL.
 *
 * @author Carl Harris
 */
public class ConcreteUrlNode extends ConcreteMetaNode
    implements UrlNode {

  /**
   * Constructs a new instance.
   * @param name node name
   * @param namespace namespace for {@code name}
   * @param handler metadata handler
   */
  public ConcreteUrlNode(String name, String namespace,
      MetadataHandler handler) {
    super(name, namespace, handler);
  }

  @Override
  public Object accept(ViewNodeVisitor visitor, Object state) {
    return visitor.visitUrl(this, state);
  }

}
