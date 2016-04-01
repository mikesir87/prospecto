/*
 * File created on Mar 14, 2016
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.soulwing.prospecto.runtime.event.ViewEventMatchers.eventOfType;
import static org.soulwing.prospecto.runtime.event.ViewEventMatchers.inNamespace;
import static org.soulwing.prospecto.runtime.event.ViewEventMatchers.whereValue;
import static org.soulwing.prospecto.runtime.event.ViewEventMatchers.withName;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.soulwing.prospecto.api.UrlResolver;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;

/**
 * Unit tests for {@link UrlNode}.
 *
 * @author Carl Harris
 */
public class UrlNodeTest {

  private static final Object MODEL = new Object();
  private static final String NAMESPACE = "namespace";
  private static final Object URL = "url";

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  ScopedViewContext viewContext;

  @Mock
  TransformationService transformationService;

  @Mock
  UrlResolver urlResolver;

  private UrlNode node;

  @Before
  public void setUp() throws Exception {
    node = new UrlNode(UrlNode.DEFAULT_NAME, NAMESPACE, transformationService);
  }

  @Test
  public void testOnEvaluate() throws Exception {
    context.checking(new Expectations() {
      {
        oneOf(viewContext).get(UrlResolver.class);
        will(returnValue(urlResolver));
        oneOf(urlResolver).resolve(node, viewContext);
        will(returnValue(URL));
        oneOf(transformationService).valueToExtract(MODEL, URL, node,
            viewContext);
        will(returnValue(URL));
      }
    });

    assertThat(node.onEvaluate(MODEL, viewContext),
        contains(
            eventOfType(View.Event.Type.URL,
                withName(UrlNode.DEFAULT_NAME),
                inNamespace(NAMESPACE),
                whereValue(is(sameInstance(URL))))));
  }

}
