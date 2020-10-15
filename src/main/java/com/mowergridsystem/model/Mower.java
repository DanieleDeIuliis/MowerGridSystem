package com.mowergridsystem.model;

public interface Mower {

    void move(Position position);

    void changeOrientation(CommandEnum direction);

    OrientationEnum getCurrentOrientation();

    Position getPosition();
}
