
Umu Can Genlik

PURPOSE:

Through this assignment we will learn the use of simple Java Beans and Java Reflection. 

PERCENT COMPLETE:

We believe we have completed 100% of this project.

PARTS THAT ARE NOT COMPLETE:

None

BUGS:

None

FILES:

Included with this project are 9 files:

build.xml, ant script to compile the code
Driver.java, contains main method of program
JavaBeanProcessor.java, contains serialize and deserialize methods
MyBean.java, base class for beans
ChatMessage.java, sample bean class
Event.java, sample bean class
Mesh.java, sample bean class
README, the text file you are presently reading
run.sh, shell script to run the program

SAMPLE OUTPUT:

bingsun2% ./run.sh
<xmlStd>
<className xsd:String>serDeser.util.ChatMessage</className>
<message xsd:String>I love you!</message>
<sender xsd:String>Cenk</sender>
<buddyName xsd:String>Nesil</buddyName>
<timeStamp xsd:String>Sat Nov 14 23:40:33 EST 2009</timeStamp>
</xmlStd>

Message: I love you!
Sender: dada
Buddy Name: dada
Timestamp: Sat Nov 14 23:40:33 EST 2009

<xmlNew>
<className>serDeser.util.Event</className>
<message>
<java.lang.String>An Event Ocurred</java.lang.String>
</message>
<sequenceNumber>
<java.lang.Integer>1</java.lang.Integer>
</sequenceNumber>
<timeStamp>
<java.lang.String>Sat Nov 14 23:40:33 EST 2009</java.lang.String>
</timeStamp>
</xmlNew>

Message: An Event Ocurred
Sequence Number: 1
Timestamp: Sat Nov 14 23:40:33 EST 2009

<xmlVerbose>
<className>
<elementLength>18</elementLength>
<elementType>java.lang.String</elementType>
<elementValue>serDeser.util.Mesh</elementValue>
</className>
<x_coordinate>
<elementLength>1</elementLength>
<elementType>java.lang.Integer</elementType>
<elementValue>3</elementValue>
</x_coordinate>
<y_coordinate>
<elementLength>1</elementLength>
<elementType>java.lang.Integer</elementType>
<elementValue>5</elementValue>
</y_coordinate>
<value>
<elementLength>4</elementLength>
<elementType>java.lang.Double</elementType>
<elementValue>3.14</elementValue>
</value>
</xmlVerbose>

X coordinate: 3
Y coordinate: 5
Value: 3.14

TO COMPILE:

Assuming you are in the directory containing this README:

## To clean:
ant -buildfile src/build.xml clean

## To compile: 
ant -buildfile src/build.xml

TO RUN:

chmod +x run.sh
./run.sh


