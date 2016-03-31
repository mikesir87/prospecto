/*
 * File created on Mar 31, 2016
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
package org.soulwing.prospecto.runtime.association;

import java.util.List;

import org.soulwing.prospecto.api.association.ToManyAssociationManager;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.entity.MutableViewEntity;
import org.soulwing.prospecto.runtime.node.AbstractViewNode;

/**
 * An updater for a to-many association between a target model object and
 * one of its properties.
 *
 * @author Carl Harris
 */
public interface ToManyAssociationUpdater {

  void update(AbstractViewNode node, Object target,
      List<MutableViewEntity> entities, ToManyAssociationManager defaultManager,
      ScopedViewContext context) throws Exception;

}