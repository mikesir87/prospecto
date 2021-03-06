/*
 * File created on Mar 12, 2016
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
package org.soulwing.prospecto.jaxrs.runtime.discovery;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.soulwing.prospecto.jaxrs.api.ModelPathSpec;
import org.soulwing.prospecto.jaxrs.api.PathTemplateResolver;
import org.soulwing.prospecto.jaxrs.api.TemplateResolver;
import org.soulwing.prospecto.jaxrs.runtime.ReflectionService;
import org.soulwing.prospecto.jaxrs.runtime.ResourceConfigurationException;
import org.soulwing.prospecto.jaxrs.runtime.ResourceDescriptor;
import org.soulwing.prospecto.jaxrs.runtime.path.ModelPath;

/**
 * A {@link ResourceTypeIntrospector} that utilizes reflection.
 *
 * @author Carl Harris
 */
class ReflectionResourceTypeIntrospector implements ResourceTypeIntrospector {

  private final ResourceDescriptorFactory descriptorFactory;
  private final ResourceMethodIntrospector methodIntrospector;

  ReflectionResourceTypeIntrospector() {
    this(new SimpleResourceDescriptorFactory(),
        new ReflectionResourceMethodIntrospector());
  }

  ReflectionResourceTypeIntrospector(
      ResourceDescriptorFactory descriptorFactory,
      ResourceMethodIntrospector methodIntrospector) {
    this.descriptorFactory = descriptorFactory;
    this.methodIntrospector = methodIntrospector;
  }

  @Override
  public Collection<ResourceDescriptor> describe(Class<?> type,
      String resourcePath, ModelPath modelPath,
      TemplateResolver templateResolver, ReflectionService reflectionService)
      throws ResourceConfigurationException {

    if (reflectionService.isAbstractType(type)) {
      throw new ResourceConfigurationException(
          "cannot describe abstract resource " + typeToString(type));
    }

    final ModelPathSpec modelPathSpec = reflectionService.getAnnotation(type,
        ModelPathSpec.class);

    final Collection<ResourceDescriptor> descriptors = new ArrayList<>();

    if (modelPathSpec != null) {

      modelPath = modelPath.concat(modelPathSpec);

      if (modelPathSpec.descriptor()) {

        if (templateResolver == null) {
          throw new ResourceConfigurationException(
              "no template resolver for " + typeToString(type));
        }

        final PathTemplateResolver pathTemplateResolver =
            TemplateResolverUtils.newResolver(templateResolver.value());

        descriptors.add(descriptorFactory.newDescriptor(type,
            resourcePath, modelPath, pathTemplateResolver));
      }
    }

    for (Method method : reflectionService.getMethods(type)) {
      if (!reflectionService.getReturnType(method).equals(void.class)) {
        descriptors.addAll(methodIntrospector.describe(
            method, resourcePath, modelPath, templateResolver,
             reflectionService, this));
      }
    }

    return descriptors;
  }

  private String typeToString(Class<?> type) {
    StringBuilder sb = new StringBuilder();
    sb.append("type ");
    sb.append(type.getSimpleName());
    sb.append(" (");
    sb.append(type.getName());
    sb.append(")");
    return sb.toString();
  }

}
