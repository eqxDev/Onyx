# Onyx
Onyx is a free, open source Hardcore Factions plugin. It uses Redis for faction and player data.

### Is it finished?
Nope. I wouldn't recommend using it until I've decided there's enough functionality and the bugs are all fixed.
It's not going to be very quick because I haven't even started making it "efficient" until everything is in.

### Rights of use
The code on this repository is property of Eric Scott and PluginManager LTD. You must receive written or verbal permission from either parties in order to resell.
You can modify this code to your liking, but you may not resell.

### Compilation and Setup
_In order to run this plugin, you must have Redis and the Java VM installed on your machine of choice._

1. Clone this repository and navigate to it through the command line
2. Type "mvn clean install" while in the folder.
3. A folder called "target" should be created, the jar file is located there

Drag that generated jar file into your server plugins and restart/start it, 2 files should be generated called "settings.yml" and "locale.yml"; open "settings.yml".

In settings.yml, change your Redis database settings to the ones that match your Redis setup and save the file, then restart the server.

### Premium version
I'll be selling a premium version of this, it will come with full support and loads more features than this free model.
Sales will take place on MC-Market resources and (soon to be) Spigot.

### Bugs and issues
Please let me know about any bugs or issues you know about or have found, you can email me them at _ps-development@hotmail.com_ or make an issue complaint on the repo.
