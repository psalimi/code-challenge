# code-challenge
## Pre-requisites:
This project uses Gradle Build Tool to run the program and test cases. Please make sure Gradle is installed on the machine before running the program. Below are the instructions to install gradle:

If on a Mac you can use homebrew to install gradle. For instructions on installing gradle manually please refer to the following link: https://gradle.org/install/:
```sh
$ brew install gradle
```
Verify that gradle was installed successfully using the following command:
```sh
$ gradle -v
```
This program uses java as the main language. Please make sure Java SDK is intalled on the machine before running the program.

## Running the program
To run the program use the following command. The program requires an argument which will be used to find the file that will be used to generate the driver history report. The syntax of the commands included in the file where derived from the instructions given for this code challenge. Two files are included as examples used to generate the driver history and can be found in "src/main/resources/files/":
```sh
$ gradle run --args="file-path"
$ gradle run --args=src/main/resources/files/input1.txt
```
The program uses the input file and creates a new file called "driverHistory.txt" which consists of the name of the driver, the total miles driven and the average speed for each driver. This file can be found under the build directory "build/resources/main/files/".

### Tests
In order to run the test cases use the following command:
```sh
$ gradle test
```

## Development
In order to calculate the driver history for each driver based on their trips, I decided to create two models called Driver and Trip. The Trip model contains information about each trip (startTime, endTime, milesDriven). The Driver model contains the name of the driver, a list of all the trips for that driver, total distance, total time driven, and the average speed. TrackDrivingHistoryService class is the main part of the program which handles all the data manipulation, calculations and verification. 

### Verification
The method isDataValid verifies that the syntax and information in each line of the input file is correct based on the command type (Driver or Trip). If data is invalid the line is ignored and a message will be logged informing the user. 

In order to avoid complex calculations and reduce the time complexity each time a trip is added to the driver, the average speed, total time and total distance is re-calculated by updating those fields. This way when the report is generated, it is not necessary to calculate this information as it is already available. 

I decided to store the drivers in a hashmap instead of an array in order to reduce the time complexity of finding that driver to O(1). Everytime a new trip is added the driver is looked up in the hashmap and the trip will be added to the list of driver's trips while updating the average speed and total distance traveled at the same time.

There is defintely room for improvement to make this code more solid. For example instead of storing the whole trip object for in the driver class, we could generate IDs for each trip and only store the ID in that list. This would be better as our data grows larger.


