/*
 * File created on Apr 4, 2016
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
package org.soulwing.prospecto.tests.editor;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Test;
import org.soulwing.prospecto.ViewTemplateBuilderProducer;
import org.soulwing.prospecto.api.AccessType;
import org.soulwing.prospecto.api.ViewTemplate;

/**
 * TODO: DESCRIBE THE TYPE HERE
 * @author Carl Harris
 */
public class ValueTest extends EditorTestBase {

  @Test
  public void testWrapperTypesPropertyAccess() throws Exception {
    validate(newTemplate(WrapperTypes.class,
        AccessType.PROPERTY));
  }

  @Test
  public void testPrimitiveTypesPropertyAccess() throws Exception {
    validate(newTemplate(PrimitiveTypes.class,
        AccessType.PROPERTY));
  }

  @Test
  public void testWrapperTypesFieldAccess() throws Exception {
    validate(newTemplate(WrapperTypes.class,
        AccessType.FIELD));
  }

  @Test
  public void testPrimitiveTypesFieldAccess() throws Exception {
    validate(newTemplate(PrimitiveTypes.class,
        AccessType.FIELD));
  }

  private ViewTemplate newTemplate(Class<?> modelType, AccessType accessType) {
    return ViewTemplateBuilderProducer
        .object("valueTest", "urn:org.soulwing.prospecto:test", modelType)
            .accessType(accessType)
            .value("stringValue")
            .value("booleanValue")
            .value("byteValue")
            .value("shortValue")
            .value("integerValue")
            .value("longValue")
            .value("bigIntegerValue")
            .value("floatValue")
            .value("doubleValue")
            .value("bigDecimalValue")
            .end()
        .build();
  }

  @SuppressWarnings("unused")
  public static class PrimitiveTypes {

    private String stringValue;
    private boolean booleanValue;
    private byte byteValue;
    private short shortValue;
    private int integerValue;
    private long longValue;
    private BigInteger bigIntegerValue;
    private float floatValue;
    private double doubleValue;
    private BigDecimal bigDecimalValue;

    public String getStringValue() {
      return stringValue;
    }

    public void setStringValue(String stringValue) {
      this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
      return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
      this.booleanValue = booleanValue;
    }

    public Byte getByteValue() {
      return byteValue;
    }

    public void setByteValue(Byte byteValue) {
      this.byteValue = byteValue;
    }

    public Short getShortValue() {
      return shortValue;
    }

    public void setShortValue(Short shortValue) {
      this.shortValue = shortValue;
    }

    public Integer getIntegerValue() {
      return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
      this.integerValue = integerValue;
    }

    public Long getLongValue() {
      return longValue;
    }

    public void setLongValue(Long longValue) {
      this.longValue = longValue;
    }

    public BigInteger getBigIntegerValue() {
      return bigIntegerValue;
    }

    public void setBigIntegerValue(BigInteger bigIntegerValue) {
      this.bigIntegerValue = bigIntegerValue;
    }

    public Float getFloatValue() {
      return floatValue;
    }

    public void setFloatValue(Float floatValue) {
      this.floatValue = floatValue;
    }

    public Double getDoubleValue() {
      return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
      this.doubleValue = doubleValue;
    }

    public BigDecimal getBigDecimalValue() {
      return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
      this.bigDecimalValue = bigDecimalValue;
    }

  }

  @SuppressWarnings("unused")
  public static class WrapperTypes {

    private String stringValue;
    private Boolean booleanValue;
    private Byte byteValue;
    private Short shortValue;
    private Integer integerValue;
    private Long longValue;
    private BigInteger bigIntegerValue;
    private Float floatValue;
    private Double doubleValue;
    private BigDecimal bigDecimalValue;

    public String getStringValue() {
      return stringValue;
    }

    public void setStringValue(String stringValue) {
      this.stringValue = stringValue;
    }

    public Boolean getBooleanValue() {
      return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
      this.booleanValue = booleanValue;
    }

    public Byte getByteValue() {
      return byteValue;
    }

    public void setByteValue(Byte byteValue) {
      this.byteValue = byteValue;
    }

    public Short getShortValue() {
      return shortValue;
    }

    public void setShortValue(Short shortValue) {
      this.shortValue = shortValue;
    }

    public Integer getIntegerValue() {
      return integerValue;
    }

    public void setIntegerValue(Integer integerValue) {
      this.integerValue = integerValue;
    }

    public Long getLongValue() {
      return longValue;
    }

    public void setLongValue(Long longValue) {
      this.longValue = longValue;
    }

    public BigInteger getBigIntegerValue() {
      return bigIntegerValue;
    }

    public void setBigIntegerValue(BigInteger bigIntegerValue) {
      this.bigIntegerValue = bigIntegerValue;
    }

    public Float getFloatValue() {
      return floatValue;
    }

    public void setFloatValue(Float floatValue) {
      this.floatValue = floatValue;
    }

    public Double getDoubleValue() {
      return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
      this.doubleValue = doubleValue;
    }

    public BigDecimal getBigDecimalValue() {
      return bigDecimalValue;
    }

    public void setBigDecimalValue(BigDecimal bigDecimalValue) {
      this.bigDecimalValue = bigDecimalValue;
    }

  }

}