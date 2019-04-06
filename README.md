# Java Reflexion example with dynamically created Java's classes using DSL (Short & Fast )

## Init
  * Clone it as Maven Project 
## DSL ( Domain Specific Language ) Structure  
#### Commands : 
  * CREATE - creates the .java file with auto-generated java code and .class
  * UPDATE (not implemented)
  * REMOVE (not implemented) 
#### Attributes Init 
 You can start adding class attributes after the **WITH** identifier
#### Attributes Structure 
 `Description = "Description" as String`
#### Full example of the DSL file
`CREATE TheSun WITH Description = "IzHot" as String,Time = "200" as int,Distance = "123124124" as int,isHot = "true" as boolean`

## Project Structure 
There are two threads working in the background: 
* The first thread reads the DSL files and generate the .java and .class files (DSLCompiler)
* The second thread is looking for .class files to execute their `run` method (Universe)
* Also every dynamically created object is a running thread with an expiration date
