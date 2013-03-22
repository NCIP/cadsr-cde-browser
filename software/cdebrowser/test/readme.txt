*** Known Issues ***
If you encounter cert issue e.g.

13:32:28,193 ERROR [STDERR] org.springframework.remoting.RemoteAccessException: Cannot access HTTP invoker remote service at [https://objcart-dev.nci.nih.gov/objcart10//http/applicationService]; nested exception is javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
13:32:28,194 ERROR [STDERR] Caused by: 
13:32:28,194 ERROR [STDERR] javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
13:3

Please do following to see if the problem can be fixed:
 
1. Compile the InstallCert java file
2. Run the compiled code to import trust keystore, 
e.g.
java InstallCert [host][port]

For localhost, host=localhost and port 443 i.e.

java InstallCert localhost:443

http://cdebrowser-dev.nci.nih.gov:443/CDEBrowser/ or simply https://cdebrowser-dev.nci.nih.gov/CDEBrowser/

For DEV, host=cdebrowser-dev.nci.nih.gov

3. Enter 1 to add trusted keystore
4. Copy the generated “jssecacerts” file to your “$JAVA_HOME\jre\lib\security” folder.
5. Restart your application.
 
Reference:
http://www.mkyong.com/webservices/jax-ws/suncertpathbuilderexception-unable-to-find-valid-certification-path-to-requested-target/
