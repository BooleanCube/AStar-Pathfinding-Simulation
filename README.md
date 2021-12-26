# A* Pathfinding Simulation

This is my first attempt at A* pathfinding. I made this simulation to experiment with interesting ways of pathfinding for game development. The Simulation is very user-friendly and you can configure all major features of my algorithm through the UI to your liking. This simulation is heavily influenced by the A* Pathfinding Visualization written by Devon Crawford, but I just optimized it a bit more and added some new handy features.

![complicated-drawings](https://cloud.githubusercontent.com/assets/25334129/22450232/2b790d14-e733-11e6-8a91-4b4cba372f9b.gif)

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
![runtime](https://cloud.githubusercontent.com/assets/25334129/22450236/2f7d1d9c-e733-11e6-87ea-60bc0ecac146.gif)

### Zoom
(Temporarily removed zooming feature for now)

~~You can (kind of) zoom in and out. I wouldn't really advise it. It does not zoom into your mouse, only towards the top left corner, and making the map too big will crash the program. This needs some work. However, If you zoom in far enough you can view each nodes information. The top left is the "F cost", bottom left is "G cost" and bottom right is "H cost". I will work on properly implementing a zoom feature soon.~~

## WARNING!

You can create unordinary and complex maps and the simulation will run fine but be wary that sometimes, if the path has already searched many nodes, the program might eventually run into a stackoverflow error. <br>

## TODO
- [ ] Add a clear feature (clears every object on the screen)
- [ ] make a separate repo for sorting later ig /shrug
- [ ] Fix scrolling to resize grid

*a project fixed by BooleanCube :]*
