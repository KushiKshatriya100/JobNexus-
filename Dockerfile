FROM tomcat:9.0-jdk17

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/JobPortal.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 7777

CMD ["catalina.sh", "run"]
