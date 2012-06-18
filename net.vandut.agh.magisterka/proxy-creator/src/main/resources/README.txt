== Client code configuration ==
Package: %PACKAGE%
Interface: %PACKAGE%.%INTERFACE%
Default Server IP Address: %IP_ADDRESS%
Server ECF Port: %ECF_PORT%

== General Build Instructions ==
1. Install Maven (tested on 2.2.1 and 3.0.4, but any version should work)
2. Install ECF in OSGi environment of your choosing
3. Download package from Proxy-Creator form (YOU ARE HERE)
4. Change groupId and artifactId in pom.xml
5. (Optional) Modify IP ADDRESS (to other than default) in ClientActivator.java file
6. Run Maven:
    b) (Optional) "mvn package", to just create osgi bundle in the "target" directory
    a) or "mvn install", to install to local repository (+ bundle in "target" directory)
7. (Optional) Run "mvn eclilse:eclipse" to edit code in Eclipse as Eclipse Project
8. Deploy target/*.jar file in OSGi environment of your choosing
