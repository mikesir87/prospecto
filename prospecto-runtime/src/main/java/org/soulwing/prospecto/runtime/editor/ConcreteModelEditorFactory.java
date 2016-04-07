/*
 * File created on Mar 21, 2016
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
package org.soulwing.prospecto.runtime.editor;

import org.soulwing.prospecto.api.ModelEditor;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.runtime.applicator.RootViewEventApplicator;
import org.soulwing.prospecto.runtime.applicator.ViewEventApplicator;
import org.soulwing.prospecto.runtime.context.ConcreteScopedViewContextFactory;
import org.soulwing.prospecto.runtime.context.ScopedViewContextFactory;

/**
 * A {@link ModelEditorFactory} that produces {@link ConcreteModelEditor}
 * objects.
 *
 * @author Carl Harris
 */
public class ConcreteModelEditorFactory implements ModelEditorFactory {

  private final ScopedViewContextFactory viewContextFactory;

  public ConcreteModelEditorFactory() {
    this(ConcreteScopedViewContextFactory.INSTANCE);
  }

  ConcreteModelEditorFactory(ScopedViewContextFactory viewContextFactory) {
    this.viewContextFactory = viewContextFactory;
  }

  @Override
  public ModelEditor newEditor(Class<?> modelType,
      ViewEventApplicator applicator, View source, ViewContext context,
      String dataKey) {
    if (!(applicator instanceof RootViewEventApplicator)) {
      throw new IllegalArgumentException("view template is not updatable");
    }
    return new ConcreteModelEditor(modelType,
        (RootViewEventApplicator) applicator, source,
        viewContextFactory.newContext(context), dataKey);
  }

}
