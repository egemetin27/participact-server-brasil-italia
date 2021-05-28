# ParticipACT Brasil Server
---
![N|Solid](http://www.esag.udesc.br/templates/centro_esag/imagens/logo_cabecalho.png)  

**ParticipACT Brasil**, Tecnologias inovadoras na gestão da cidade inteligente.  

# Prerequisites
---
###### Server:
* [JDK 7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [Tomcat 7](http://developer.android.com/sdk/index.html)
* [Postgres 9](https://www.postgresql.org/download/)
###### Workspace:
* [Eclipse (Java EE) Neon or higher](http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/neon1rc3)
* [Gradle last version](http://www.gradle.org/downloads)
* [Apache Tomcat Eclipse Integration](https://www.mulesoft.com/tcat/tomcat-eclipse)

# Resume
---
###### How to import in eclipse:
1. install gradle http://www.gradle.org/downloads
2. run: gradle eclipse
3. start eclipse
4. File -> Import -> Existing Projects into Workspace
5. select participact-server
6. right click on the project and choose Configure -> Convert to Gradle Project
_to use with GCM (google cloud messaging): add API key in it.unibo.participact.domain.PANotificationConst.java
change package_name according to the new app id_

###### Set up Tomcat:
Add a Charset Encoding filter to support UTF-8: edit web.xml of tomcat (v7.x)
   and add the following filter as *first* filter:
```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.apache.catalina.filters.SetCharacterEncodingFilter</filter-class>
    <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
    </init-param>
</filter>

<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```    
Edit the Connector to interpret URIs as UTF-8:
```xml
<Connector URIEncoding="UTF-8" connectionTimeout="20000" port="8080" protocol="HTTP/1.1" redirectPort="8443"/>
```
###### Set up PostgreSQL:
In the installation procedure was created a user account called postgres that is associated with the default Postgres role. In order to use Postgres.
```sh
# sudo -i -u postgres
```

Get a Postgres prompt immediately
```sh
$ psql
psql (9.6rc1)
Type "help" for help.

postgres=#

```

Create a new database
```sh
postgres=# CREATE DATABASE participact_db WITH OWNER = postgres ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8' CONNECTION LIMIT = -1;
```

Check if OK
```sh
postgres=# \list

                                    List of databases
      Name      |  Owner   | Encoding |   Collate   |    Ctype    |   Access privileges
----------------+----------+----------+-------------+-------------+-----------------------
 participact_db | postgres | UTF8     | en_US.UTF-8 | en_US.UTF-8 |

```

Inside ``` /src/main/resources/META-INF/persistence.xml ``` you can find a row like this:
```xml
<property name="hibernate.hbm2ddl.auto" value="validate" />
```
you have to set like this for table creation:
```xml
<property name="hibernate.hbm2ddl.auto" value="create" />
```
At second run, you have to put back **validate**. In this way, data will be preserved between different sever runs. Otherwise, every time on startup, database will be recreated and data stored will be deleted. There is
also a third option: **update**. With this option modify you can modify database model adding or removing tables or column using Java mapping and this changes will be reflected on the DB after restart. In this case, stored data should be preserved.   

Default Login   
* username: **root**
* password: **secret**

# Installation Instructions
---
##### With great power comes great responsibility.
Root privileges are required to build and install packages.
```sh 
$ sudo -i
We trust you have received the usual lecture from the local System
Administrator. It usually boils down to these three things:

    #1) Respect the privacy of others.
    #2) Think before you type.
    #3) With great power comes great responsibility.
```
##### Download and install Java 7  
Create a new directory to files
```sh 
# mkdir /usr/local/java
``` 
Download the latest version of Java 7 (Java SE Development Kit 7uXX)
```sh
# wget --no-cookies http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz
``` 
Unpack it using tar
```sh 
# tar zxpvf jdk-7u79-linux-x64.tar.gz
``` 
Set the Java Home and will put Java into the path of users
```sh 
# JAVA_HOME=/usr/local/java/jdk1.7.0_79/
# export  JAVA_HOME
# PATH=$JAVA_HOME/bin:$PATH
# export PATH
``` 
Verify that Java is installed 
```sh
# java -version
``` 
Should print out
```sh 
java version "1.7.0_79"
Java(TM) SE Runtime Environment (build 1.7.0_79-b15)
Java HotSpot(TM) 64-Bit Server VM (build 24.79-b02, mixed mode)
``` 
##### Download, install and configure Tomcat 7
Create a new directory to files
```sh 
# mkdir /usr/local/tomcat && cd /usr/local/tomcat/
```
Download and install Tomcat 7
```sh 
# wget http://ftp.unicamp.br/pub/apache/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72.tar.gz
```
Unpacked and installed
```sh 
# tar zxpvf apache-tomcat-7.0.42.tar.gz
```
To configure Tomcat to launch automatically create a file called **Tomcat** in the directory ```/etc/rc.d/init.d/``` with the following contents:
```bash
#!/bin/bash
###############################
# Tomcat init script for Linux.
###############################
# chkconfig: 2345 96 14
# description: The Apache Tomcat servlet/JSP container.
JAVA_HOME=/usr/local/java/jdk1.7.0_79/
CATALINA_HOME=/usr/local/tomcat/apache-tomcat-7.0.72
export JAVA_HOME CATALINA_HOME
exec $CATALINA_HOME/bin/catalina.sh $*
```
Set the proper permissions 
```sh 
# chmod 755 /etc/rc.d/init.d/tomcat
```
Enable Tomcat for auto-launch
```sh 
# chkconfig --level 2345 tomcat on
```
Tomcat should now be automatically launched whenever your server restarts. You can start and stop the Tomcat manually with the command:
```sh 
# /etc/init.d/tomcat start
# /etc/init.d/tomcat stop
```
To set up the Tomcat users. This will allows access to the Manger Console in the Tomcat interface. The users are configured in a file called **tomcat-users.xml** which is stored in the **apache-tomcat-7.0.72/config directory**. Open this file using nano 
```sh 
# vi /usr/local/tomcat/apache-tomcat-7.0.72/conf/tomcat-users.xml
```
and edit the user permissions as below, changing the password as appropriate:
```xml 
<?xml version='1.0' encoding='utf-8'?>
<tomcat-users>
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<role rolename="manager-jmx"/>
<role rolename="manager-status"/>
<role rolename="admin-gui"/>
<user username="tomcat" password="********" roles="manager-gui"/>
</tomcat-users>
```
Add a Charset Encoding filter to support UTF-8: **edit web.xml** of tomcat (v7.x) 
```sh 
# vi /usr/local/tomcat/apache-tomcat-7.0.72/conf/web.xml
```
and add the following filter as *first* filter:
```xml
<filter>
   <filter-name>CharacterEncodingFilter</filter-name>
   <filter-class>org.apache.catalina.filters.SetCharacterEncodingFilter</filter-class>
   <init-param>
        <param-name>encoding</param-name>
        <param-value>UTF-8</param-value>
   </init-param>
   <async-supported>true</async-supported>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```
To configure the URL encoding in Tomcat. Edit **conf/server.xml** and find the line where the HTTP Connector is defined.
```sh 
# vi /usr/local/tomcat/apache-tomcat-7.0.72/conf/web.xml
```
It will look something like this:
```xml
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```
Add a **URIEncoding="UTF-8"** property to the connector:
```xml
<Connector URIEncoding="UTF-8" port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```
```(Optional)``` If you have a firewall, remember to open port 8080 to the world.
```sh
# iptables -A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT -m comment --comment "Tomcat Server port"
# service iptables save 
```
##### Download, install and configure PostgreSQL 9
Requirements : 
* GNU make is required; other make programs will not work. GNU make is often installed under the name **gmake**;
* **tar** is required to unpack the source distribution, in addition to either **gzip** or **bzip2**.
* readline library : **readline-devel** and **readline** to compile.
```sh
# gmake --version
Copyright (C) 2006  Free Software Foundation, Inc.
This is free software; see the source for copying conditions.

# tar --version
tar (GNU tar) 1.23
Copyright (C) 2010 Free Software Foundation, Inc.
License GPLv3+: GNU GPL version 3 or later <http://gnu.org/licenses/gpl.html>.

# gunzip --version
gzip 1.3.12
Copyright (C) 2007 Free Software Foundation, Inc.
Copyright (C) 1993 Jean-loup Gailly. ...

# yum install readline-devel.x86_64 readline.x86_64
```

Download/Getting the source [from postgreSQL](https://www.postgresql.org/download/).
```sh
# cd ~
# wget https://ftp.postgresql.org/pub/source/v9.6rc1/postgresql-9.6rc1.tar.gz
```
Unpacked
```sh
# tar zxpvf postgresql-9.6rc1.tar.gz
```
Configuration and install
```sh
# cd postgresql-9.6rc1/
# ./configure
# gmake
# gmake install
```
Verify the postgreSQL directory structure. After the installation, make sure bin, doc, include, lib, man and share directories are created under the default /usr/local/pgsql directory as shown below.
```sh
# ls -l /usr/local/pgsql/
total 16
drwxr-xr-x. bin
drwxr-xr-x. include
drwxr-xr-x. lib
drwxr-xr-x. share
```
Create postgreSQL user account
```sh
# adduser postgres
# passwd postgres
Changing password for user postgres.
New UNIX password:
Retype new UNIX password:
passwd: all authentication tokens updated successfully.
```
Create the postgres data directory and make postgres user as the owner.
```sh
# mkdir /usr/local/pgsql/data
# chown postgres:postgres /usr/local/pgsql/data
# ls -ld /usr/local/pgsql/data
drwxr-xr-x. 2 postgres postgres 4096 /usr/local/pgsql/data
```
Initialize postgreSQL data directory
```sh
# su - postgres
$ /usr/local/pgsql/bin/initdb -D /usr/local/pgsql/data
```
Start postgreSQL database
```sh
$ /usr/local/pgsql/bin/postgres -D /usr/local/pgsql/data >logfile 2>&1 &
[1] 32099
$  cat logfile
LOG:  database system was shut down at 2016-09-22 20:30:34 BRT
LOG:  MultiXact member wraparound protections are now enabled
LOG:  database system is ready to accept connections
LOG:  autovacuum launcher started
```
Create postgreSQL DB and test the installation
```sh
$ /usr/local/pgsql/bin/createdb test
$ /usr/local/pgsql/bin/psql test
psql (9.6rc1)
Type "help" for help.

test=#
```
Type ```\q``` and then press ```ENTER``` to quit ```psql```
```sh
test=# \q
$ exit
```
Copy server startup script under services
```sh
# cp contrib/start-scripts/linux /etc/rc.d/init.d/postgresql
```

Then make the startup script executable
```sh
# chmod 775 /etc/rc.d/init.d/postgresql
```

Add the script to the Server's startup routine (init) 
```sh
# chkconfig --add postgresql
```

On some systems with shared libraries you need to tell the system how to find the newly installed shared libraries. 
```sh
# LD_LIBRARY_PATH=/usr/local/pgsql/lib
# export LD_LIBRARY_PATH
# /sbin/ldconfig /usr/local/pgsql/lib
```

You should add /usr/local/pgsql/bin into your PATH (Environment Variables).
```sh
# PATH=/usr/local/pgsql/bin:$PATH
# export PATH
# which psql
/usr/local/pgsql/bin/psql
```

# Workspace Instructions
---
### Installing Gradle
##### Prerequisites
* Gradle requires a Java JDK or JRE to be installed, version 7 or higher (to check, use java -version). Gradle ships with its own Groovy library, therefore Groovy does not need to be installed. Any existing Groovy installation is ignored by Gradle.
* Gradle uses whatever JDK it finds in your path. Alternatively, you can set the JAVA_HOME environment variable to point to the installation directory of the desired JDK.
##### Download
You can download one of the Gradle distributions from the [Gradle web site](https://gradle.org/gradle-download/).
#####  Unpack and Set System variables
**Windows**   
* Unzip the Gradle download to the folder to which you would like to install Gradle, eg. ```C:\Program Files``` . The subdirectory gradle-x.x will be created from the archive, where x.x is the version.   
* Add location of your Gradle "bin" folder to your path. Open the system properties (WinKey + Pause), select the "Advanced" tab, and the "Environment Variables" button, then add "C:\Program Files\gradle-x.x\bin" (or wherever you unzipped Gradle) to the end of your "Path" variable under System Properties. Be sure to omit any quotation marks around the path even if it contains spaces. Also make sure you separated from previous PATH entries with a semicolon ";".   
* In the same dialog, make sure that JAVA_HOME exists in your user variables or in the system variables and it is set to the location of your JDK, e.g. C:\Program Files\Java\jdk1.7.0_06 and that %JAVA_HOME%\bin is in your Path environment variable.   
* Open a new command prompt (type cmd in Start menu) and run ```gradle –version``` to verify that it is correctly installed. Should print out: 
```sh
Gradle 3.1
Build time:   2016-09-19 10:53:53 UTC
Revision:     13f38ba699afd86d7cdc4ed8fd7dd3960c0b1f97
Groovy:       2.4.7
Ant:          Apache Ant(TM) version 1.9.6 compiled on June 29 2015
JVM:          1.8.0_101 (Oracle Corporation 25.101-b13)
OS:           Windows 10 10.0 amd64
```

**Mac/Linux**   
* Extract the distribution archive, i.e. gradle-x.x-bin.tar.gz to the directory you wish to install Gradle. These instructions assume you chose /usr/local/gradle. The subdirectory gradle-x.x will be created from the archive.   
* In a command terminal, add Gradle to your PATH variable: ```export PATH=/usr/local/gradle/gradle-x.x/bin:$PATH```
Make sure that JAVA_HOME is set to the location of your JDK, e.g. ```export JAVA_HOME=/usr/java/jdk1.7.0_06``` and that $JAVA_HOME/bin is in your PATH environment variable.   
Run ```gradle –version``` to verify that it is correctly installed.   

### How to import in eclipse
1. Open the terminal in the project folder and run the command ``` gradle eclipse ``` 
2. start eclipse
3. File -> Import -> Existing Projects into Workspace
4. select participact-server
5. right click on the project and choose Configure -> Convert to Gradle Project   
`To use with GCM (google cloud messaging): add API key in it.unibo.participact.domain.PANotificationConst.java
change package_name according to the new app id`

### Apache Tomcat Eclipse Integration `(Optional)`
###### Apache Tomcat Eclipse Integration:   
1. Select the "Install New Software..." menu item from the Eclipse "Help" menu.   
2. Click the "Work with:" drop down menu, and select the WTP Project site from the list.
3. Next, select the latest version of the WTP SDK from the list of projects.    
4. Click "Next". *You can review them if you want, but it's not necessary* .. and click the "Finish" button, Eclipse will begin downloading the components and installing.
###### Configuring a Tomcat Server In Eclipse:   
1. Click in the "Server" tab, located in the lower half of Eclipse's development screen by default.  
2. Secondary click inside the tab to open the "New Server".   
3. Select the server type `Tomcat 7 (Recommended)` and choose a local name for your new server, as well as a Server name, and click Finish.   
4. If you did everything correctly, a new Server will appear in the Server list.  Double-clicking on the server's name will call up a window with information about the server.   
4.1 In the server location option: Choose `Use Tomcat installation (takes control of Tomcat Installation)`   
###### Running an Application On Your Tomcat Server:
1. Starting the Server, right-clicking the application in the Project Explorer pane, and choosing Run On Server from the "Run As..."
2. Select the Server Name created in the previous step.   
**If showing error (in Eclipse):**    
**Error**: The superclass "javax.servlet.jsp.tagext.SimpleTagSupport" was not found on the Java Build Path.   
**Solution**: https://www.mkyong.com/eclipse/eclipse-simpletagsupport-was-not-found-on-the-java-build-path/

# Versioning
---
For transparency and insight into the release cycle, releases will be numbered with the follow format:   

`<major>.<minor>.<patch>`   

And constructed with the following guidelines:

* Breaking backwards compatibility bumps the major
* New additions without breaking backwards compatibility bumps the minor
* Bug fixes and misc changes bump the patch

For more information on semantic versioning, please visit http://semver.org/.
# License
---
Copyright (c) Centro de Ciências da Administração e Socioeconômicas - **ESAG** / UNIVERSIDADE DO ESTADO DE SANTA CATARINA - **UDESC**  
*Av. Madre Benvenuta, 2037 - Itacorubí - Florianópolis - SC CEP: 88.035-001 - Telefone (48) 3664-8200*