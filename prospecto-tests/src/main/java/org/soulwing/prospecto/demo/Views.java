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
package org.soulwing.prospecto.demo;

import org.soulwing.prospecto.ViewTemplateBuilderProducer;
import org.soulwing.prospecto.api.AccessType;
import org.soulwing.prospecto.api.ViewTemplate;

/**
 * Demo views.
 *
 * @author Carl Harris
 */
public interface Views {

  String NAMESPACE = "urn:org.soulwing.prospecto:demo";

  ViewTemplate VENDOR_SUMMARY_TEMPLATE = ViewTemplateBuilderProducer.getInstance()
      .object("vendor", NAMESPACE, Vendor.class)
        .url()
        .value("name")
        .value("taxId")
        .end()
      .build();

  ViewTemplate PO_SUMMARY_TEMPLATE = ViewTemplateBuilderProducer.getInstance()
      .arrayOfObjects("orders", "order", NAMESPACE, PurchaseOrder.class)
        .value("id")
        .value("fob")
        .value("dueDate")
        .value("total")
          .source("itemTotal")
          .accessType(AccessType.PROPERTY)
        .subview("vendor", VENDOR_SUMMARY_TEMPLATE)
        .end()
      .build();

  ViewTemplate PO_DETAILS_TEMPLATE = ViewTemplateBuilderProducer.getInstance()
      .object("order", NAMESPACE, PurchaseOrder.class)
        .value("id")
        .value("fob")
        .value("dueDate")
        .value("total")
          .source("itemTotal")
          .accessType(AccessType.PROPERTY)
        .envelope("approverList")
          .arrayOfValues("approvers", "approver")
          .end()
        .subview("vendor", VENDOR_SUMMARY_TEMPLATE)
        .arrayOfObjects("items", "item", PurchaseItem.class)
          .value("description")
          .value("quantity")
          .value("unitPrice")
          .end()
        .end()
      .build();

}
