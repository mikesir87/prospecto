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
package org.soulwing.prospecto.runtime.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumSet;

import org.soulwing.prospecto.api.AccessMode;

/**
 * A accessor that uses JavaBeans-style accessor methods via the Reflection API.
 *
 * @author Carl Harris
 */
class PropertyAccessor extends AbstractAccessor {

  private final Method getter;
  private final Method setter;

  public PropertyAccessor(Method getter, Method setter,
      EnumSet<AccessMode> accessModes) {
    super(accessModes);
    this.getter = getter;
    this.setter = setter;
    if (getter != null) {
      this.getter.setAccessible(true);
    }
    if (setter != null) {
      this.setter.setAccessible(true);
    }

  }

  @Override
  public Class<?> getDataType() {
    return getter.getReturnType();
  }

  @Override
  public Object get(Object source)
      throws IllegalAccessException, InvocationTargetException {
    if (getter == null) {
      throw new UnsupportedOperationException(AccessMode.READ
          + " access is not supported by this accessor");
    }
    return getter.invoke(source);
  }

  @Override
  public void set(Object target, Object value)
      throws IllegalAccessException, InvocationTargetException {
    if (setter == null) {
      throw new UnsupportedOperationException(AccessMode.WRITE
          + " access is not supported by this accessor");
    }
    setter.invoke(target, value);
  }

}
