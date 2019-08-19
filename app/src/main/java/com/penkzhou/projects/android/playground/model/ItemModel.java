package com.penkzhou.projects.android.playground.model;

import androidx.annotation.Keep;

@Keep
public final class ItemModel {
    private final String name;
    private final String icon;
    private final String path;

    public ItemModel(String name, String icon, String path) {
        this.name = name;
        this.icon = icon;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getPath() {
        return path;
    }
}
