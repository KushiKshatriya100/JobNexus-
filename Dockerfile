#FROM tomcat:10.1.47-jdk17
#
#RUN rm -rf /usr/local/tomcat/webapps/ROOT
#
#COPY target/JobPortal.war /usr/local/tomcat/webapps/ROOT.war
#
#EXPOSE 8080
#
#CMD ["catalina.sh", "run"]


# ✅ Jakarta Servlet compatible Tomcat
FROM tomcat:10.1.47-jdk17

# ✅ Clean default ROOT webapp
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# ✅ Copy your WAR file as ROOT (so app runs directly at domain root)
COPY target/JobPortal.war /usr/local/tomcat/webapps/ROOT.war

# ✅ Default env vars (local fallback; Render will override these automatically)
ENV DB_URL="jdbc:mysql://localhost:3306/job_portal" \
    DB_USER="root" \
    DB_PASS="root"

# ✅ Expose Tomcat's default port
EXPOSE 8080

# ✅ Start Tomcat server
CMD ["catalina.sh", "run"]
