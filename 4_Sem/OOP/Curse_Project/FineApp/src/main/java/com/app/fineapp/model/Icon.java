package com.app.fineapp.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Icon {
    protected Integer image;
    protected Integer color;

    public Integer getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
