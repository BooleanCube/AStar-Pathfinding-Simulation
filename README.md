# A* Pathfinding Simulation

This is my first attempt at A* pathfinding. I made this simulation to experiment with interesting ways of pathfinding for game development. The Simulation is very user-friendly and you can configure all major features of my algorithm through the UI to your liking. This simulation is heavily influenced by the A* Pathfinding Visualization written by Devon Crawford, but I just optimized it a bit more and added some new handy features.

## Basic Controls
You must create a map to start the pathfinding. The start node is blue, end node is red and the walls are black. 

To create nodes:
  - Start: hold 's' + left click
  - End: hold 'e' + left click
  - Wall: left click
  
To delete nodes:
  - same as creation, except right click
  
![basic-controls](https://cloud.githubusercontent.com/assets/25334129/22450191/f433bb24-e732-11e6-8004-19b923cf4d08.gif)

### Diagonal
My algorithm supports both diagonal and non diagonal pathfinding. <br>
Simply check the "diagonal" box at the bottom left of the screen.

![diagonal-vs-non-diagonal](https://cloud.githubusercontent.com/assets/25334129/22450200/fd49d752-e732-11e6-9684-f9284486d6eb.gif)

### Variable Speed
You may change the speed of the visualization during runtime. (By default, `speed` is 50%.) <br>
Notice: speed only works when showSteps is true. <br>
if `showSteps` is false, well, that leads into the next section.. 

### Show Steps or Timed Efficiency
You may choose to view a step-by-step process of the algorithm by selecting "showSteps" box at the bottom left. 
  - If showSteps is false, the algorithm will skip visuals until the end, and process as fast as possible.
This is useful for when you want to analyze the efficiency of my algorithm in different coniditons. The example below shows "showSteps" as false, where it times the algorithm and outputs "Completed in 4ms" at the bottom left. 

![showsteps](https://cloud.githubusercontent.com/assets/25334129/22450236/2f7d1d9c-e733-11e6-87ea-60bc0ecac146.gif)

### Complicated Stuff
Those are the basics! Now you can be free to make the map as complicated as you desire. (Not really, because making the map too large will overflow the stack). But go ahead! 

![complicated-drawings](https://cloud.githubusercontent.com/assets/25334129/22450232/2b790d14-e733-11e6-8a91-4b4cba372f9b.gif)

### Zoom
(Temporarily removed zooming feature for now)

~~You can (kind of) zoom in and out. I wouldn't really advise it. It does not zoom into your mouse, only towards the top left corner, and making the map too big will crash the program. This needs some work. However, If you zoom in far enough you can view each nodes information. The top left is the "F cost", bottom left is "G cost" and bottom right is "H cost". I will work on properly implementing a zoom feature soon.~~


*by BooleanCube :]*
