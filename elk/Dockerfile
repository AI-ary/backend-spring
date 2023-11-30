FROM docker.elastic.co/logstash/logstash:8.8.1

ENV MYSQL_CONNECTOR_J_VERSION 8.0.23
ENV MYSQL_CONNECTOR_J_URL https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION.tar.gz

# 드라이버 다운로드 및 복사
RUN curl -L -O $MYSQL_CONNECTOR_J_URL
RUN tar -xvf mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION.tar.gz
RUN cp mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION/mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION.jar /usr/share/logstash/mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION.jar
RUN rm -rf mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION.tar.gz mysql-connector-java-$MYSQL_CONNECTOR_J_VERSION

CMD ["logstash", "-f", "/usr/share/logstash/pipeline/logstash.conf"]
