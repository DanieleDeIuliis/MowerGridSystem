package com.mowergridsystem.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.mowergridsystem.model.CommandEnum.L;
import static com.mowergridsystem.model.OrientationEnum.*;

public class StandardMowerTest {
    @Test
    public void standardMowerTestCorrectInitialization(){
        StandardMower standardMower = new StandardMower(new Position(1,2),
                N);
        Assertions.assertEquals(2,standardMower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(1,standardMower.getPosition().getRowCoordinate());
        Assertions.assertEquals(0, standardMower.getCurrentOrientationIndex());
        Assertions.assertEquals(N, standardMower.getCurrentOrientation());
    }

    @Test
    public void changeOrientationTestSuccessfulNegativeShift(){
        StandardMower standardMower = new StandardMower(new Position(1,2),
                N);
        standardMower.changeOrientation(L);
        Assertions.assertEquals(W, standardMower.getCurrentOrientation());
    }

    @Test
    public void changeOrientationTestSuccessfulPositiveShift(){
        StandardMower standardMower = new StandardMower(new Position(1,2),
                W);
        standardMower.changeOrientation(CommandEnum.R);
        Assertions.assertEquals(N, standardMower.getCurrentOrientation());
    }

    @Test
    public void moveHorizontallyTestCorrectPositionUpdated(){
        StandardMower standardMower = new StandardMower(new Position(1,2),
                E);
        standardMower.move(new Position(1,3));
        Assertions.assertEquals(3, standardMower.getPosition().getColumnCoordinate());
        Assertions.assertEquals(1, standardMower.getPosition().getRowCoordinate());
    }

}
