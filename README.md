## **leads-deduplicator**

This project deduplicates a given JSON as per the rules.

## **Introduction**

This project removes the duplicates from code_challenge_leads.json and generates output file without the duplicates.

## **Installation & Usage**

To install Project Title, follow these steps:

1. Clone the repository: **`git clone https://github.com/patilds/leads-deduplicator.git`**
2. Navigate to the project directory: **`cd leads-deduplicator`**
3. This has been developed on JDK22.
4. Install openjdk22, mvn packages and install dependencies: mvn clean install, this should download and install dependencies.
5. For simplicity, just open src/main/java/main/Main.java in a Java Editor like IntelliJ or eclipse and run the main function. 
6. This should compile and run the java project and generate output.json & the console log shows the exact difference between old record and new record and which attribute changed (as per requirement) (I have included it as part of this project)
7. Alternatively, a JAR can be run of this program, which should give the exact same output.

## **Useful Commands**
To build standalone JAR with dependencies,
> mvn clean compile assembly:single

To run the JAR,
> java -jar ./target/leads-deduplicator-1.0-SNAPSHOT-jar-with-dependencies.jar "code_challenge_leads.json"