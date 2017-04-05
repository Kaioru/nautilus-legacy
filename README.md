# Nautilus [![CircleCI](https://circleci.com/gh/Kaioru/nautilus.svg?style=shield&circle-token=a552425db0e34ff7d0d696b421389cc25614b5d6)](https://circleci.com/gh/Kaioru/nautilus)
Nautilus is a modular server emulator for a certain online game.

## Setup
### IntelliJ IDEA
#### Step 1
Clone the repository with `git clone https://github.com/kaioru/nautilus`
#### Step 2
Open up IntelliJ IDEA and import the project with Maven.
#### Step 3
Download and/or follow the guides listed below
* [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok-plugin)
* [Hibernate Metamodel Generator](https://docs.jboss.org/hibernate/orm/5.0/topical/html/metamodelgen/MetamodelGenerator.html)
#### Step 4
Use the `mvn clean package` command to test for any build errors.
#### Step 5
Aaaand you're set, Happy coding~

## Architecture
The Nautilus is divided into multiple cluster and shards.
### World
A center cluster that handles shard connections and heartbeat.
Multiple world clusters can be started for multi-world.
### Login
The login shard handles authentication, world, channel and character selection.
A login shard can be connected to multiple world clusters to allow multi-world.
### Channel
The channel shard handles game-play and migration between neighbour channels.

This project is for educational purposes only.
