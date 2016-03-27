/*
 * File created on Mar 15, 2016
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.soulwing.prospecto.runtime.listener.ViewNodeEventMatchers.viewNodePropertyEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.converter.ValueTypeConverter;
import org.soulwing.prospecto.runtime.accessor.Accessor;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.listener.NotifiableViewListeners;

/**
 * Unit tests for {@link ObjectNode}.
 *
 * @author Carl Harris
 */
public class ArrayOfValueNodeTest {

  private static final String NAME = "name";
  private static final String ELEMENT_NAME = "elementName";
  private static final String NAMESPACE = "namespace";

  private static final Class<?> MODEL_TYPE = Collection.class;

  private static final Object MODEL = new Object();
  private static final Object ELEMENT = new Object();

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private Accessor accessor;

  @Mock
  private ScopedViewContext viewContext;

  @Mock
  private NotifiableViewListeners listeners;

  @Mock
  private ValueTypeConverter<?> converter;

  @Mock
  private View.Event childEvent;

  private ArrayOfValueNode node = new ArrayOfValueNode(NAME, ELEMENT_NAME,
      NAMESPACE);

  @Before
  public void setUp() throws Exception {
    context.checking(new Expectations() {
      {
        allowing(accessor).getDataType();
        will(returnValue(MODEL_TYPE));
      }
    });

    node.setAccessor(accessor);
  }

  @Test
  public void testOnEvaluate() throws Exception {
    context.checking(new Expectations() {
      {
        oneOf(accessor).get(MODEL);
        will(returnValue(Collections.singletonList(ELEMENT)));
        allowing(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).shouldVisitProperty(
            with(viewNodePropertyEvent(node, MODEL, ELEMENT, viewContext)));
        will(returnValue(true));
        oneOf(listeners).didExtractValue(
            with(viewNodePropertyEvent(node, MODEL, ELEMENT, viewContext)));
        will(returnValue(ELEMENT));

        oneOf(viewContext).getValueTypeConverters();
        will(returnValue(Collections.singletonList(converter)));
        oneOf(converter).supports(ELEMENT.getClass());
        will(returnValue(true));
        oneOf(converter).toValue(ELEMENT);
        will(returnValue(ELEMENT));
      }
    });

    final List<View.Event> events = node.onEvaluate(MODEL, viewContext);
    assertThat(events.size(), is(equalTo(3)));
    assertThat(events.get(0).getType(), is(equalTo(View.Event.Type.BEGIN_ARRAY)));
    assertThat(events.get(0).getName(), is(equalTo(NAME)));
    assertThat(events.get(0).getNamespace(), is(equalTo(NAMESPACE)));
    assertThat(events.get(0).getValue(), is(nullValue()));
    assertThat(events.get(1).getType(), is(equalTo(View.Event.Type.VALUE)));
    assertThat(events.get(1).getName(), is(equalTo(ELEMENT_NAME)));
    assertThat(events.get(1).getNamespace(), is(equalTo(NAMESPACE)));
    assertThat(events.get(1).getValue(), is(ELEMENT));
    assertThat(events.get(2).getType(), is(equalTo(View.Event.Type.END_ARRAY)));
    assertThat(events.get(2).getName(), is(equalTo(NAME)));
    assertThat(events.get(2).getNamespace(), is(equalTo(NAMESPACE)));
    assertThat(events.get(2).getValue(), is(nullValue()));
  }

}
