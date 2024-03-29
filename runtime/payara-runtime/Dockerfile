FROM alpine:3.18.3 AS happi-explorer-payara

ARG PAYARA_SERVER="payara-6.2023.9.zip"
ARG APPLICATION="payara-runtime-0.0.17-SNAPSHOT.war"

COPY "target/$PAYARA_SERVER" "/tmp"
COPY "target/$APPLICATION" "/tmp"

ENV PAYARA_USER="duke"
ENV PAYARA_HOME="/opt/payara6"
ENV PATH="$PATH:$PAYARA_HOME/bin"

# TODO: Optimize Payara internal configuration...
RUN \
 apk update &&\
 apk upgrade &&\
 apk add --no-cache openjdk17-jre-headless &&\
 addgroup --system "$PAYARA_USER" &&\
 adduser --system --disabled-password --no-create-home --home="$PAYARA_HOME" --ingroup="$PAYARA_USER" "$PAYARA_USER" &&\
 unzip "/tmp/$PAYARA_SERVER" -d "/opt" &&\
 mkdir --parent "$PAYARA_HOME/glassfish/domains/domain1/autodeploy" &&\
 mv "/tmp/$APPLICATION" "$PAYARA_HOME/glassfish/domains/domain1/autodeploy" &&\
 chown -R "$PAYARA_USER":"$PAYARA_USER" "$PAYARA_HOME" &&\
 chmod u+x "$PAYARA_HOME/bin/asadmin" &&\
 rm "/tmp/$PAYARA_SERVER"

WORKDIR "$PAYARA_HOME"
USER "$PAYARA_USER"
EXPOSE 8080 8181 4848
CMD ["asadmin", "start-domain", "--verbose"]
