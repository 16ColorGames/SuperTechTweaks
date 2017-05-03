# SuperTechTweaks
Want to help out?
Here is a list of tasks I'm currently working on:
* Dynamic hardness for ore based on ore contents
* Fix vein generation errors (tends to escape min and max y and create "chunky" lines)
* Other generators (Have fun!)


# How to get set up

1. Fork the repo (Top Right corner of the main github page)
2. Clone your fork (depends on your setup)
3. Download Forge MDK 2221 for 1.10.2 (https://files.minecraftforge.net/maven/net/minecraftforge/forge/index_1.10.2.html and click show all)
4. Copy the eclipse and gradle folders from the MDK to your cloned repo
5. Run "gradlew setupDecompWorkspace" from your main repo folders
6. Run "gradlew eclipse" from your main repo folder
7. Open Eclipse with your workspace set to the eclipse folder you copied to your repo folder