README for the Connect Four project.


Software Requirements Specifications


Introduction:

A digital version of the game “Connect Four” will be implemented as an application for the Android Operating System on smart phones and tablets. 
Connect Four is a two-player game in which the players first choose a color and then take turns dropping coloured discs from the top into a seven-column, six-row vertically-suspended grid. The pieces fall straight down, occupying the next available space within the column. The object of the game is to connect four of one's own discs of the same color next to each other vertically, horizontally, or diagonally before your opponent (Wikipedia).


Overall: 
 
Program characteristics:
	- The game will initially be setup to be played by 2 human players who will take turns using a single platform (an Android smartphone or tablet) to play a head to head game of connect four.

User characteristics:
-	Smartphone and tablet game users of all ages are the target users.
-	The user is not expected to have any specific knowledge and all required input must be clearly explained and made to be intuitive.


Specifics:

a) Functionality. (What is the software supposed to do?)
When started the program must:
1- Display a menu allowing the first player to enter a name and choose a disc color.
	2- Repeat for the second player.
	3- Display the 7x6 grid and allow the players to sequentially add discs into the columns until four discs of the same color are lined up or no more moves are possible.
	4- Display a message stating the winner, if any, or that the game is over.
	5- Return to first menu 

Types of Gameplay to be implemented	Importance
Human vs. Human gameplay  (on same device)	- Essential
Human vs. CPU opponent				- Desired
Human vs. Human over the internet gameplay	- Optional (if time permits)
 
b) External interfaces. How does the software interact with people, the system’s hardware, other hardware, and other software?
- Input: Smartphone or Tablet touchscreen input (with virtual keyboard if required).
- The app is programmed in Java therefore interacts with the hardware through the JVM.

c) Performance. What is the speed, availability, response time, recovery time of various software functions, etc.?
	- The game must save its state when the platform (phone or tablet) switches to another activity (e.g. a phone call is received).

d) Attributes. What are the portability, correctness, maintainability, security, etc. considerations?
	- There is no intent to port the game to any other platform.
- Maintenance will be performed by a separate team therefore code must be commented and documentation kept up to date with all changes.
- There should be documentation/hints that explain on features of the software

e) Design constraints. Are there any required standards in effect, implementation language, resource limits, operating environment(s) etc.?
- The game is intended to run on Android Version 4.0 (???)
- Android OS constraints: 
- The User Interface layout will be specified in XML.
- Java will be used to code the program.

