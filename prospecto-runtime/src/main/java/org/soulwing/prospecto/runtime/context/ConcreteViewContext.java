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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.converter.ValueTypeConverter;
import org.soulwing.prospecto.api.scope.MutableScope;
import org.soulwing.prospecto.api.scope.Scope;
import org.soulwing.prospecto.api.scope.Scopes;
import org.soulwing.prospecto.runtime.association.AssociationManagerService;
import org.soulwing.prospecto.runtime.association.LinkedListAssociationManagerService;
import org.soulwing.prospecto.runtime.listener.LinkedListNotifiableViewListeners;
import org.soulwing.prospecto.runtime.listener.NotifiableViewListeners;
import org.soulwing.prospecto.runtime.reference.LinkedListReferenceResolverService;
import org.soulwing.prospecto.runtime.reference.ReferenceResolverService;
import org.soulwing.prospecto.runtime.scope.ConcreteMutableScope;
import org.soulwing.prospecto.runtime.scope.LinkedListScopes;
import org.soulwing.prospecto.runtime.util.StringUtil;

/**
 * A {@link ScopedViewContext} implementation.
 * evaluation.
 *
 * @author Carl Harris
 */
public class ConcreteViewContext implements ScopedViewContext {

  private final Scopes scopes = new LinkedListScopes();

  private final NotifiableViewListeners listeners =
      new LinkedListNotifiableViewListeners();

  private final List<ValueTypeConverter<?>> valueTypeConverters =
      new ArrayList<>();

  private final ReferenceResolverService referenceResolvers =
      new LinkedListReferenceResolverService();

  private final AssociationManagerService collectionManagers =
      new LinkedListAssociationManagerService();

  static class ScopeFrame extends ConcreteMutableScope {

    private final String name;
    private final Class<?> modelClass;

    ScopeFrame(String name, Class<?> modelClass) {
      this.name = name;
      this.modelClass = modelClass;
    }

    public String getName() {
      return name;
    }

    public Class<?> getModelClass() {
      return modelClass;
    }

  }

  private final Deque<ScopeFrame> scopeStack = new LinkedList<>();

  public ConcreteViewContext() {
  }

  public ConcreteViewContext(ViewContext source) {
    this.scopes.toList().addAll(source.getScopes().toList());
    this.listeners.toList().addAll(source.getListeners().toList());
    this.valueTypeConverters.addAll(source.getValueTypeConverters());
    this.referenceResolvers.toList().addAll(source.getReferenceResolvers().toList());
    this.collectionManagers.toList().addAll(source.getAssociationManagers().toList());
  }

  @Override
  public MutableScope newScope() {
    return new ConcreteMutableScope();
  }

  @Override
  public MutableScope appendScope() {
    MutableScope scope = newScope();
    scopes.append(scope);
    return scope;
  }

  @Override
  public MutableScope prependScope() {
    MutableScope scope = newScope();
    scopes.prepend(scope);
    return scope;
  }

  @Override
  public Scopes getScopes() {
    return scopes;
  }

  @Override
  public NotifiableViewListeners getListeners() {
    return listeners;
  }

  @Override
  public List<ValueTypeConverter<?>> getValueTypeConverters() {
    return valueTypeConverters;
  }

  @Override
  public ReferenceResolverService getReferenceResolvers() {
    return referenceResolvers;
  }

  @Override
  public AssociationManagerService getAssociationManagers() {
    return collectionManagers;
  }

  @Override
  public List<String> currentViewPath() {
    final List<String> nodes = new ArrayList<>();
    final Iterator<ScopeFrame> frames = scopeStack.descendingIterator();
    while (frames.hasNext()) {
      final ScopeFrame frame = frames.next();
      nodes.add(frame.getName());
    }
    return nodes;
  }

  @Override
  public String currentViewPathAsString() {
    return PATH_DELIMITER + StringUtil.join(currentViewPath(), PATH_DELIMITER);
  }

  @Override
  public List<Class<?>> currentModelPath() {
    final List<Class<?>> nodes = new ArrayList<>();
    final Iterator<ScopeFrame> frames = scopeStack.descendingIterator();
    while (frames.hasNext()) {
      final ScopeFrame frame = frames.next();
      if (frame.getModelClass() != null) {
        nodes.add(frame.getModelClass());
      }
    }
    return nodes;
  }

  @Override
  public void push(String name, Class<?> modelType) {
    scopeStack.push(new ScopeFrame(name, modelType));
  }

  @Override
  public void pop() {
    scopeStack.pop();
  }

  @Override
  public <T> T get(Class<T> type) {
    final T obj = getOptional(type);
    if (obj == null) {
      throw new NullPointerException("object of type "
          + type.getName() + " not found");
    }
    return obj;
  }

  @Override
  public <T> T getOptional(Class<T> type) {
    T obj = null;
    final Iterator<ScopeFrame> frames = scopeStack.iterator();
    while (obj == null && frames.hasNext()) {
      obj = frames.next().get(type);
    }
    if (obj == null) {
      final Iterator<Scope> scopes = this.scopes.toList().iterator();
      while (obj == null && scopes.hasNext()) {
        obj = scopes.next().get(type);
      }
    }
    return obj;
  }

  @Override
  public <T> T get(String name, Class<T> type) {
    final T obj = getOptional(name, type);
    if (obj == null) {
      throw new NullPointerException("object of type "
          + type.getName() + " with name '" + name + "' not found");
    }
    return obj;
  }

  @Override
  public <T> T getOptional(String name, Class<T> type) {
    T obj = null;
    final Iterator<ScopeFrame> frames = scopeStack.descendingIterator();
    while (obj == null && frames.hasNext()) {
      obj = frames.next().get(name, type);
    }
    if (obj == null) {
      final Iterator<Scope> scopes = this.scopes.toList().iterator();
      while (obj == null && scopes.hasNext()) {
        obj = scopes.next().get(name, type);
      }
    }
    return obj;
  }

  @Override
  public void put(Object obj) {
    scopeStack.peek().put(obj);
  }

  @Override
  public void put(String name, Object obj) {
    scopeStack.peek().put(name, obj);
  }

  @Override
  public void putAll(Collection<?> objs) {
    scopeStack.peek().putAll(objs);
  }

  @Override
  public void putAll(Map<String, ?> objs) {
    scopeStack.peek().putAll(objs);
  }

  @Override
  public boolean remove(Object value) {
    return scopeStack.peek().remove(value);
  }

}

