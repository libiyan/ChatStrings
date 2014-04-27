ChatStrings
===============

Class that parses chat strings for @mentions, (emoticons), and http:// urls links.

To run this file you would run ChatStringsExample which has a main method and sample strings.
To compile the example:
$ javac ChatStringsExample.java 
Then to run it:
$ java -classpath gson-2.2.4.jar:jsoup-1.7.3.jar:. ChatStringsExample

Then you would do: java StringFrequencyExample test_docs/war_and_peace.txt
I am using war_and_peace.txt, but you can pass it any other txt file.

To run the tests you would execute in the main directory: 
$ java -cp .:junit-4.11.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore StringFrequencyTest

To compile ChatStrings.java you must include Jsoup and Gson in the class path and execute: 
$ javac -classpath gson-2.2.4.jar:jsoup-1.7.3.jar ChatStrings.java
