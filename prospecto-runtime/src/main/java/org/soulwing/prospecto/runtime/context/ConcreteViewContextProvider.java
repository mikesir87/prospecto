/*
 * File created on Mar 10, 2016
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
package org.soulwing.prospecto.runtime.context;

import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.options.Options;
import org.soulwing.prospecto.spi.ViewContextProvider;

/**
 * A trivial {@link ViewContextProvider} implementation.
 *
 * @author Carl Harris
 */
public class ConcreteViewContextProvider implements ViewContextProvider {

  @Override
  public ViewContext newContext(Options options) {
    return new ConcreteViewContext(options);
  }

}
