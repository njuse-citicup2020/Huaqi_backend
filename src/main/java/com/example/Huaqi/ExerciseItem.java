package com.example.Huaqi;

import com.example.Huaqi.po.OptionPO;

public class ExerciseItem {
    private OptionPO option;

    private double execPrice;

    private int size;

    public OptionPO getOption() {
        return option;
    }

    public void setOption(OptionPO option) {
        this.option = option;
    }

    public double getExecPrice() {
        return execPrice;
    }

    public void setExecPrice(double execPrice) {
        this.execPrice = execPrice;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ExerciseItem() {
    }

    public ExerciseItem(OptionPO option, double execPrice, int size) {
        this.option = option;
        this.execPrice = execPrice;
        this.size = size;
    }
}
