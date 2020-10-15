# MowerGridSystem
## How to build
Go to the root of the project:

* **Compile and run unit tests:** mvn clean install / mvn install

ConsoleAppTest.java is the component test class in which there's the parametrized
test *testComputeMowerGridSystemService* that takes as input a file and compares the output with an output file.

Input and Output files are in the corresponding folder under: src/main/resources/consoleApp.

## How to run
Go to the root of the project:

* **Run the program:** mvn exec:java

The program will ask an absolute path of a file which has to comply with the following format:
 ```bash
 GridXCoordinate GridYCoordinate                                 |       5 5
 MowerXCoordinate MowerYCoordinate MowerOrientation              |       1 2 N
 Commands                                                        |       LFLFLFLFF
 ...                                                             |       ...
 MowerXCoordinate MowerYCoordinate MowerOrientationCommands      |       5 5 N
 Commands                                                        |       RFFFFFRFFFFF
 ```
The output will be a list on screen with final position for each mower. Each line has the format:
```bash
MowerXCoordinate MowerYCoordinate MowerOrientation              |      1 3 N
```
## Notes
### Coordinates
The input and ouput coordinates follow the "bottom left" notation, meaning that the bottom left cell has coordinates (0,0).

However, the system is based on the "top left" notation, meaning that the top left cell has coordinates (0,0).

The classes inputParser and outputParser, in addition to parsing have the role of converting from the "bottom left" to "top left" notation back and forth.

### Grid: The Board
The only way to modify the board of the grid is through a synchronized method that first checks if the position is valid,
and in case it's possible, it modifies the board. This ensures that in a multi-thread scenario, only one thread at a time
can check the state of a cell in the board and request the change.

Three methods are synchronized:
* checkPositionAndChangeState
* invertPositionIsOccupiedState
* isNewPositionValid

### Time assumption
The service will execute one turn a time. During one turn each thread let the mower managers perform their next command 
and it ends when all of them finish.

The assumption here is that a mower requires the same amount of time to change its orientation and to move forward one cell. Doing so,
it's also possible to forecast collisions between mowers.

### Sleep in MowerMoveService
Even though the program performs as expected in a multi-thread environment it has been introduced a sleep between two
submit of a job to a thread in the pull. This ensures a deterministic behaviour during the test phase. 

In case this behaviour is not required, remove the sleep and keep particular attention to mowerCollision and mowerCrash tests.
