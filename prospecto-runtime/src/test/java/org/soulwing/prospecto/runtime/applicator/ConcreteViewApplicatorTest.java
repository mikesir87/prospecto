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
package org.soulwing.prospecto.runtime.applicator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.soulwing.prospecto.api.View.Event.Type.BEGIN_ARRAY;
import static org.soulwing.prospecto.api.View.Event.Type.BEGIN_OBJECT;
import static org.soulwing.prospecto.api.View.Event.Type.END_ARRAY;
import static org.soulwing.prospecto.api.View.Event.Type.END_OBJECT;
import static org.soulwing.prospecto.api.View.Event.Type.VALUE;
import static org.soulwing.prospecto.testing.matcher.ViewEventMatchers.eventOfType;
import static org.soulwing.prospecto.testing.matcher.ViewEventMatchers.withName;

import java.util.Deque;

import org.jmock.Expectations;
import org.jmock.auto.Mock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.soulwing.prospecto.api.View;
import org.soulwing.prospecto.api.ViewInputException;
import org.soulwing.prospecto.api.factory.ObjectFactory;
import org.soulwing.prospecto.api.listener.ViewTraversalEvent;
import org.soulwing.prospecto.runtime.context.ScopedViewContext;
import org.soulwing.prospecto.runtime.entity.InjectableViewEntity;
import org.soulwing.prospecto.runtime.factory.ObjectFactoryService;
import org.soulwing.prospecto.runtime.listener.NotifiableViewListeners;
import org.soulwing.prospecto.runtime.view.ViewBuilder;

/**
 * Unit tests for {@link ConcreteViewApplicator}.
 *
 * @author Carl Harris
 */
public class ConcreteViewApplicatorTest {

  private static final String NAME = "name";
  private static final String DATA_KEY = "data";
  private static final ViewTraversalEvent EVENT =
      new ViewTraversalEvent(null, null, null);

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();

  @Mock
  private ScopedViewContext viewContext;

  @Mock
  private ObjectFactoryService objectFactory;

  @Mock
  private RootViewEventApplicator root;

  @Mock
  private MockModel model;

  @Mock
  private InjectableViewEntity entity;

  @Mock
  private NotifiableViewListeners listeners;

  private ConcreteViewApplicator editor;

  @Test
  @SuppressWarnings("unchecked")
  public void testCreateWithUnenvelopedFlatView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .end();

    final ConcreteViewApplicator editor = new ConcreteViewApplicator(MockModel.class,
        root, source, viewContext, null, EVENT);

    context.checking(new Expectations() {
      {
        allowing(viewContext).getObjectFactories();
        will(returnValue(objectFactory));
        allowing(entity).getType();
        will(returnValue(MockModel.class));
        oneOf(objectFactory).newInstance(MockModel.class);
        will(returnValue(model));
        oneOf(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).afterTraversing(EVENT);
        oneOf(root).toModelValue(
            with(nullValue(InjectableViewEntity.class)),
            with(eventOfType(BEGIN_OBJECT)),
            (Deque<View.Event>) with(contains(
                eventOfType(VALUE, withName(NAME)),
                eventOfType(END_OBJECT)
                )
            ),
            with(viewContext));
        will(returnValue(entity));
        oneOf(root).apply(entity, model, viewContext);
      }
    });

    assertThat(editor.create(), is(sameInstance((Object) model)));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateUnenvelopedFlatView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .end();

    final ConcreteViewApplicator editor = new ConcreteViewApplicator(MockModel.class,
        root, source, viewContext, null, EVENT);

    context.checking(new Expectations() {
      {
        oneOf(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).afterTraversing(EVENT);
        oneOf(root).toModelValue(
            with(nullValue(InjectableViewEntity.class)),
            with(eventOfType(BEGIN_OBJECT)),
            (Deque<View.Event>) with(contains(
                    eventOfType(VALUE, withName(NAME)),
                    eventOfType(END_OBJECT)
                )
            ),
            with(viewContext));
        will(returnValue(entity));
        oneOf(root).apply(entity, model, viewContext);
      }
    });

    editor.update(model);

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateUnenvelopedNestedView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .type(END_OBJECT)
        .end();

    final ConcreteViewApplicator editor = new ConcreteViewApplicator(MockModel.class,
        root, source, viewContext, null, EVENT);

    context.checking(new Expectations() {
      {
        oneOf(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).afterTraversing(EVENT);
        oneOf(root).toModelValue(
            with(nullValue(InjectableViewEntity.class)),
            with(eventOfType(BEGIN_OBJECT)),
            (Deque<View.Event>) with(
                contains(
                    eventOfType(BEGIN_OBJECT),
                    eventOfType(VALUE, withName(NAME)),
                    eventOfType(END_OBJECT),
                    eventOfType(END_OBJECT)
                )
            ),
            with(viewContext));
        will(returnValue(entity));
        oneOf(root).apply(entity, model, viewContext);
      }
    });

    editor.update(model);

  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWithUnexpectedObject() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(BEGIN_OBJECT).name("other" + DATA_KEY)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source,
        viewContext, DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWithUnexpectedArray() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(BEGIN_ARRAY)
        .type(VALUE).name(NAME)
        .type(END_ARRAY)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWithNonObjectData() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(BEGIN_ARRAY).name(DATA_KEY)
        .type(VALUE).name(NAME)
        .type(END_ARRAY)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWhenDataKeyNotFound() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWhenNotWellFormed() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateWhenNotWellFormed() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        null, EVENT).update(model);
  }


  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopeWithEmptyView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateWithArrayView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_ARRAY)
        .type(END_ARRAY)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateWithValueView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(VALUE)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateWithNameMismatch() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT).name(NAME)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }

  @Test(expected = ViewInputException.class)
  @SuppressWarnings("unchecked")
  public void testUpdateWithNamespaceMismatch() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT).namespace(NAME)
        .type(END_OBJECT)
        .end();

    new ConcreteViewApplicator(MockModel.class, root, source, viewContext,
        DATA_KEY, EVENT).update(model);
  }


  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopedFlatView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name("ignored")
        .type(BEGIN_OBJECT).name(DATA_KEY)
        .type(VALUE).name(NAME)
        .type(END_OBJECT).name(DATA_KEY)
        .type(VALUE).name("ignored")
        .type(END_OBJECT)
        .end();

    final ConcreteViewApplicator editor = new ConcreteViewApplicator(
        MockModel.class, root, source, viewContext, DATA_KEY, EVENT);

    context.checking(new Expectations() {
      {
        oneOf(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).afterTraversing(EVENT);
        oneOf(root).toModelValue(
            with(nullValue(InjectableViewEntity.class)),
            with(eventOfType(BEGIN_OBJECT)),
            (Deque<View.Event>) with(
                contains(
                    eventOfType(VALUE, withName(NAME)),
                    eventOfType(END_OBJECT))
            ),
            with(viewContext));
        will(returnValue(entity));
        oneOf(root).apply(entity, model, viewContext);
      }
    });

    editor.update(model);

  }

  @Test
  @SuppressWarnings("unchecked")
  public void testUpdateEnvelopedNestedView() throws Exception {

    final View source = ViewBuilder
        .begin()
        .type(BEGIN_OBJECT)
        .type(VALUE).name("ignored")
        .type(BEGIN_OBJECT).name(DATA_KEY)
        .type(BEGIN_OBJECT)
        .type(VALUE).name(NAME)
        .type(END_OBJECT)
        .type(END_OBJECT).name(DATA_KEY)
        .type(VALUE).name("ignored")
        .type(END_OBJECT)
        .end();

    final ConcreteViewApplicator editor = new ConcreteViewApplicator(
        MockModel.class, root, source, viewContext, DATA_KEY, EVENT);

    context.checking(new Expectations() {
      {
        oneOf(viewContext).getListeners();
        will(returnValue(listeners));
        oneOf(listeners).afterTraversing(EVENT);
        oneOf(root).toModelValue(
            with(nullValue(InjectableViewEntity.class)),
            with(eventOfType(BEGIN_OBJECT)),
            (Deque<View.Event>) with(
                contains(
                    eventOfType(BEGIN_OBJECT),
                    eventOfType(VALUE, withName(NAME)),
                    eventOfType(END_OBJECT),
                    eventOfType(END_OBJECT)
                )
            ),
            with(viewContext));
        will(returnValue(entity));
        oneOf(root).apply(entity, model, viewContext);
      }
    });

    editor.update(model);

  }

  private interface MockModel {}

}
