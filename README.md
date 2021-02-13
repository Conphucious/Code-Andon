<p align="center">
  <a href="https://github.com/Conphucious/Code-Andon">
   <img src="icon.png" width="80" height="80">
  </a>

  <h3 align="center">Code Andon</h3>

  <p align="center">
    Programmable Tower Light System
    <br />
    <a href=""><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="">View Demo</a>
    ·
    <a href="https://github.com/Conphucious/Code-Andon/issues">Report Bug</a>
    ·
    <a href="https://github.com/Conphucious/Code-Andon/issues">Request Feature</a>
  </p>
</p>

## About The Project
This project is a software implementation of Andon Systems used in lean manufacturing. 

- When code compiles successfully, the tower turns <b>GREEN</b>. 
- When code generates warnings, the tower turns <b>YELLOW</b> 
- When code fails to compile or generates runtime errors, the tower turns <b>RED</b>.

These errors are reliant on using SF4J logger to parse the logs. This application serves as a serial communicator to the 
Arduino microcontroller which controls the 12VDC Tower Light (hooked up to a 12VDC relay).

This project is a serial driver to interfacing with a microcontroller (Arduino in particular) which controls a 12vdc Tower Light.


### Built With
* Java 8
* Maven 3.6.1



## Getting Started
To get a local copy up and running follow these simple steps.

### Installation
* Download the [files folder](https://github.com/Conphucious/Code-Andon/tree/master/files)
* Setup the build scripts
  - If you are running on CLI, you can run the script as is.
  - If you are running on an IDE like IntelliJ, edit your run configuration and set this script as your build task
  ```sh
  chmod 755 build_mvn.sh
  ./build_mvn.sh ~/LOCATION_TO_YOUR_MVN_PROJECT ./build.log true
  # The true flag will toggle whether your want to ignore mvn install warnings.
  ```
* Open the [code-andon.ino](https://github.com/Conphucious/Code-Andon/blob/master/files/code-andon.ino) file in Arduino IDE and upload the sketch to your arduino
  - you may need to change the pin numbers accordingly to how religiously you follow the wiring schematics
* Run [code-andon.jar](https://github.com/Conphucious/Code-Andon/blob/master/files/jar-app/code-andon.jar) via double click though you can run ```java -jar code-andon.jar``` to see any output logs
  - You will need to set (in the app) your build logs to wherever your build.log was specified in the build_mvn Arg2
  - You will need to set (in the app) your runtime logs to wherever you pointed the stdout redirect (log output) on your IDE
  - You will need to set (in the app) your Arduino COM port.

### Usage
Once everything is configured above, you simply launch the app each time you want to use it, press start, and press stop/exit when you are done. Everything can be configured in the set menu items.

## Roadmap
* Stdout logs
* Log viewer in GUI
* Compatibility with other applications
* Compatibility with DevOps services such as Azure DevOps/Jenkins.


<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* JSCC (Java Simple Serial Connector)
