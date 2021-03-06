/*
 * File created on Apr 8, 2016
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
package org.soulwing.prospecto.runtime.meta;

import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.meta.MetadataHandler;
import org.soulwing.prospecto.api.template.MetaNode;
import org.soulwing.prospecto.api.url.UrlResolver;

/**
 * A {@link MetadataHandler} that resolves URLs.
 *
 * @author Carl Harris
 */
public class UrlResolvingMetadataHandler implements MetadataHandler {

  public static UrlResolvingMetadataHandler INSTANCE =
      new UrlResolvingMetadataHandler();

  private UrlResolvingMetadataHandler() {}

  @Override
  public java.lang.Object produceValue(MetaNode node, Object parentModel,
      ViewContext context) throws Exception {
    return context.get(UrlResolver.class).resolve(node, context);
  }

  @Override
  public void consumeValue(MetaNode node, Object parentModel, Object value,
      ViewContext context) throws Exception {
  }

}
