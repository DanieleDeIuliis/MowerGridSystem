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
        Assertions.assertEquals(2,standardMower.getPosition().getXCoordinate());
        Assertions.assertEquals(1,standardMower.getPosition().getYCoordinate());
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
    public void isOrientationHorizontalTestSuccessAfterOrientationChange(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                NORTH);
        OrientationEnum currentOrientation = standardMower.getCurrentOrientation();
        Assertions.assertFalse(standardMower.isOrientationHorizontal(currentOrientation));
        standardMower.changeOrientation(LEFT);
        currentOrientation = standardMower.getCurrentOrientation();
        Assertions.assertTrue(standardMower.isOrientationHorizontal(currentOrientation));
    }

    @Test
    public void moveHorizontallyTestCorrectPositionUpdated(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                EST);
        standardMower.moveHorizontally();
        Assertions.assertEquals(3, standardMower.getPosition().getXCoordinate());
        standardMower.changeOrientation(LEFT);
        standardMower.changeOrientation(LEFT);
        standardMower.moveHorizontally();
        Assertions.assertEquals(2, standardMower.getPosition().getXCoordinate());
    }

    @Test
    public void moveVerticallyTestCorrectPositionUpdated(){
        StandardMower standardMower = new StandardMower(new Position(2,1),
                NORTH);
        standardMower.moveVertically();
        Assertions.assertEquals(2, standardMower.getPosition().getYCoordinate());
        standardMower.changeOrientation(LEFT);
        standardMower.changeOrientation(LEFT);
        standardMower.moveVertically();
        Assertions.assertEquals(1, standardMower.getPosition().getYCoordinate());
    }
}
