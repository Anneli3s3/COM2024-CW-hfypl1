# COM2024-CW-hfypl1

Brick Destroy

Additional function

1. Design for background and button
   I changed the design of the background and button to a more attractive image.
2. Additional Level
   I added a new level for the game which uses Titanium and Cement Bricks.
3. Additional Brick
   I added a new type of Brick, Titanium Brick which holds lower posibility than the Steel Brick and needs longer time to break it.
4. Additional Button 
   I added a new button, control button to allow the user to access the methods of playing the game.
5. High-score List
   I created a High Score List that handles the score of the player and it will save the score of the player after the game. It holds the top 10 players that scored the highest.


Refactor

1.Separate the class into packages to organise the coding. I have separated the whole project into two main package, which is the main and the model. 

  Main package : CompareScore, DebugConsole, DebugPanel, GameBoard, GameFrame, GraphicMain, HighScore, HomeMenu, LoadImg, Player, Score, StageLevel, Wall

  Model package: Ball, Brick, CementBrick, ClayBrick, RubberBall, SteelBrick, TitaniumBrick

2. Create level class 
  A new class create so that we can add level more easily . The codes looks more neat after the make level is removed from the original class.

3. Renaming
   I renamed few of the name of the variables. This helps the programmer to know which variable are the one they want. As an example, I renamed gameTimer to gamCounter. This help me to recognise the functionality of the variable and the name is more stick to its functionality.
   
4. Cleaning 
   I removed some of the old codes that are redundant in the program to make it more neat. 

