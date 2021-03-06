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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.soulwing.prospecto.api.ViewContext;
import org.soulwing.prospecto.api.template.MetaNode;
import org.soulwing.prospecto.api.url.UrlResolver;

/**
 * Unit tests for {@link UrlResolvingMetadataHandler}.
 *
 * @author Carl Harris
 */
public class UrlResolvingMetadataHandlerTest {

  private static final Object MODEL = new Object();
  private static final String URL = "url";

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private ViewContext viewContext;

  @Mock
  private UrlResolver resolver;

  @Mock
  private MetaNode node;

  @Test
  public void testProduceValue() throws Exception {
    context.checking(new Expectations() {
      {
        oneOf(viewContext).get(UrlResolver.class);
        will(returnValue(resolver));
        oneOf(resolver).resolve(node, viewContext);
        will(returnValue(URL));
      }
    });

    assertThat((String) UrlResolvingMetadataHandler.INSTANCE.produceValue(
        node, MODEL, viewContext), is(equalTo(URL)));
  }

}
