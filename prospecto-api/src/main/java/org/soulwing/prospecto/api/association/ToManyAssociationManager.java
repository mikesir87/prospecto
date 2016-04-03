/*
 * File created on Mar 29, 2016
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
package org.soulwing.prospecto.api.association;

import java.util.Iterator;

import org.soulwing.prospecto.api.ViewEntity;

/**
 * An object that during model update manages the association between an object
 * and a collection composed in the object.
 * <p>
 * A manager is used, by the underlying framework, in a transactional fashion.
 * Before invoking methods to update the associated collection, the framework
 * will first invoke the {@link #begin(Object)} method to inform the manager
 * that a sequence of collection-operations will be performed. When all
 * update operations have been performed, the framework will notify the manager
 * that the update is complete by invoking the {@link #end(Object)} method.
 *
 * @author Carl Harris
 */
public interface ToManyAssociationManager<T, E>
    extends AssociationManager<T, E> {

  /**
   * Gets an iterator for the associated collection.
   * @param owner association owner
   * @return collection iterator
   * @throws Exception
   */
  Iterator<E> iterator(T owner) throws Exception;

  /**
   * Gets the size of the associated collection.
   * @param owner association owner
   * @return collection size
   * @throws Exception
   */
  int size(T owner) throws Exception;

  E findAssociate(T owner, ViewEntity elementEntity) throws Exception;

  /**
   * Adds an element to the associated collection.
   * @param owner association owner
   * @param element the element to add to the collection
   * @throws Exception
   */
  void add(T owner, E element) throws Exception;

  /**
   * Removes an element from the associated collection.
   * @param owner association owner
   * @param element the element to remove from the collection
   * @return {@code true} if an element was removed
   * @throws Exception
   */
  boolean remove(T owner, E element) throws Exception;

  /**
   * Begins a transaction for updating the associated collection.
   * @param owner association owner
   */
  void begin(T owner) throws Exception;

  /**
   * Ends a transaction that has updated the associated collection.
   * @param owner association owner
   */
  void end(T owner) throws Exception;

}
