<p align="center">
  <img width="300" height="300" src="http://i66.tinypic.com/2be2rs.png">
</p>

# What is JEngine
JEngine is a AWT Framework for implementing 2D scenes, frame-based animations, and gameplay
Particularly an open-source 2D game engine that is simple and easy to use, highly customizable, and requires *no* outside libraries to work.

# JEngine Quick Basics

### [**VIEW JAVADOC HERE**](https://webpages.uncc.edu/jdemeis/javadoc/index.html)  
[![CodeFactor](https://www.codefactor.io/repository/github/joey101937/jengine/badge)](https://www.codefactor.io/repository/github/joey101937/jengine)

-The physical window that displays your project is controlled by the **Window** class
-The part inside the window is a Game object. Games represent scenes that function as a world within which your objects exist. 
Games have their own InputHandlers to take in user input via mouse and keyboard. You may have multiple scenes for your project. Your window can swap between them using **setCurrentGame(Game g)** method. Note only current Game's input handler will detect user input and Games are paused when another game is made the current game and unpause/start when they are the one being made the current game. pause/unpause can also be manually toggled.

-Within each Game world there are **GameObject2**s which are the core of JEngine's functionality. Every functional object that exists in the world is in some way a GameObject2, a game character for example is a GameObject2. All GameObject2s in a Game instance are stored in that game's **Handler**. add objects with game.addObject(GameObject2), remove with game.removeObject(GameObject2) or get objects using getAllObjects(). GO2s tick and render with their host game, and by default have rectangular hitboxes (things that manage collision) that reflect the perimiter of that object's current visual

-A Game's **Camera** controls the viweport

-Hitboxes manage collision and are GameObjects are created with a rectangular one, but can also be created independently of a gameobject and can be either circular or 4-sided polygonal. Hitboxes can detect if they overlap eachother

-**Coordinate** and **DCoordinate** classes are used heavily when talking about location in the gameworld. Coordinate uses ints and often used to reflect the location of an object in pixels while DCoordinates use doubles and are typically used to store an object's true location and velocity. Both classes have considerable utility methods built in. Note these classes are not immutable, so use caution when modifying coordinates that may be referenced elsewhere. Use the .copy() method to generate an equivilent copy of a coordinate to avoid modifying the original coordinate. Add and Subtract methods modify the calling coordinate, they do not return a new coordinate based on the operation like you may find with strings.

# Your First Project
**Technical Note Before Starting** Once you have imported JEngine to your IDE, go to Framework.Game class and change *NATIVE_RESOLUTION* field to match your own screen dimension, then go into run properties of the project and set the VM options to include 
*-Dsun.java2d.d3d=false -Xmx1024*.

**Check out the GameDemo package to see small example projects and their setup**
JEngine is super easy to use and get started; first simply import the framework into your IDE of choice (I use netbeans).
Next you should gather your assets for the project and put them into the 'assets' folder of the working directory. JEngine by default
supports plain images (.png reccomended) or animation sequences, loaded by frame. See Visual Assets section of readme.

Now that you have your assets imported, you should create a scene for the user to see. *Note the a Game object is a single scene*.  Scenes are instances of the Game class and created with a background image, which is important because it creates the gameworld using the parameters of the given image.

Once you have your first Scene, create the **Window** around it by calling Window.initialize(Game). Now call start() method on your game. If done correctly, you should see a window with your given background image inside. Initialize should only be called once at the start of the program. Once you have your window, call setGame(Game) to swap out different games in the window.

Now you can create a character to go inside the world. I would reccomend copying the simple character from the sandbox demo, or you can make your own class that extends GameObject2. You just need a location for the object to be at and you should create a visual for the object so you can see it in the scene. you can use the method **setGraphic(Sprite image)** to set the object to be unanimated and use the given sprite as its visual. Hitboxes are automatically managed for you by default. Once you have your character object, call **addObject(GameObject2 go)** on your world and pass in your character. If done correctly, you should see your character's sprite at the character's location in your gameworld. note if you picked an out of bounds coordinate, the object may have been pulled back in to the nearest in-bounds location. 

Moving a GameObject can be done by modifying it's location directly (forcibly teleports the object), or by changing its velocity. Velocity is the prefered way to move things if you want them to move around the world rather than just teleport to a different location. 

To put your character in view if you put it in a location off-screen, position the camera over it or have the camera track it using **setTarget(GameObject2 go)** method in camera. **Ex: myGame.camera.setTarget(character);**

To make your game accept user keyboard/mouse input, create a class that extends InputHandler, then set an instance of that class to be the inputhandler for your game using **setInputHandler(InputHandler in)** in the Game class. Inside your inputHandler class you have acess to all mouse listener, mouse motion listener, and key listener methods as well as the **locationOfMouse(MouseEvent e)** method which provides the coordinate point of the mouse during the given mouse event *in terms of the game world*.

# Scenes/Games
To start a JEngine project, you must first have your base Game. Instances of the Game class are scenes and represent distinct gameworlds within. Created using **new Game(BufferedImage) background);**. To view it, you must also have a **Window** to put that game in. The window is the JFrame that holds the scene(s) and presents them to the user. Create using **Window.intitialize(Game)** Game class should be created *before* the Window. 

To make the Game start running, call the .start() method on your Game instance.

Games's core loop involves *ticking* and *rendering*. Ticking runs 60 times per second by default (change using Main.ticksPerSecond). Ticking updates all objects within the world logically. GameObject2s, which are objects that exist in the world, all have tick() methods that run whenever their parent game ticks. Render is the same system except render deals exclusively with visual effect rendering using the passed Graphics2D object from Java AWT.

Games store GameObject2s. Create a GameObject2 instance and add it to your world using *addObject(GameObject2 o)* method in Game class.
User input is done on a Game to Game basis, where each scene/game has its own InputHandler. In JEngine, create a class to handle input and have it extend Framework.InputHandler, This will give you access to all Keylistener, MouseListener, and MouseMotionListener methods as well as the **locationOfMouse(MouseEvent e)** method which provides the coordinate point of the mouse during the given mouse event *in terms of the game world*.

A gameworld is as large as it's background image, and this may be smaller than the size of your monitor. A game's **Camera** controls the viewport. Camera can be moved to a particular Coordinate or set to follow a GameObject2

You can have multiple scenes in one project. To do this, simply create a new Game and start it. To put it on screen, use the Window.setGame(Game) method. This pauses the active game, removes it, then adds the new given game. New given games are unpaused if paused or started if the game hasnt been started yet. *You can pause a Game manually using .setPaused(true), or resume with (.setPaused(false));*

### Other Fields

**WorldWidth and WorldHeight** are the dimensions of the gameworld, this is determined by the background.

**WindowWidth and WindowHeight** are the dimensions of the window used to view the game. If your game is smaller than the user's screen, this will be the size of the world. Otherwise, it will be the size of their screen.

**worldBorder** is the distance GameObject2's may get to the edge of the world before being constrained.

**pausedSafely** is used for knowning when it is safe to switch scenes. This is only true when the game is both paused and the final render/tick has finished

**Pausing/Unpausing** is done with setPaused(boolean).

**getBackgroundImage** gets the image set as the background of the world. Note this is what you want to use in most cases. getBackground() returns the color of the canvas element which is generally never used.

### Camera
Each Game object has a Camera object within it, the camera is what sets the veiwpoint for the scene. Camera *location* is the offset from the topleft corner of the world to the topleft of the viewpoint. Note the location of the camera will always be negative numbers because Y is distance traveled down from top of world and X is distance traveled right from right side of world.

**Moving the Viewpoint** Moving the camera can be done by one of three ways. First is with velocity, much like GameObject2s, Cameras 'tick' like gameobejcts and the camera's location will be updated by its velocity every tick, but will not leave the world. constrainCameraToWorld() method keeps the camera within the bounds of the visable area. *World Border does NOT affect Camera.* Movement types are the same as Gameobject2 movements except the rotation based setting is the same as the Speed Ratio setting. See GameObject2 movement types for details. Moving the camera can also be done by directly changing the *location* DCoordinate field. This ignores velocity and instantly teleports the camera to the given point. The final way to move the camera is by *tracking an object*.

**Tracking an Object** You can set the camera to follow an object, this will make the camera pan with the movement of a set object, keeping it on screen and at the center of the screen if possible (camera will not follow out of bounds). Set the camera to follow an object using the *setTarget(GameObject2)* method. To check if the camera is tracking, use *isTrackingTarget()*. Note this will only determine if the camera is trying to track something. May return true even if the tracked target is null. To get the current target, use *getTarget()*. *setTarget(GameObject2) setTrackingTarget(boolean)* can be used to enable/disable tracking or setting a new target. Null targets will not move the camera.

**Field of View**
Field of view represents the visible area of the world stored in a Rectangle object. X and Y are the cordinates are where the topleft corner of the field is and the height and width are as their names suggest. This can be used to determin if something is on-screen by creating another rectangle where that object is and using the *intersects* method built in to see if they intersect. For GameObject2s this is simply done by calling *isOnScreen()* method to determine if they are on screen presently. GameObject2s will not render if they are not on screen to help with performance.

### Handler and VisualEffectHandler
Every Game object has a handler and VisualEffectHandler. These keep track of all GameOject2s and Stickers in a game respectively. A Game object will refer to its handler to check for occupants and add/remove objects. You can add/remove objects from a game's handler directly to add/remove them from the game itself. If an object is not in the handler, it will not tick nor render and is effectively not in the world. Visual effect handler maintains Stickers, AnimatedStickers, and OnceThroughStickers. VisualEffectHandler can also add lines to the world which can be used to help debug. This must be done directly through a game's visualEffectHandler using *addLine(Coordinate start,Coordinate end)* method. By default lines are stored in Coordinate arrays of length 2 in the visualHandler's *lines* field. To remove a line, remove the index corresponding with that line's coordinate. Lines are added to the list in the same order as created.

### Retrieving GameObject2s In A Scene
**getAllObjects()** returns a list of all GameObject2's in this game's handler, which functionally means it gets all objects in the world (not their subobjects; an object's subobjects are stored in that object)

**getObjectsInArea(Rectangle r)** gets all GameObject2's in this game's handler that are in *or touching* the defined rectangle area. Reminder to make a rectangle: Rectangle r = new Rectangle(int x, int y, int width, int height); where x and y are topleft coordinate. 
*This method uses hitboxes, so your object must have a hitbox to be detected. Note objects do NOT need to be solid to get detected. Checks subobjects*

**getObjectsNearPoint(Coordiante c, double distance)** gets all GameObject2's whose location is within <distance> units of the given coordinate. *Note this method uses raw location, so the center point must be within distance. Hitboxes are not required. Does NOT check for subobjects*

**getObjectsIntersecting(Hitbox h)** You can create a standalone hitbox over your custom area and check collision using it. For circles, the code its *new Hitbox(Coordiante centerPoint, double radius)*, and for polygons, its *new Hitbox(Coordinate[] vertices)*; where the Coordinate array contains *exactly four* points which are, in order, top-left, top-right, bottom-left, bottom-right. This method checks each object in the world *and* their subobjects for collision. *Note this method also grabs non-solid objects*

# Visual Assets
## Loading Assets
JEngine loads all visual assets in the SpriteManager class, which is one of the framework classes you should modify regularly. Image
assets are stored in either static BufferedImage for images or BufferedImage arrays for frame based animation sequences. First declare the
variable and name it appropriately, then add code to load it in SpriteManager's **Initialize** method. Initialize runs once using the 
static block to pre-load all assets before they need to be rendered and stores them in memory rather than using ImageIO every time we need
to get outside assets.

To make it easy, use SpriteManager's **load(String filename)** and **loadSequence(String folderName)** to load images and animation
sequences respectively. Note these filepaths are *within* the assets folder. The demos included load their assets with this class and you 
should follow the same system. Once this is done, you can reference your image using SpriteManager.<your variable name>.

## Using Assets
Once you have loaded the raw image data using SpriteManger, we are now ready to apply them to either a game background, GameObject, or 
Sticker. To do this we create either a **Sprite** oject for plain images or a **Sequence** object for animation sequences. Creating them
is as easy as **new Sprite(BufferedImage);** and **new Sequence(BufferdImage[]);**

Sprites and Sequences implement the **Graphic** interface which means can both be scaled, destroyed, and copied without modifying the original asset. This is important if you have multiple objects using the same asset.(rotation is handled by implementation).

### Graphic Interface
This is the interface both Sprite and Sequence implement, and is used to store a graphical asset. To know if the Graphic is a Sprite or a Sequence, use the isAnimated() method. Sequences return true, sprites return false as they are simply images. To get the current frame of a sequence or image of a sprite use the getCurrentImage() method. This interface allows for scaling, copying, and destroying.
*Note destroy() method does **not** destroy the underlying asset*.

### Using Sequences
A sequence represents a frame based animation. Options include scaling the size of the visuals with **scale(double s)** and
scaleTo(double s)** methods; and changing the speed of animation by adjusting frameDelay field.

Sequences have their own threads that animate them and keep up with current frames. These animator threads do not start until the sequence is rendered and stop if the sequence is disabled.

Sequnces can pause animation using setPaused(true) method or resumed with setPaused(false). Sequences can also be reversed using reverse() method and resumed by calling it again.

# Stickers
**Stickers** in JEngine represent a visual effect that is temporarily rendered to a location in a scene. Stickers are created in the following way: *new Sticker(Game g, BufferdImage bi, Coordiante c, int i)* where g is the game you want to add the sticker to, bi is the visual asset you want to render, c is where in the world you want the sticker to be rendered at, and i is how long the effect should last. Example of sticker use is a blast effects on explosion or impact.

**AnimatedStickers** are stickers except they use BufferedImage arrays to store frames of an animation, much like a **Sequence**. Animated stickers loop through their animation until the given time duration is complete.

**OnceThroughStickers** are AnimatedStickers except instead of looping until duration is over, the sticker will only play until one cycle of the animation has completed, even if the duration is not over. Giving a OnceThroughSticker a low duration can still end the sticker before the animation is complete. OnceThroughStickers can be instanciated without providing a duration; in this case the duration is assumed to be infinite and the sticker will only end when the animation sequence given to it is complete.

### Sticker Operations
Stickers can be manipulated in the following ways:
1. Stickers can be scaled to a given size ratio. *scale(double)* will scale based on current size while *scaleTo(double)* scaled based on the original size of the visual. 
2. Stickers can be attached to GameObject2s in much the same way subobjects are. Stickers will now follow the GameObject2 and move with it. GameObject2 that the sticker is attached to is reffered to as the 'host'. Attach with *attachTo(GameObject2)*.
3. Stickers can be moved by changing *spawnLocation* coordinate field.
4. Stickers can be manually disabled and turned off by calling the *disable()* method. 

# Hitboxes
Hitboxes come in two types: Circular and Polygonal. Circle hitboxes are the simplest and are more performant. A polygonal box hitbox is generated by default for each GameObject and automatically adjusts to fit perfectly according to whatever visual asset is being rendered for the object's visual. This is updated every tick. Hitboxes are most accurate when detecting collision with others of the same shape however each type can detect the other with reasonable accuracy. To create a circle hitbox, you just need either a coordinate point for it to be created at or a GameObject2 to connect to, and a value for the radius (double). Polygonal hitboxes take an array of coordinates for the vertices of the polygon. At this time, only and exactly 4 (four) vertices are supported. You may assign to a GameObject2 by adding it to constructor parameter. The vertices *must* be put into the given array in this order: **TopLeft, TopRight, BottomLeft, BottomRight**. 

GameObject2s by default only check for collision with the hitboxes connected to other objects in their hostGame instance, to get collisions with a free floating hitbox, you must create the hitbox and check for collisions with that Hitbox object with each object you want to test; usually iterating through a Game's getAllObjects() array will suffice for checking collisions with all objects in a particular scene.

### Custom Hitboxes
Polygonal box hitbox is generated by default for each GameObject and automatically adjusts to fit perfectly according to whatever visual asset is being rendered for the object's visual. This is updated every tick. To change to the default *circle* hitbox, simply call the setHitbox method and provide a new Hitbox object with the following parameters: 1. The GameObject2 in question, and 2. 0.0. The object determines what object to assign to and the 0.0 is the default radius, which doesnt matter because the unless the **updateHitbox** method is overridden, the hitbox will stay in line with the size of the object its assigned to. 

*Note: UpdateHitbox is run with the default updateLocation method*

**To create a custom hitbox that is a circle of set size other than the size of the object's visual**, override the updateHitbox method and leave it blank. Now you can set a hitbox to use with setHitbox and it will not automatically contour to the object its a part of.
*It is not possible to use a basic circle hitbox that is NOT centered on the object. To get that effect, use a subobject at the desired offset and put the hitbox on that subobject.*

**To create a custom hitbox that is a polygon other than size of object's visual**, again you will need to override the updateHitbox method for the object. Now, create a hitbox where each of the four vertices is an *offset* relative to the center point of the object. That hitbox will be used for the object.

**To create a custom irregular compound hitbox**, you will need to create a simple subobject for each component. For example, say you have a humanoid object and you want to make each limb have its own rectangular hitbox. In this case, you will need to create a limb subobject for each and override their update hitbox methods to be blank and provide them your desired vertices. Note these subobjects do not need visual assets. Now make sure they are solid and override their onCollide(GameObject2 go) method, and add to the body a call to the subobject's host (your humanoid object) onCollide method and provide the go parameter. *host.onCollide(go);*.

**To create a custom dynamic hixbox**, you will need to override the object's updateHitbox() method. This runs each time the location is updated by default but you can call it manually or add it to the tick() method. Reference the current hitbox with getHitbox() to modify values or setHitbox() to create an entirly new one. Use these to modify the active hitbox at runtime. Note that by default, it is this method that keeps the hitbox's size inline with the size of the object on screen, so if you want to maintain that functionality, begin with *super.updateHitbox();*. 

# GameObject2 class
GameObject2's are the core of all functional objects within a scene. GameObject2's may somtimes be refered to as GameObjects; they are the same thing. Dont ask about what happened to GameObject1.

### General Fields

**hostGame** This is the game that this object is a part of. Whenever it is addded to a world, that world is set as the host game. Watch out if your using this object across multiple scenes, make sure hostGame is set properly. hostGame generally auto-sets itself but if your looking to fix a bug, there is a possibilty this is the cause.

**name** This is used to help debugging; effectively a tag on the object. Displays on object when in debug view

**renderNumber** Used to help debugging; tracks how many times this object has ticked

**location** This is a DCoordinate that tracks the object's absolute position. This is then rounded to a Coordinate to render to pixel based screen. Modifying this value will change the location of the object

**innateRotation** This is how much the object is to be considered rotated by default. 90 makes the right side of the object considered to be the top for example.

**baseSpeed** This is the object's speed when uneffected by modifiers and this is used in speedRatio and rotationBased movement when moving with velocity.

**isAnimated** Weather or not the object is currently using an animated squence or static sprite.

**graphic** Current visual representation of this object. Can be either a sprite or sequence object. May be animated.

**animations** Map of animation sequneces to animation names for ease of access.

**rotation** current clockwise degree of rotation

**isSolid** and **preventOverlap** see ***collision***

**isInvisible** weather or not the object should be rendered to the screen.

**scale** percentage size of the object with 1.0 being 100% or default size.

**isAlive** is the object considered alive? Objects must be alive to function and dead objects will be removed from game handlers.

**movementType** see movement types section

**plane** which 'layer' this object is on, used for collision. Objects will only collide with other objects when they are on the same plane. default plane is 0.

**hitbox** this object's hitbox. See ***Hitboxes***

**attachedStickers** A list of all stickers attached to this gameobject2. See stickers section for more details.

**ID** A numeric identifier for this object.

**pathingModifiers** This is a map that assigns different speed modifiers to different terrain types. See pathing layer for more details.

**subObjects** List of all subobjets of this object.

**zLayer** Is the Z-axis value, and determines which objects will be rendered on top of or below others.

**plane** Objects may only collide with other objects if they are on the same plane



### Important Methods
**tick()** 
Tick runs every game 'tick', a number of times per second equal to the TPS (ticks per second), settable in the options menu. Tick is used to update logical computations. When overriding tick, you should generally first call super.tick, which maintains the variable that counts ticks as well as the updateLocation method.

**render(Graphics2D g)**
Render is run *every frame* and should be used to draw things to the scene. Generally you do not need to override this method unless you know what you are doing. Avoid adding complex logic checks to this as it runs very often and is not set to run in consistant intervals.

**updateLocation()**
Update location adjusts the object's location based on its velocity. This method controls collision, hitboxes, and constrains* the object to stay within bounds of the gameworld. 

**constrainToWorld()**
This runs in updateLocation method every tick. The job of this method is to detect if the object is out of bounds and if so, teleports it back in bounds at the nearest allowed point. Override to allow going out of bounds or for implementing unique logic to check if object is out of bounds.

**updateHitbox()**
This method creates and maintains the default hitbox on an object. If you want to change the hitbox or use no hitbox at all, overide this method. Creates a box hitbox by default but will also work with circular ones if you set the hitbox to a circle. This method ensures the hitbox is always sized to match the current visual representation of the object on screen. If using image sprites or sequences, This will be a box matching the dimensions of the on-screen image. If you have a circle hitbox, it will be a circle with a diameter equal to the *width* of the current sprite.

**destroy()**
Destroys the object and removes it from play. isAlive will be *false* after this.

### Visual Representation
A GameObejct2 is rendered to the screen at its *pixelLocation*, this is the Coordinate approximation of its *location*, which is stored using a separate DCoordinate for greater location accuracy. *pixelLocation* represents where in the world the object will be rendered, measured in pixels.

The visual of a GameObject2 may be changed at any time, and my be swapped from animated to non-animated at any time.
You can scale the GameObject2 to be larger or smaller, and rotate it in any direction. Note doing these operations may change the hitbox and therefor collision

**Applying Non-Animated Sprite** 
If you dont want your object to be animated, you will use a **Sprite** object,and apply it to the object using the **setGraphic(Sprite)** method where the sprite you give is a new Sprite with your desired bufferedImage.

**Applying Animated Visuals**
If you want your object to have an animated visual, you will need to load in the frames of the animation via the SpriteManager or similar clas, and store that, in order, in a bufferedImage array. Now create a **Sequence** object with that array,**new Sequence(BufferedImage[])**. Now you can call **setGraphic(Sequence)** on the object and your object will use the given animation sequence.


### Transformations
**Scaling**
Change the *scale* field in the object and it will scale the object to the given amount. Sprite and Sequence objects will scale to match the objects scale on render.

**Rotating**
Rotation is more complicated than setting a single variable. To rotate the object by a set number of degrees, call **Rotate(double)** method. This method rotates from where the object is currently rotated. To set the rotation directly, call **rotateTo(double)** method. To rotate in such a way to face a specifiec point or object, call the **lookAt(Coordinate)** or **lookAT(GameObject2)** methods.

### Moving GameObejcts 
Moving gameobjects involves changing their *location*. Directly changing the location field will result in the object 'teleporting' around the world,and would cause problems with collision. Instead, you should modify the object's *velocity*. Velociy moves the object every tick based on the direction and the extremity of the velocity. Velocity based movement is fluid and works with collision. Velocity is stored in a DCoordinate, x = X velocity, y = Y velocity. positive X velocity moves the object to the right while positive Y velocity moves the object downward. Inversing the sign to negative would produce opposite results.

**MOVEMENT TYPES**
GameObject2s support 3 types of movement, these are as follows:

**Raw Velocity** Raw velocity is just what it sounds like. Every tick the objects location is directly modified by whatever the velocity is. ie an object with velocity of (100,0) would move 100 units to the right every tick.

**Speed Ratio** Speed ratio is a type of movement that streamlines an object's speed so that it always travels a distance equal to its given *speed* field every tick based on velocity (0 velocity will not move). This is usful if the Object is traveling in a direction that is not perpendicular to the X or Y axis, especially projectiles, and is the default type for most objects. Change how fast the object moves not by velocity but with *speed*. an object with speed of 5 and velocity of (100,0) would travel 5 untis to the right.

**Rotation Based** This is speed ratio except velocity is relative to the gameobject2's orientation, not global. positive y velocity that would usually correlate with going upwards to the top of the screen would instead push the object forward in whatever direction the object is facing. Example is a gameobject with speed of 1, velocity of (0,100), turned 90 degrees to the right. The object will move 1 unit to the right (direction its facing) every tick.

### Collision
GameObjects can be in 3 states for collision; solid, non-solid, overlap allowed. 

**isSolid** is a field in gameobject2 that determines weather or not the object will collide with other objects when it touches them. If not solid, neither object's **onCollide** method will trigger and the objects will move through eachother. If set to true, the object *will* trigger the onCollision method when touching another gameobject.

**preventOverlap** is a sparate flag that determines how objects interact with collision. When turned on (default setting), the object will not be permitted to move onto another object's hitbox via velocity. If it tries, it will trigger the onCollide method but will NOT move through the other object. If the velocity is then set to 0, the onCollision method will stop triggering and the object in question will rest immediately next to the other object. Disabling this field will allow the object to pass through other solid objects as if it was not solid, however the onCollide methods for both objects will still triger as usual.

**collisionSliding** is another collision modifier that affects movement along another hitbox, used in conjunction with PreventOverlap. This flag, when enabled, allows objects to slide across other objects. This is done by making it so that whenever a velocity would result in a collision, the object will check each axis to see if it is clear; if exactly one can be zeroed out to prevent collision, the object will move in such a way to preserve the velocity of the unblocked axis while not advancing on the axis that was blocked. This is especially useful when you use hitboxes to make floors as it allows objects to move across the floor even if they have downwards velocity(gravity).

**Note:** Solid objects that do not allow overlap will still be able to move freely through another solid object if for some reason they are already overlapping that object; this is to prevent objects from getting stuck inside eachother in the event something unforseen sets them close enough together that they overlap.

**onCollide(GameObject2)** is a method that triggers every tick that two objects are touching. This method triggers for both objects. Override this method to perform collision-based logic.

**SubObject Collision** Subobjects have their own hitboxes and therefore their own collisions, to check manually if two objects intersect, make sure you check all of their subobjects, stored in the *subobjects* arraylist field. Subobjects may transfer their onCollision to their host object using host.onCollision(<the other object>)
 
 ## Utility Objects
 ### Projectile
 //TODO
 ### TextObject
 //TODO
 ### BlockObject
//TODO
# SubObjects
//TODO
# Pathing Layer
A pathingLayer is an image file with the same dimensions as the world. This image however is made up of only a handful of colors with each of those colors representing a type of terrain. By default, there are four types defined: green= ground; red=hostile, blue= water; black= impassable.

GameObject2s can check what type of terrain they are on and it may effect movement. GameObjecs contain a **HashMap<PathingLayer.Type,Double>** called **pathingModifers**, which implements a speed modifier based on what type of terrain they are on. 1=standard speed, .5 = half speed, 2= double speed. *Modifiers of less than 0.05 will be considered impassable and the GameObject will NOT be able to move onto it*.

*Default Movement Modifiers: ground,hostile: 1.0 | water:0.33 | impass:0.0*

GameObject2s by default move regularly on ground and hostile terrain, slowly in water, and not at all in impassable terrain. Hostile terrain has no intrinsic function but an example use case is setting a player character object to take damage when it detects that the current terrain is hostile.

You can use a GameObject2's **getCurrentTerrain()** method to fetch the terrain type that the object is currently on. **Note: terrain is determined by the object's *exact* location, usually the object's center point.**

*Example:*

GameObject2 object = new GameObject2(new Coordinate(0,0));

object.pathingModifiers.put(PathingLayer.Type.water, .33); //the object moves slower in the water

### Adding Custom Terrain Types
Adding your own terrain type involves three modifications to the Framework.PathingLayer class.
1. Declare a static Color field for your terrain type. Put this next to the existing *public static final Color* fields at the top of the file
2. Add your terrain type's name to the PathingLayer.Type enum located in the PathingLayer.java file. Looks like this: *public static enum Type {ground, water, impass};*
3. Modify the **private static Type getType(int c)** method in PathingLayer class to incorporate your layer. There is a comment in the method to guide you. But in general you want to compare c to your color's getRGB() method. If its a match, return your new terrain type.
# Engine Options
### **Debug Mode** 
set with Main.debugMode field, this is the one of the most useful tools for viewing your scene on a technical level. This view replaces the background with the game's pathing map if applicable, renders hitbox outlines *(red=solid, blue=non-solid, grey=solid but preventOverlap is off)*; Object names; and orientation markers on all objects.
### **Overview Mode**
Zooms out on the scene allowing you to see the whole thing on screen. Enable with Main.setOverviewMode(boolean) and check with Main.overviewMode().
### **RenderDelay** 
slow the rendering process by this much. Lowers FPS and response time but smoothes performance on weaker hardware. Changed with **Main.renderDelay**
### **Triple Buffer (boolean)** 
If false, uses only a double buffer. More buffers require more cpu power but make things animate smoother. Changed with  **Main.tripleBuffer**
### **Ticks per Second**
How fast scenes run their tick method. Slows or speeds up the game relative to real time. lower number = slower game but smoother performance for weak hardware.
### **Resolution Scaling**
When you create a project that uses visual image assets, those assets are rendered pixel per pixel and their size (without in-engine scaling) is determined by the actual size of the image asset used. Ie: a 200x200 image will display over a distance of 200x200 in the game. The problem is that different screens have different resolutions than the screen you are testing your project on, so a character that looks large on your 1080p display will look tiny on a 4k display. To keep things looking uniform across all screen resolutions, set the final static field **NATIVE_RESOLUTION** in game class to reflect the resolution of you, the programmer's screen. Now you may call the **Game.scaleForResolution()** option and it will automatically scale your entire project to look the same on whatever screen size the project is run in as it does on the screen you are testing on.

# Audio
### Quick Start Guide
In JEngine, audio is played using the SoundEffect class. To instantiate an object of this class, you must provide a File as the parameter. This file is the source for the audio and must be a java supported audio format. I recommend .au format.

Once you have the SoundEffect instantiated, you can start it by calling its .start() method. Any prior experience with javax.sound.sampled libraries is very beneficial but not required. If you know the library, you can call getClip() to get the clip object and do what you want with it. Otherwise, the SoundEffect class has some built in utility methods that are easy to use via JEngine including pause/resume, restart, setLooping, setVolume, etc.

**See [SoundEffect-JavaDoc](https://webpages.uncc.edu/jdemeis/javadoc/Framework/Audio/SoundEffect.html) for all utility methods**

### Playing Sounds and Best Practices
Because creating a sound effect from a file is fairly expensive in terms of performance, as with any IO operation, you will want to do this only once per sound. To do this it is reccommended you have a static SoundEffect variable that will act as the 'source', and whenever you want to play that sound, create a copy of it (this does not require IO operations) and use that instead. 

### SoundEffect Example
***BASIC EXAMPLE***<br>
<pre>
SoundEffect s = new SoundEffect(new File("mySound.au")); //create effect from source
s.setVolume(.7f); //set volume to 70%
s.start(); //plays sound
</pre>
***BEST PRACTICE EXAMPLE***
<pre>
public class Example{
private static SoundEffect soundSource = new SoundEffect(new File("mySound.au"));
//soundSource acts as the source, loaded once at start of app
  public void playSound(){
    SoundEffect s = soundSource.createCopy(); //create copy of mySound.au sound effect without having to read from filestructure
    s.setVolume(.7f); //set volume of copy, not of source
    s.start();// play the copy
  }
}
</pre>

### Linking SoundEffects To Games
Linking a sound to a game will make that sound be part of that game rather than a simple global sound. Sounds that are linked to games will only play while that game is unpaused. SoundEffects linked in such a way only play if both they *and their linked game* are unpaused. Linked sounds are stored in the Game's **AudioManager**. Access all sounds linked to a game by using game.audioManager.getAllSounds();

### SoundEffectListener
You may want to detect and react to happenings on a sound effect. Implementing this interface then calling the .setListener method on the desired sound effect will allow you react to events in a sound effect. For example, override the onPause() method with a function that prints "the sound was paused!" to the console and every time the sound is paused, your listener will print that to the console. 

# Running Your .Jar Outside IDE
To run JEngine projects, you must run the java executable jar but with special options to allow it to run correctly. These are *-Dsun.java2d.d3d=false* for graphics and *-Xmx1024* for memory allocation. To make things easier, I have included a run.bat and run.sh for windows and ubuntu linux respectively; running these files will automatically run the jar with correct commandline parameters if it is in the same directory as the .jar file. Use shortcuts to these files to run the jar from a different directory. If you have an operating system that is not windows or ubuntu, you can create the file equivilent for your specific distrebution and have it exeute the following line of code (or call it directly via command line): 

**java -Dsun.java2d.d3d=false -Xmx1024m -jar 2DTemplate.jar**  
(note 2DTemplate.jar is name of project jar)
