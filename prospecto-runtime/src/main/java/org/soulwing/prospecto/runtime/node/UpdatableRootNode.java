/*
 * File created on Mar 25, 2016
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

import java.util.Deque;

import org.soulwing.prospecto.api.ModelEditorException;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;

/**
 * An updatable view node at the root of a view.
 *
 * @author Carl Harris
 */
public interface UpdatableRootNode extends UpdatableViewNode {

  Class<?> getModelType();

  Object create(Deque<View.Event> events,
      ScopedViewContext context) throws ModelEditorException;

  void update(Object target, Deque<View.Event> events,
      ScopedViewContext context) throws ModelEditorException;

}
