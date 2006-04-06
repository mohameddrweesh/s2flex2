package org.seasar.flex2.util.data.transfer;

import org.seasar.flex2.util.data.transfer.annotation.Export;

public class TestClass {
    private String strData;

    private Object[] arrayData;

    private int[] intArrayData;

    private Boolean _booleanData;

    private Character charData;

    private Short shortData;

    private Integer intData;

    private Long longData;

    private Double doubleData;

    private Float floatData;

    public Object[] getArrayData() {
        return arrayData;
    }

    public void setArrayData(Object[] arrayData) {
        this.arrayData = arrayData;
    }

    public Boolean getBooleanData() {
        return _booleanData;
    }

    public void setBooleanData(Boolean booleanData) {
        this._booleanData = booleanData;
    }

    public Character getCharData() {
        return charData;
    }

    public void setCharData(Character charData) {
        this.charData = charData;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }

    public Float getFloatData() {
        return floatData;
    }

    public void setFloatData(Float floatData) {
        this.floatData = floatData;
    }

    public int[] getIntArrayData() {
        return intArrayData;
    }

    public void setIntArrayData(int[] intArrayData) {
        this.intArrayData = intArrayData;
    }

    public Integer getIntData() {
        return intData;
    }

    public void setIntData(Integer intData) {
        this.intData = intData;
    }

    public Long getLongData() {
        return longData;
    }

    public void setLongData(Long longData) {
        this.longData = longData;
    }

    public Short getShortData() {
        return shortData;
    }

    public void setShortData(Short shortData) {
        this.shortData = shortData;
    }

    @Export(storage = StorageType.SESSION)
    public String getStrData() {
        return strData;
    }

    public void setStrData(String strData) {
        this.strData = strData;
    }
}
