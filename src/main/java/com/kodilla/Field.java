package com.kodilla;


import javafx.scene.image.ImageView;

public class Field extends ImageView {
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
