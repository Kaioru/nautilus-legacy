# Nautilus Tiny
This module contains the TinyServer and TinyClient applications.

## Usage - TinyServer
### Options
```
-majorversion	(Required) Determines the game's Major Version
-minorversion	(Required) Determines the game's Minor Version
-locale		Determines the game's Locale, defaults to 8
-port		Determines the TinyServer's port, defaults to 8484
```
### Example
Running the TinyServer jar with the options `-majorversion 62 -minorversion 1 -port 1000` would run the TinyServer with the v62.1 handshake and on port 1000.

## Usage - TinyClient
### Options
```
-host		Determines the Server's host, defaults to localhost
-port		Determines the Server's port, defaults to 8484
```
### Example
Running the TinyClient jar with the options `-port 1000` would run the TinyClient to connect to localhost on port 1000.
