package com.mowergridsystem.model;

public interface Mower {
    void moveHorizontally();
    void moveVertically();
    void changeOrientation(CommandEnum direction);
    OrientationEnum getCurrentOrientation();
}
