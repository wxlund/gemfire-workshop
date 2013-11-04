# gemfire-workshop

This project is meant to be used as the basis of a GemFire 1/2 to 1 day development workshop.  It makes extensive use of the Spring GemFire project to simplify development, and embeds the GemFire server in many of the examples to ease demonstrations.  It is does not require you to download GemFire, as it will automatically retrieve the necessary dependencies via Maven, and projects classes that allow you to bootstrap GemFire using Spring GemFire.

The test scenarios are all driven via the embedded text console in STS, so you can quickly walk through the relevant code in the Client.java or various Datanode java classes.

There is a coorseponding presentation you can use to guide the workshop at https://drive.google.com/a/gopivotal.com/file/d/0BxB8rdXPgFrNZFZzUXVZTHAzVG8/edit?usp=sharing 

Please enter an enhancements as issues in github, or, better yet, clone this project and send a pull request with your changes. ;)

## Requirements
* STS

## Use

![Screen capture of the Import Projects dialog](/doc/imgs/import-projects.png)

To use this project, start by cloning it from github.com.  After you've cloned a copy locally, open up STS.  Select the *File->Import...* menu option, type "Existing Maven" into the filter text box.  Select the "Existing Maven Projects" item in the tree, and click the *Next* button.

![Screen capture of the Import Maven Projects dialog](/doc/imgs/import-maven-projects.png)

In the resulting dialog, click the *Browse* button to browse to the folder you cloned this project into and click *OK*.  Once STS detects the project pom.xml and selects it, click the *Finish* button at the bottom of the dialog.

![Screen capture of the Included Launchers in the Run Configuration dialog](/doc/imgs/run-configurations.png)

Now that the project is imported, you can use the Run Configurations included in the project *launches* folder to run your demos.  To see the included launchers, you need select the *Run->Run Configurations...* menu option.  You will noticed items are prefixed with numbers to indicate the general order you should launch the items.

### Basic GemFire Demo
To run through a basic GemFire demo, you can execute the *2-Locator*, *3-Datanode*, and *4-Client* launches.  From there, simply select the various menu options in the client and server consoles to run through various features of GemFire.  Typically, before executing a function, you should go to the relevant section in the Client or Datanode class to walk through it, and then go to the corresponding spring-cache-client.xml and spring-cache-server.xml Spring configurations to examine the configurations involved with that function.

------

![Screen capture of the New Console Function](/doc/imgs/new-console.png)

To more easily see what is going on without having to click as much, it is suggested that you add an additional Console view and pin your previous view and new view to the Datanode and Client launches you executed.  To do this, click on tab for the *Console* view if it isn't showing.  Next, click the *New Console* dropdown in your current *Console* view, and select the *New Console View* option.  This will create a new Console View that we can use to display the server.  

Next we need to pin the consoles to specific processes, or they will jump to different ones depending on what output is generated.  To pin a console, first click the *Pin Console* button in the console you want to pin.  Next, click the *Display Selected Console* drop down and assign the "Datanode" process to that console.  Do the same thing for the "Client" process in the other console.  

Finally rearrange the consoles so that you can easily see them both.  Simply click and drag the tab for the console you wish to reposition.  It can be a bit tricky to get the consoles in the right positions.  To make them line up side by side, simply drag the tab to the left or right side of the area displaying the text for the console you wish to place it next to.  For an above and below alignment, simple drag the console to the top or bottom of the area displaying the text of the console you wish to position it next to.  Now, you can easily see both the Datanode and client processes.

-------

Both the Client and the Datanode have functions that you can run.  At startup, the server loads some test data from the CSV files in the "src/main/resources/data" folder.

#### Client Functions
1.	Populate Dummy
	This function loads 1000 "Dummy" entities into the "Dummy" partitioned region.  This load is done from the client, and it the most inefficient way to load data.  It is simply an example to show the put function.  You will also see log entries on the Datanode due to a cache-writer installed on that region that simply logs out entries before they have been added to the cache.
	
2.	Update Dummy
	This function puts an entry into the Dummy region, and then immediately overwrites the first entry with a new entry to show an update.  The first time this function is run, there is no entry with a key of "1" in that region, so it will first add a new entry.  The second update and all subsequent executions will cause updates to occur.  You can see these updates on the server as log entries in the console.
	
3.	OQL for Dummy
	This function executes an OQL query for data that contains a value of "200" for the field2 property.  There is no server side output for this.  Typically you should run this after the Update Dummy client function so that you get multiple entries back.
	
4.	Declarative caching
	Spring allows you to use GemFire in an very unobtrusive way with your existing code.  You can use GemFire as a caching layer to wrap existing method calls with AOP so that you don't have to modify your existing source code.  This example invokes a DAO that is wrapped with the Spring caching aspect that is pointed to our "Dummy" region.  The first execution, the DAO method is actually invoked, as can be seen from the log entry on the client.  But the result of that invocation is stored in the cache, as we can see from the log entry on the Datanode.
	The next step on the client retrieves the entity again, and we can see that the second method call doesn't actually call the DAO method at all, and simply returns the cached value from the "Dummy" region in GemFire.
	The final two steps showcase removal of entries from the cache.  Both invocations actually call the DAO method for removal.  However, the first one causes a removal from the cache, and the second one simply invokes the DAO method.
	
5.	Distributed Function
	Currently not functioning.
	This function executes a distributed function call on all the Datanodes that are hosting the "DUMMY" region.  This function simply sums up all the values for the field2 property on the first 500 entries in the region.  This an example of a data-aware function as we are passing in a list of keys to operate on.
	
6.	Put for expiry
	This function puts an entry into the "DUMMY_EXPIRE" region on the server.  This region is configured to expire entries from the region 1 second after they are put into the region.  You can see this from the log entries in the Datanode.
	
7.	Put for eviction
	This function puts an entry into the "DUMMY_EVICT" region on the server.  This region is configured to spill entries out to disk the second entry in the region is added.  The difference here is that the key is still kept in memory, but the data is actually transparently overflowing to disk.
	
8.	Programmatic CQ Registration
	This function shows the process of registering a dynamic continuous query from the client.  This function requires you to go to the Datanode console, and then issue the "Update Dummy Data" function to see the results of the dynamic continuous query in the client.
	
9.	Manual join vs. Distributed Function Join on all data
	This function shows the difference in executing a join by pulling all the data back to the client, vs. executing a distributed function to do the join for you.  The time difference isn't all the great on a single machine, unfortunately.
	
10.	Manual join vs. Distributed Function Join on a single key
	This function shows the difference in executing a join by pulling two rows back to the client, vs. executing a distributed function to do the join for you.  The time difference isn't all the great on a single machine, unfortunately.
	
#### Server Functions
1.	Populate Dummy Data
	This function is similar to the client function of the same name, but it is meant to exhibit the fact that a server can operate on its local regions just like a client can.  This can provide for faster loading as the client doesn't have to be a hop.
	
2.	Update Dummy Data
	The server can update data as well of course.  But more importantly, this function is meant to work hand in hand with the client function for Programmatic CQ Registration.  When that function is executed on the client, then you can use this function on the server to update data that the client will see as a result of that CQ registration.
	
3.	Add Dummy Data for Client Interest
	The client has been configured for interest registration for certain types of data in the spring-cache-client.xml.  This function allows you to add data to the region to allow this listener to be invoked on the client.  You can see the effects of this update from the client console log entries.

4.	Add Dummy Data for Client CQ
	This function is primarily meant to work with the automated CQ registration that has been done via the spring-cache-client.xml configuration.  This is different from the client function for doing a Programmatic CQ registration, and shows a different log message in the client console as a result.
	
### Database Backed Datanode
Not converted yet.

### WAN Replication
Not converted yet.

### Secure Client and Server
Not converted yet.
