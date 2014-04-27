ChatStrings
===============

Class that parses chat strings for @mentions, (emoticons), and http:// urls links.

I am using Jsoup to get parse the html links and get their title attribute. I am also using Gson 
to build the JSON objects and convert them to Strings. I am using Gson because it is a very
fast JSON parser and allows me to use annotated objects to describe my JSON objects.

To run this file you would run ChatStringsExample which has a main method and sample strings.
To compile the example:
$ javac ChatStringsExample.java 
Then to run it:
$ java -classpath gson-2.2.4.jar:jsoup-1.7.3.jar:. ChatStringsExample

---------
If you want to edit the main class, ChatStrings, you would need to recompile it:
To compile ChatStrings.java you must include Jsoup and Gson in the class path and execute: 
$ javac -classpath gson-2.2.4.jar:jsoup-1.7.3.jar ChatStrings.java

To compile the tests:
$ javac -cp .:junit-4.11.jar ChatStringsTest.java
To run the tests you would execute in the main directory: 
$ java -cp .:junit-4.11.jar:hamcrest-core-1.3.jar:gson-2.2.4.jar:jsoup-1.7.3.jar org.junit.runner.JUnitCore ChatStringsTest

