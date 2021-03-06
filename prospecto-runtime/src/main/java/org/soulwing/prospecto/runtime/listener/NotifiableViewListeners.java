/*
 * File created on Mar 23, 2016
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
package org.soulwing.prospecto.runtime.listener;

import org.soulwing.prospecto.api.listener.ViewListeners;
import org.soulwing.prospecto.api.listener.ViewNodeEvent;
import org.soulwing.prospecto.api.listener.ViewNodePropertyEvent;
import org.soulwing.prospecto.api.listener.ViewTraversalEvent;

/**
 * A notifiable collection of view listeners.
 *
 * @author Carl Harris
 */
public interface NotifiableViewListeners extends ViewListeners {

  boolean shouldVisitNode(ViewNodeEvent event);

  void nodeVisited(ViewNodeEvent event);

  Object didExtractValue(ViewNodePropertyEvent event);

  Object willInjectValue(ViewNodePropertyEvent event);

  void propertyVisited(ViewNodePropertyEvent event);

  void entityCreated(ViewNodePropertyEvent event);

  void entityDiscarded(ViewNodePropertyEvent event);

  void beforeTraversing(ViewTraversalEvent event);

  void afterTraversing(ViewTraversalEvent event);

}
