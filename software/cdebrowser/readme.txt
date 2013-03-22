*** Build Instruction ***

1. Modify local.build.properties accordingly
2. Execute ant command -

ant build-all

5. Ignore all warnings for now

In deployment-artifacts/jboss/CDEBrowser.war (42.6 MB)

6. Deploy locally if needed

ant deploy

7. Copy *-ds.xml under cdebrowser/deployment-artifacts/jboss to your jboss server/default/deploy directory

8. Visit http://localhost:8080/CDEBrowser

*** Good Sample JBoss 4.0.5 Console Output ***

/Applications/jboss-4.0.5.GA/bin/run.sh: line 79: ulimit: open files: cannot modify limit: Invalid argument
run.sh: Could not set maximum file descriptor limit: unlimited
=========================================================================

  JBoss Bootstrap Environment

  JBOSS_HOME: /Applications/jboss-4.0.5.GA

  JAVA: java

  JAVA_OPTS: -Dprogram.name=run.sh -Xms128m -Xmx512m -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000

  CLASSPATH: /Applications/jboss-4.0.5.GA/bin/run.jar:/lib/tools.jar

=========================================================================

13:29:16,836 INFO  [Server] Starting JBoss (MX MicroKernel)...
13:29:16,838 INFO  [Server] Release ID: JBoss [Zion] 4.0.5.GA (build: CVSTag=Branch_4_0 date=200610162339)
13:29:16,839 INFO  [Server] Home Dir: /Applications/jboss-4.0.5.GA
13:29:16,839 INFO  [Server] Home URL: file:/Applications/jboss-4.0.5.GA/
13:29:16,840 INFO  [Server] Patch URL: null
13:29:16,840 INFO  [Server] Server Name: default
13:29:16,840 INFO  [Server] Server Home Dir: /Applications/jboss-4.0.5.GA/server/default
13:29:16,841 INFO  [Server] Server Home URL: file:/Applications/jboss-4.0.5.GA/server/default/
13:29:16,841 INFO  [Server] Server Log Dir: /Applications/jboss-4.0.5.GA/server/default/log
13:29:16,841 INFO  [Server] Server Temp Dir: /Applications/jboss-4.0.5.GA/server/default/tmp
13:29:16,841 INFO  [Server] Root Deployment Filename: jboss-service.xml
13:29:17,246 INFO  [ServerInfo] Java version: 1.6.0_41,Apple Inc.
13:29:17,246 INFO  [ServerInfo] Java VM: Java HotSpot(TM) 64-Bit Server VM 20.14-b01-445,Apple Inc.
13:29:17,246 INFO  [ServerInfo] OS-System: Mac OS X 10.7.5,x86_64
13:29:17,682 INFO  [Server] Core system initialized
13:29:19,793 INFO  [WebService] Using RMI server codebase: http://someones-MacBook-Pro.local:8083/
13:29:19,815 INFO  [Log4jService$URLWatchTimerTask] Configuring from URL: resource:log4j.xml
13:29:23,084 INFO  [ServiceEndpointManager] WebServices: jbossws-1.0.3.SP1 (date=200609291417)
13:29:23,926 INFO  [Embedded] Catalina naming disabled
13:29:23,949 INFO  [ClusterRuleSetFactory] Unable to find a cluster rule set in the classpath. Will load the default rule set.
13:29:23,950 INFO  [ClusterRuleSetFactory] Unable to find a cluster rule set in the classpath. Will load the default rule set.
13:29:24,181 INFO  [Http11BaseProtocol] Initializing Coyote HTTP/1.1 on http-0.0.0.0-8080
13:29:24,181 INFO  [Catalina] Initialization processed in 231 ms
13:29:24,181 INFO  [StandardService] Starting service jboss.web
13:29:24,183 INFO  [StandardEngine] Starting Servlet Engine: Apache Tomcat/5.5.20
13:29:24,213 INFO  [StandardHost] XML validation disabled
13:29:24,231 INFO  [Catalina] Server startup in 50 ms
13:29:24,383 INFO  [TomcatDeployer] deploy, ctxPath=/invoker, warUrl=.../deploy/http-invoker.sar/invoker.war/
13:29:24,875 INFO  [WebappLoader] Dual registration of jndi stream handler: factory already defined
13:29:25,187 INFO  [TomcatDeployer] deploy, ctxPath=/, warUrl=.../deploy/jbossweb-tomcat55.sar/ROOT.war/
13:29:25,291 INFO  [TomcatDeployer] deploy, ctxPath=/jbossws, warUrl=.../tmp/deploy/tmp2694367457482632065jbossws-context-exp.war/
13:29:25,407 INFO  [TomcatDeployer] deploy, ctxPath=/jbossmq-httpil, warUrl=.../deploy/jms/jbossmq-httpil.sar/jbossmq-httpil.war/
13:29:26,134 INFO  [TomcatDeployer] deploy, ctxPath=/web-console, warUrl=.../deploy/management/console-mgr.sar/web-console.war/
13:29:26,622 INFO  [MailService] Mail Service bound to java:/Mail
13:29:26,741 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/jboss-ha-local-jdbc.rar
13:29:26,767 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/jboss-ha-xa-jdbc.rar
13:29:26,785 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/jboss-local-jdbc.rar
13:29:26,801 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/jboss-xa-jdbc.rar
13:29:26,823 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/jms/jms-ra.rar
13:29:26,841 INFO  [RARDeployment] Required license terms exist, view META-INF/ra.xml in .../deploy/mail-ra.rar
13:29:27,068 INFO  [WrapperDataSourceService] Bound ConnectionManager 'jboss.jca:service=DataSourceBinding,name=jdbc/CDEBrowserDS' to JNDI name 'java:jdbc/CDEBrowserDS'
13:29:27,201 INFO  [ConnectionFactoryBindingService] Bound ConnectionManager 'jboss.jca:service=ConnectionFactoryBinding,name=JmsXA' to JNDI name 'java:JmsXA'
13:29:32,832 INFO  [TomcatDeployer] deploy, ctxPath=/CDEBrowser, warUrl=.../tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/
13:29:33,455 INFO  [FacesConfigurator] Reading standard config org/apache/myfaces/resource/standard-faces-config.xml
13:29:33,512 INFO  [FacesConfigurator] Reading config jar:file:/Applications/jboss-4.0.5.GA/server/default/./tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/WEB-INF/lib/ajaxanywhere-1.1.0.6.jar!/META-INF/faces-config.xml
13:29:33,517 INFO  [FacesConfigurator] Reading config jar:file:/Applications/jboss-4.0.5.GA/server/default/./tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/WEB-INF/lib/jsfCompAA-0.1.jar!/META-INF/faces-config.xml
13:29:33,523 INFO  [FacesConfigurator] Reading config jar:file:/Applications/jboss-4.0.5.GA/server/default/./tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/WEB-INF/lib/tomahawk-1.1.3.jar!/META-INF/faces-config.xml
13:29:33,541 INFO  [FacesConfigurator] Reading config /WEB-INF/cdebrowser-faces-config.xml
13:29:33,613 WARN  [LocaleUtils] Locale name in faces-config.xml null or empty, setting locale to default locale : en_US
13:29:33,933 INFO  [HtmlRenderKitImpl] Overwriting renderer with family = net.sf.jsfcomp.AjaxAnywhere rendererType = org.apache.myfaces.HtmlTree2 renderer class = org.apache.myfaces.custom.tree2.HtmlTreeRenderer
13:29:33,954 INFO  [StartupServletContextListener] ServletContext '/Applications/jboss-4.0.5.GA/server/default/./tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/' initialized.
13:29:33,974 INFO  [[/CDEBrowser]] Initializing Spring root WebApplicationContext
13:29:33,974 INFO  [ContextLoader] Root WebApplicationContext: initialization started
13:29:34,000 INFO  [XmlWebApplicationContext] Refreshing org.springframework.web.context.support.XmlWebApplicationContext@71b98cbb: display name [Root WebApplicationContext]; startup date [Fri Mar 22 13:29:34 EDT 2013]; root of context hierarchy
13:29:34,085 INFO  [XmlBeanDefinitionReader] Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/applicationContext-common.xml]
13:29:34,238 INFO  [XmlBeanDefinitionReader] Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/applicationContext-domainDao.xml]
13:29:34,246 INFO  [XmlBeanDefinitionReader] Loading XML bean definitions from ServletContext resource [/WEB-INF/spring/applicationContext-ocbrowser.xml]
13:29:34,251 INFO  [XmlWebApplicationContext] Bean factory for application context [org.springframework.web.context.support.XmlWebApplicationContext@71b98cbb]: org.springframework.beans.factory.support.DefaultListableBeanFactory@5527f4f9
13:29:34,313 INFO  [DefaultListableBeanFactory] Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@5527f4f9: defining beans [treeCache,treeService,CDEBrowserService,appServiceLocator,daoFactory,serviceLocator,treeData,treeNode,dataSource,sessionFactory,OCBrowserService]; root of factory hierarchy
13:29:34,513 INFO  [Environment] Hibernate 3.2.0.ga
13:29:34,519 INFO  [Environment] hibernate.properties not found
13:29:34,521 INFO  [Environment] Bytecode provider name : cglib
13:29:34,527 INFO  [Environment] using JDK 1.4 java.sql.Timestamp handling
13:29:34,614 INFO  [LocalSessionFactoryBean] Building new Hibernate SessionFactory
13:29:34,621 INFO  [ConnectionProviderFactory] Initializing connection provider: org.springframework.orm.hibernate3.LocalDataSourceConnectionProvider
13:30:39,085 INFO  [SettingsFactory] RDBMS: Oracle, version: Oracle Database 10g Enterprise Edition Release 10.2.0.5.0 - 64bit Production
With the Partitioning, Data Mining and Real Application Testing options
13:30:39,085 INFO  [SettingsFactory] JDBC driver: Oracle JDBC driver, version: 10.1.0.4.0
13:30:39,105 INFO  [Dialect] Using dialect: org.hibernate.dialect.OracleDialect
13:30:39,141 INFO  [TransactionFactoryFactory] Using default transaction strategy (direct JDBC transactions)
13:30:39,143 INFO  [TransactionManagerLookupFactory] No TransactionManagerLookup configured (in JTA environment, use of read-write or transactional second-level cache is not recommended)
13:30:39,143 INFO  [SettingsFactory] Automatic flush during beforeCompletion(): disabled
13:30:39,144 INFO  [SettingsFactory] Automatic session close at end of transaction: disabled
13:30:39,144 INFO  [SettingsFactory] JDBC batch size: 15
13:30:39,144 INFO  [SettingsFactory] JDBC batch updates for versioned data: disabled
13:30:39,144 INFO  [SettingsFactory] Scrollable result sets: enabled
13:30:39,144 INFO  [SettingsFactory] JDBC3 getGeneratedKeys(): disabled
13:30:39,145 INFO  [SettingsFactory] Connection release mode: on_close
13:30:39,145 INFO  [SettingsFactory] Default batch fetch size: 1
13:30:39,145 INFO  [SettingsFactory] Generate SQL with comments: disabled
13:30:39,145 INFO  [SettingsFactory] Order SQL updates by primary key: disabled
13:30:39,145 INFO  [SettingsFactory] Query translator: org.hibernate.hql.ast.ASTQueryTranslatorFactory
13:30:39,148 INFO  [ASTQueryTranslatorFactory] Using ASTQueryTranslatorFactory
13:30:39,148 INFO  [SettingsFactory] Query language substitutions: {}
13:30:39,148 INFO  [SettingsFactory] JPA-QL strict compliance: disabled
13:30:39,148 INFO  [SettingsFactory] Second-level cache: enabled
13:30:39,149 INFO  [SettingsFactory] Query cache: disabled
13:30:39,149 INFO  [SettingsFactory] Cache provider: org.hibernate.cache.EhCacheProvider
13:30:39,203 INFO  [SettingsFactory] Optimize cache for minimal puts: disabled
13:30:39,203 INFO  [SettingsFactory] Structured second-level cache entries: disabled
13:30:39,209 INFO  [SettingsFactory] Echoing all SQL to stdout
13:30:39,209 INFO  [SettingsFactory] Statistics: disabled
13:30:39,209 INFO  [SettingsFactory] Deleted entity synthetic identifier rollback: disabled
13:30:39,210 INFO  [SettingsFactory] Default entity-mode: pojo
13:30:39,248 INFO  [SessionFactoryImpl] building session factory
13:30:39,383 INFO  [SessionFactoryObjectFactory] Not binding factory to JNDI, no JNDI name configured
13:30:39,417 INFO  [ContextLoader] Root WebApplicationContext: initialization completed in 65443 ms
13:30:39,486 INFO  [ActionServlet] Loading chain catalog from jar:file:/Applications/jboss-4.0.5.GA/server/default/./tmp/deploy/tmp7038709301365009740CDEBrowser-exp.war/WEB-INF/lib/struts-core-1.3.5.jar!/org/apache/struts/chain/chain-config.xml
13:30:39,965 INFO  [STDOUT] org.apache.struts.config.impl.ModuleConfigImpl@7366fdde init org.apache.struts.action.ActionServlet@6ea53502
13:30:39,981 INFO  [ValidatorPlugIn] Loading validation rules file from '/WEB-INF/cdebrowser/validator-rules.xml'
13:30:39,992 INFO  [ValidatorPlugIn] Loading validation rules file from '/WEB-INF/cdebrowser/validation.xml'
13:30:40,184 ERROR [URLDeploymentScanner] Incomplete Deployment listing:

--- MBeans waiting for other MBeans ---
ObjectName: jboss.ejb:service=EJBTimerService,persistencePolicy=database
  State: CONFIGURED
  I Depend On:
    jboss.jca:service=DataSourceBinding,name=DefaultDS

ObjectName: jboss.mq:service=InvocationLayer,type=HTTP
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=Invoker
    jboss.web:service=WebServer

ObjectName: jboss:service=KeyGeneratorFactory,type=HiLo
  State: CONFIGURED
  I Depend On:
    jboss:service=TransactionManager
    jboss.jca:service=DataSourceBinding,name=DefaultDS

ObjectName: jboss.mq:service=StateManager
  State: CONFIGURED
  I Depend On:
    jboss.jca:service=DataSourceBinding,name=DefaultDS
  Depends On Me:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq:service=DestinationManager
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=MessageCache
    jboss.mq:service=PersistenceManager
    jboss.mq:service=StateManager
    jboss.mq:service=ThreadPool
    jboss:service=Naming
  Depends On Me:
    jboss.mq.destination:service=Topic,name=testTopic
    jboss.mq.destination:service=Topic,name=securedTopic
    jboss.mq.destination:service=Topic,name=testDurableTopic
    jboss.mq.destination:service=Queue,name=testQueue
    jboss.mq.destination:service=Queue,name=A
    jboss.mq.destination:service=Queue,name=B
    jboss.mq.destination:service=Queue,name=C
    jboss.mq.destination:service=Queue,name=D
    jboss.mq.destination:service=Queue,name=ex
    jboss.mq:service=SecurityManager
    jboss.mq.destination:service=Queue,name=DLQ

ObjectName: jboss.mq:service=PersistenceManager
  State: CONFIGURED
  I Depend On:
    jboss.jca:service=DataSourceBinding,name=DefaultDS
  Depends On Me:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq.destination:service=Topic,name=testTopic
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
    jboss.mq:service=SecurityManager

ObjectName: jboss.mq.destination:service=Topic,name=securedTopic
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
    jboss.mq:service=SecurityManager

ObjectName: jboss.mq.destination:service=Topic,name=testDurableTopic
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
    jboss.mq:service=SecurityManager

ObjectName: jboss.mq.destination:service=Queue,name=testQueue
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
    jboss.mq:service=SecurityManager

ObjectName: jboss.mq.destination:service=Queue,name=A
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq.destination:service=Queue,name=B
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq.destination:service=Queue,name=C
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq.destination:service=Queue,name=D
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq.destination:service=Queue,name=ex
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager

ObjectName: jboss.mq:service=Invoker
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=TracingInterceptor
    jboss:service=Naming
  Depends On Me:
    jboss.mq:service=InvocationLayer,type=HTTP
    jboss.mq:service=InvocationLayer,type=JVM
    jboss.mq:service=InvocationLayer,type=UIL2

ObjectName: jboss.mq:service=TracingInterceptor
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=SecurityManager
  Depends On Me:
    jboss.mq:service=Invoker

ObjectName: jboss.mq:service=SecurityManager
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
  Depends On Me:
    jboss.mq.destination:service=Topic,name=testTopic
    jboss.mq.destination:service=Topic,name=securedTopic
    jboss.mq.destination:service=Topic,name=testDurableTopic
    jboss.mq.destination:service=Queue,name=testQueue
    jboss.mq:service=TracingInterceptor
    jboss.mq.destination:service=Queue,name=DLQ

ObjectName: jboss.mq.destination:service=Queue,name=DLQ
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=DestinationManager
    jboss.mq:service=SecurityManager

ObjectName: jboss.mq:service=InvocationLayer,type=JVM
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=Invoker

ObjectName: jboss.mq:service=InvocationLayer,type=UIL2
  State: CONFIGURED
  I Depend On:
    jboss.mq:service=Invoker

--- MBEANS THAT ARE THE ROOT CAUSE OF THE PROBLEM ---
ObjectName: jboss.jca:service=DataSourceBinding,name=DefaultDS
  State: NOTYETINSTALLED
  Depends On Me:
    jboss.ejb:service=EJBTimerService,persistencePolicy=database
    jboss:service=KeyGeneratorFactory,type=HiLo
    jboss.mq:service=StateManager
    jboss.mq:service=PersistenceManager


13:30:40,284 INFO  [Http11BaseProtocol] Starting Coyote HTTP/1.1 on http-0.0.0.0-8080
13:30:40,392 INFO  [ChannelSocket] JK: ajp13 listening on /0.0.0.0:8009
13:30:40,403 INFO  [JkMain] Jk running ID=0 time=0/34  config=null
13:30:40,421 INFO  [Server] JBoss (MX MicroKernel) [4.0.5.GA (build: CVSTag=Branch_4_0 date=200610162339)] Started in 1m:23s:578ms
