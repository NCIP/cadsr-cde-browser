
===============================================================================
			CDBrowser @cdebrowser.version@
===============================================================================

Contains the complete application source for CDEBrowser @cdebrowser.version@

<JBOSS_HOME> refers to Jboss home directory.
<HOME> refers to directory to which "CDBrowser_@cdebrowser.version.suffix@.zip" is unzipped.
<INSTALL_HOME> refers to directory  <HOME>/cdebrowser_@cdebrowser.version.suffix@

Content of  <INSTALL_HOME> directory
=============================
lib\java		- Third party libraries.
config		 	-  Property and xml files that should be in the application 
		            classpath.
		  
config\connections  	-  oracle-ds.xml and cle-providers.xml  which defines the 
			     database connections for the application.
config\ejb		-  EJB discriptor files.
config\META-INF         -  application.xml.
config\properties       -  Property files used within the application.
config\services         -  MVC Framework for J2EE service discriptors.
config\WEB-INF     	-  web module discriptors and struts config files.
src\java		-  Java source code.
\ui	            	-  The jsps,html,javascript and stylesheets.


Building the Application  
=========================

Pre-requisites
---------------
1.  JBOSS 4.0.2 installed. Available from http://www.jboss.org
2.  JDK 1.4.5_05  installed
3.  Ant 1.6.2 or above installed. Available from http://ant.apache.org/
4.  caDSR @cdebrowser.version@ repository installed. Please refer to caDSR installation document

An ear file need to be build before the application can be installed, follow
the steps below to build an ear file. An ear file is not provided with the
download because the database connection information and environment properties 
could be different for each deployment.

  1. Update  cle-providers.xml  located in directory  <INSTALL_HOME>\config\connections
            - Update the hostname,port and SID that could be used to connect to caDSR repository.
            - Please update the values for user and password with the username/password to be used to 
               connect to caDSR repository.
               caDSR installation scripts creates a user called "sbrext" that can be used for this purpose	

 2. Update  cdebrowser.properties located in directory  <INSTALL_HOME>\config\properties\jboss
            - Update value for property XML_DOWNLOAD_DIR to point to directory on the server that could be 
              used by the application to temporarily write files. Application should have write 
              privileges to this directory.
              
              For windows (need extra "\")
              eg . XML_DOWNLOAD_DIR=c:\\temp\\
              For unix
              eg . XML_DOWNLOAD_DIR=/usr/local/temp    
              
            - Pointing to new a EVS server
               If there is a need for the application to point to EVS server other than the one hosted by 
               NCI please update. 
                 -  EVS_URL_SOURCES property to define type of codes available as coma separated string.
                          eg.    EVS_URL_SOURCES  = NCI_META_CUI,LOINC_CODE,GO_CODE,NCI_CONCEPT_CODE

                 - For each type in EVS_URL_SOURCES string there should be a matching name value pair 
                   defining the url to query for that type of concept code
                     eg.     NCI_META_CUI=http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=
			     LOINC_CODE=http://nciterms.nci.nih.gov/NCIBrowser/Connect.do?dictionary=LOINC&&code=
			     GO_CODE=http://nciterms.nci.nih.gov/NCIBrowser/Connect.do?dictionary=GO&&code=
			     NCI_CONCEPT_CODE=http://nciterms.nci.nih.gov/NCIBrowser/Connect.do?dictionary=NCI_Thesaurus&&code=
                      Note: In caDSR repository different EVS Sources are captured as  part of Concept's EVS Source property

  3. Run the build script (build.xml) from  directory src/java with target "dist"
	eg. ant -f  build.xml  dist
	
  4. cdebrowser.ear will be created under directory <INSTALL_HOME>/dist

Deploying Application
=====================
Pre-requisites
---------------
1.  JBOSS 4.0.1 installed. Available from http://www.jboss.org


1. Shutdown Jboss instance.

2. Copy  file  <INSTALL_HOME>\config\connections\oracle-ds.xml to directory <JBOSS_HOME> server\default\deploy
    If file already exists in <JBOSS_HOME> server\default\deploy, then update the file with 
    datasource defined in <INSTALL_HOME>\config\connections\oracle-ds.xml.

    Note: Please update the following tags in oracle-ds.xml to match your database(caDSR repository) environment.		
                <connection-url>jdbc:oracle:thin:@hostname:port:SID</connection-url>
		<user-name>username</user-name>
		<password>password</password>
		
                caDSR installation scripts creates a user called "sbrext" that can be used for this purpose

3. Edit login-config.xml in <JBOSS_HOME>\server\default\conf to add the 
   xml snipet below between <policy> tags

	<application-policy name="FormbuilderDomain">
		<authentication>
			<login-module code="gov.nih.nci.ncicb.cadsr.security.jboss.DBLoginModule" flag="required">
	                      <module-option name="ServiceLocatorClassName">gov.nih.nci.ncicb.cadsr.servicelocator.ejb.ServiceLocatorImpl</module-option>
			</login-module>
		</authentication>
	</application-policy>    

4. Edit log4j.xml in <JBOSS_HOME>\server\default\conf to added the xml snippet below to end of the file before
  </log4j:configuration> closing tag


        <!-- ======================= -->
        <!-- Setup the CDE Browser Appender -->
        <!-- ======================= -->
        <appender name="CDEBROWSER_FILE" class="org.jboss.logging.appender.RollingFileAppender">
                <errorHandler class="org.jboss.logging.util.OnlyOnceErrorHandler"/>
                <param name="File" value="${jboss.server.home.dir}/log/cdebrowser.log"/>
                <param name="Append" value="false"/>
                <param name="MaxFileSize" value="1MB"/>
                <param name="MaxBackupIndex" value="3"/>
                <layout class="org.apache.log4j.PatternLayout">
                        <param name="ConversionPattern" value="%d %-5p [%c] %m%n"/>
                </layout>
        </appender>

        <!-- ======================= -->
        <!-- Setup the CDE Browser category -->
        <!-- ======================= -->
        <category name="gov.nih.nci.ncicb.cadsr" additivity="false">
                <priority value="DEBUG"/>
                <appender-ref ref="CDEBROWSER_FILE"/>
        </category>

5. Copy cdebrowser.ear from <INSTALL_HOME>/dist to 
	<JBOSS_HOME>\server\default\deploy
	
6. Startup the Jboss instance
    
7. Access the application using url http://<HOST>:<POST>/CDEBrowser/
    eg.
    	http://localhost:8080/CDEBrowser/



Descriptions some of the cdebrowser specific property files under directory  <INSTALL_HOME>/config/properties
---------------------------------------------------------------------------------------------------------------------------------------------------------

deployment.properties
  MVC Framework for j2ee persistence layer descriptor file. Maps resources to 
  connections defined in cle_providers.xml

cdebrowser.properties
   Contains properties that specify the names of the data sources for sbr and sbrext caDSR repository schemas, 
  full path of   the directory where temporary files are created during XML and EXCEL downloads, 
  XML download pagination flag and number of   records per file when pagination flag is set to 'yes'. 

cdebrowser_search_tabs.properties
  Can be used to configure the number of tabs and their labels on the data element search page.

cdebrowser_details_tabs.properties
  Can be used to configure the number of tabs and their labels on the data element details page.

cdebrowser_lov_tabs.properties
  Can be used to configure the number of tabs and their labels on all list of values (LOV) pages.

cdebrowser_error_tabs.properties
  Can be used to configure the number of tabs and their labels on the error page.    


The source code has been compiled and tested using  JDK 1.5.0_05 , struts 1.1 and JBOSS 4.0.2
CDEBrowser @cdebrowser.version@ works with caDSR repository @cdebrowser.version@ and is not 
compatible with earlier versions of caDSR repository

