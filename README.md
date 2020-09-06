# SpaceExplorer-AOOP
Advanced Object Oriented Programming | Space Explorer

![Image of Space Explorer GUI](https://github.com/staceysike/SpaceExplorer-AOOP/blob/master/spacegui.PNG)

The purpose of this assignment was to design and create an "item factory" GUI application, similar to a popular concept called 
"The blue ball factory", using Java and NetBeans IDE. The factory and items can be adapted to any concept but 
requirements include:
* Items will have specific charecteristics that determine how an item reacts with other items
* Items are run on their own threads
* Collisions must be detected
* Functionality for the user to add items
* Added items interact with 'static' items in the factory
* At least 2 different items
* Stop button functionality for the user to stop the factory

The 'factory' I created is conceptualized in outer space, with a rocket item and asteroid items (maximum of 5) for the user to add. These
items interact with eachother - an asteroid destorys a rocket. These two items also interact with the 'static' items in 
outer space - a blackhole and a floating shield (only for the rocket). The blackhole teleports the rocket to
another location on the GUI (it has special armour :smirk:), but destroys the weak asteroids. The shield protects the rocket
against asteroids and blackholes.

Rules:

Moving Items

Rocket
* Rocket hits Asteroid or Asteroid hits Rocket = Rocket destroyed
* Rocket hits Black Hole = Rocket teleports to a random location
* Rocket hits Shield = Rocket protected for 10 seconds, Shield despawns and respawns in 10 seconds
* Max of 1 rocket at once on the GUI

Asteroid
* Asteroid hits Rocket or Rocket hits Asteroid = Rocket destroyed
* Asteroid hits Black Hole = Asteroid destroyed
* Max of 5 asteroids at once on the GUI

Static Items

Black Hole
* Black Hole comes into contact with Rocket = Rocket teleports to random location
* Black Hole comes into contact with Asteroid = Asteroid destroyed
* One per round
* Placed randomly each new round

Shield
* Shield comes into contact with Rocket = Rocket protected for 10 seconds
* Placed randomly each new round
* Once hit, it will de-spawn. After 10 seconds, it will respawn again.
