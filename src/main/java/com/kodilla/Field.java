package com.kodilla;


import javafx.scene.image.ImageView;

import java.io.IOException;
import java.io.Serializable;


public class Field extends ImageView implements Serializable {
    private FieldType fieldType;

    public Field(FieldType fieldType){
        super();
        this.fieldType = fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }
}
