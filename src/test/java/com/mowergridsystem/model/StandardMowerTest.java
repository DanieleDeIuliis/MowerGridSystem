package com.mowergridsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mowergridsystem.model.CommandEnum.LEFT;
import static com.mowergridsystem.model.OrientationEnum.*;

public class StandardMowerTest {
    @Test
    public void standardMowerTestCorrectInitialization(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                NORTH);
        Assertions.assertEquals(2,standardMower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(1,standardMower.getPosition().getRowCoordinate());
        Assertions.assertEquals(0, standardMower.getCurrentOrientationIndex());
        Assertions.assertEquals(NORTH, standardMower.getCurrentOrientation());
    }

    @Test
    public void changeOrientationTestSuccessfulNegativeShift(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                NORTH);
        standardMower.changeOrientation(LEFT);
        Assertions.assertEquals(WEST, standardMower.getCurrentOrientation());
    }

    @Test
    public void changeOrientationTestSuccessfulPositiveShift(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                WEST);
        standardMower.changeOrientation(CommandEnum.RIGHT);
        Assertions.assertEquals(NORTH, standardMower.getCurrentOrientation());
    }

    @Test
    public void moveHorizontallyTestCorrectPositionUpdated(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                EST);
        standardMower.move(new Position(3,2));
        Assertions.assertEquals(3, standardMower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(2, standardMower.getPosition().getRowCoordinate());
    }

}
