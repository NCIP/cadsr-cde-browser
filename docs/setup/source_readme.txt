
===============================================================================
			CDBrowser 2.1
===============================================================================

Contains the complete application source for CDEBrowser 2.1.

The Content of the zip file by directory
	
lib            - Third party libraries.
config         -  Property and xml files that should be in the application 
		  classpath.
		  
config\connections  -  Datasources.xml and cle-providers.xml  which defines the 
			database connections for the application.
config\ejb          -  EJB discriptor files.
config\META-INF     -  Principal.xml and application.xml.
config\properties   -  Property files used within the application.
config\services     -  MVC Framework for J2EE service discriptors.
config\WEB-INF      -  web module discriptors and struts config files.
src                 -  Java source code.
\ui	            -  The jsps,html,javascript and stylesheets.

Descriptions some of the cdebrowser specific property files.
----------------------------------

deployment.properties
  MVC Framework for j2ee persistence layer descriptor file. Maps resources to 
  connections defined in cle_providers.xml

cdebrowser.properties
  Contains properties that specify the names of the data sources for sbr and sbrext caDSR repository schemas, full path of   the directory where temporary files are created during XML and EXCEL downloads, XML download pagination flag and number of   records per file when pagination flag is set to 'yes'. 

cdebrowser_search_tabs.properties
  Can be used to configure the number of tabs and their labels on the data element search page.

cdebrowser_details_tabs.properties
  Can be used to configure the number of tabs and their labels on the data element details page.

cdebrowser_lov_tabs.properties
  Can be used to configure the number of tabs and their labels on all list of values (LOV) pages.

cdebrowser_error_tabs.properties
  Can be used to configure the number of tabs and their labels on the error page.    
