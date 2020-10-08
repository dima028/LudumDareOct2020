# pending 

#### To use mvn from the command line
* clean:  mvn clean
* checkstyle: mvn checkstyle:checkstyle  (results show up in target/site)
* compile : mvn compile
* execute: 'mvn exec:java'

#### To Do:
* improve user interface: minimap to demonstrate user & flag
* example:

```[ ][ ][ ][ ][ ]```

```[ ][ ][ ][ ][ ]```
 
 ```[ ][ ][*][ ][ ]```
 
 ```[ ][ ][ ][ ][ ]```
 
 ```[ ][ ][o][ ][ ]```

* implement responsive flag motion:
    * measure distance from each block surrounding flag to user
    * move flag to furthest block
