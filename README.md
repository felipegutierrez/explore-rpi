# Raspberry Pi and Apache Edgent

This project is to have fun with Raspberry Pi's and Apache Edgent.


## Requirements

 - Connect a [HC-SR04 sensor](https://www.modmypi.com/blog/hc-sr04-ultrasonic-range-sensor-on-the-raspberry-pi) on your Raspberry Pi.
 - Install Java 8 `apt install oracle-java8-jdk`.
 - Install [wiringPi](http://wiringpi.com/download-and-install/) and use the command `gpio readall` to check the RPi pins against the WiringPi library.
 - add the line `export JAVA_TOOL_OPTIONS="-Dpi4j.linking=dynamic"` on the `/home/pi/.bashrc` file.
 - Install maven `sudo apt install maven`
 - Install MQTT server `sudo apt install mosquitto`

## Execution

Create a jar file `mvn package` and execute it on your Raspberry Pi: `java -jar explore-pi.jar`. Or execute the jar file already with the parameters, e.g.: `java -jar target/explore-rpi.jar 11 192.168.56.1`.

## Troubleshooting



