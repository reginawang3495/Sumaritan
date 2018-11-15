# Sumaritan

## What it does:
Sumaritan is a software that automatically summarizes notes or textbooks with camera input for students and allows the visually impaired to function normally in their daily lives by reading aloud important signs.

## Requirements to use this application
1. Java JRE 1.8.0+ 
2. Java JDK 10.0.0+
3. API Keys for Aylien and Google Cloud. 

## To run the demo, 
1. Edit Sumaritan/Demo/Sumaritan.jar 
      Move /Demo/Jars into /Demo/Sumaritan.jar
2. Move to right folder by running
   cd /path/to/Sumaritan/Demo 
3. Run the jar file, by running
   java -jar Sumaritan.jar

## To edit and run the application without the demo, 
1. Add API Keys and Client Ids 
On command line run:
2. To move to right folder, run 
   cd /path/to/Sumaritan/SDHacks 
3. Add exterior jars, found in the jar file
4. To compile, run
   javac src/CamHacks/*.java
5. To run, run 
   java CamHacks.ApplicationMaster
