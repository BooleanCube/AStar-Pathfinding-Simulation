# A* Pathfinding Simulation

This is my first attempt at A* pathfinding. I made this simulation to experiment with interesting ways of pathfinding for game development. The Simulation is very user-friendly and you can configure all major features of my algorithm through the UI to your liking. This simulation is heavily influenced by the A* Pathfinding Visualization written by Devon Crawford, but I just optimized it a bit more and added some new handy features.

![sus](https://user-images.githubusercontent.com/47650058/147399417-daed2f3a-5933-4c36-b3df-f2c84ea024cf.gif)

## Basic Controls

### Map Creation
You must create a map to start the pathfinding. The start node is blue, end node is red and the walls are black.

To create nodes:
  - Start: hold 's' + left click
  - End: hold 'e' + left click
  - Wall: left click
  
To delete nodes:
  - same as creation, except right click

### Diagonal
My algorithm supports both diagonal and non diagonal pathfinding. <br>
Simply check the "diagonal" box at the bottom left of the screen.

### Variable Speed
You may change the speed of the visualization during runtime. (By default, `speed` is 50%.) <br>
Notice: speed only works when showSteps is true. <br>
if `showSteps` is false, well, that leads into the next section.. 

### Show Steps or Timed Efficiency
You may choose to view a step-by-step process of the algorithm by selecting `showSteps` box at the bottom left. 
  - If `showSteps` is false, the algorithm will skip visuals until the end, and process as fast as possible.
This is useful for when you want to analyze the efficiency of my algorithm in different coniditons. The example below shows `showSteps` as false, where it times the algorithm and outputs `Completed in 4ms` at the bottom left.
<img src="https://user-images.githubusercontent.com/47650058/147399062-76e9eb05-939d-460e-9083-1e9c2a1b9fa9.png" width="500" />

### Zoom
You may scroll up and down to zoom in and out. If zoomed in far enough, you will be able to see details like the `g cost`, `h cost` and `f cost` for each node that is open and closed. The Zoom feature can only be used when the board is empty so if you want to resize the grid you would have to first clear the board.
<img src="https://user-images.githubusercontent.com/47650058/147398876-e92b037e-9b70-4d35-b46a-3fc413baf548.png" width="500" />

## WARNING!

You can create unordinary and complex maps and the simulation will run fine but be wary that sometimes, if the path has already searched many nodes, the program might eventually run into a stackoverflow error. <br>

*a project fixed by BooleanCube :]*
