
===============================================================================
			CDBrowser 2.0
===============================================================================

Contains the complete application source and the ear file.

The Content of the zip file by directory
	
lib            - Third party libraries.
config         -  Property and xml files that should be in the application 
		  classpath.
		  
config\WEB-INF - web.xml 
src\java       - Java source code.
src\ui	       - The jsps,html,javascript and stylesheets.

Configuration files description
----------------------------------
cle_providers.xml
  MVC Framework for j2ee persistance layer discriptor file.
  Defines database connection parameters for bc4j application module connection for
  sbr caDSR repository schema. 

deployment.properties
  MVC Framework for j2ee persistance layer discriptor file. Maps resources to 
  connections defined in cle_providers.xml

data-sources.xml
  defines the data sources for sbr and sbrext caDSR repository schemas.

cdebrowser.properties
  contains properties that specify the names of the data sources for sbr and sbrext caDSR repository schemas, full path of   the directory where temporary files are created during XML and EXCEL downloads, XML download pagination flag and number of   records per file when pagination flag is set to 'yes'. 

cdebrowser_search_tabs.properties
  can be used to configure the number of tabs and their labels on the data element search page.

cdebrowser_details_tabs.properties
  can be used to configure the number of tabs and their labels on the data element details page.

cdebrowser_lov_tabs.properties
  can be used to configure the number of tabs and their labels on all list of values (LOV) pages.

cdebrowser_error_tabs.properties
  can be used to configure the number of tabs and their labels on the error page.    