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

import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.node.UrlNode;
import org.soulwing.prospecto.runtime.event.ConcreteViewEvent;
import org.soulwing.prospecto.runtime.listener.ConcreteTransformationService;
import org.soulwing.prospecto.runtime.listener.TransformationService;

/**
 * A generator for the event associated with a URL node.
 *
 * @author Carl Harris
 */
class UrlGenerator extends AbstractMetaGenerator<UrlNode> {

  UrlGenerator(UrlNode node) {
    this(node, ConcreteTransformationService.INSTANCE);
  }

  UrlGenerator(UrlNode node, TransformationService transformationService) {
    super(node, transformationService);
  }

  View.Event newEvent(Object value) {
    return new ConcreteViewEvent(View.Event.Type.URL, node.getName(),
        node.getNamespace(), value);
  }

}
