FROM adrienpessu/docker-maven-postgres:latest

# Install Maven
RUN curl -o /tmp/apache-maven-3.6.3.tar.gz http://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz \
  && rm /opt/maven \
  && tar xzf /tmp/apache-maven-3.6.3.tar.gz -C /opt/ \
  && ln -s /opt/apache-maven-3.6.3 /opt/maven \
  && rm -f /tmp/apache-maven-3.6.3.tar.gz

COPY db/schema.sql /docker-entrypoint-initdb.d/1-schema.sql
COPY db/data.sql /docker-entrypoint-initdb.d/2-data.sql
COPY start.sh /app/

COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app/
RUN mvn package -DskipTests && cp target/demo-0.0.1-SNAPSHOT.jar /app/app.jar && mvn clean

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD password

RUN ["chmod", "+x", "/app/start.sh"]
EXPOSE 8080

CMD ["/app/start.sh"]
