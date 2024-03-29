# Nautilus [![CircleCI](https://circleci.com/gh/Kaioru/nautilus.svg?style=shield&circle-token=a552425db0e34ff7d0d696b421389cc25614b5d6)](https://circleci.com/gh/Kaioru/nautilus)
Nautilus is a modular server emulator.

## Features
**Distributed** - Nautilus makes use of Java's RMI API to have a distributed architecture which allows server administrators to easily scale the service depending on the load.

**Flexibility** - Need a 2nd-level cache for your database connections? Or a database connection pool like HikariCP? Just edit the config files, no additional work required!

**Modular** - Use specific parts of Nautilus in your application without the junk you don't need.

## Building/Developing
### Step 1
Clone the repository with `git clone https://github.com/kaioru/nautilus`.
### Step 2
Import the cloned repository as a Maven project in your preferred IDE.
### Step 3
Download and configure your IDE for the following:
* [Project Lombok](https://projectlombok.org/)
* [Hibernate Metamodel Generator](http://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html)
### Step 4
Use the `mvn install` command to download any missing dependencies.

Aaand.. you're set!

## Acknowledgements
* [@ddolpali](https://github.com/ddolpali) - Random advice and stufferinos.
* [Zygon (RaGEZONE)](http://forum.ragezone.com/members/515335.html) - For the Desu project which is referenced when making this project.
* [kevintjuh93 (RaGEZONE)](http://forum.ragezone.com/members/501793.html) - For the MoopleDEV project which is referenced when making this project.

## Disclaimers
* This software has not profitted in any way, shape or form.
* This software is made purely for educational purposes only.
* This software is not limited to the stated game and can be used freely as any server protocol library.

No EULA's and ToS's were meant to be violated. Please contact the author if so and will be dealt with asap. 
