FROM keycloak/keycloak:25.0.0 AS builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Configure a database vendor
ENV KC_DB=mysql

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
#RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:security,DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM registry.access.redhat.com/ubi9 AS ubi-micro-build
RUN mkdir -p /mnt/rootfs
RUN dnf install --installroot /mnt/rootfs wget --releasever 9 --setopt install_weak_deps=false --nodocs -y; dnf --installroot /mnt/rootfs clean all

FROM keycloak/keycloak:22.0.5
COPY --from=ubi-micro-build /mnt/rootfs /
COPY --from=builder /opt/keycloak/ /opt/keycloak/
COPY dodgame-realm.json /opt/keycloak/data/import/

USER root

COPY wait-for-it.sh /usr/local/sbin/
RUN chmod +x /usr/local/sbin/wait-for-it.sh

COPY theme/build/libs/dodgame-keycloak-theme.jar /opt/keycloak/providers
RUN chmod +x /opt/keycloak/providers/dodgame-keycloak-theme.jar

# change these values to point to a running postgres instance
ENV KC_DB=mysql
ENV KC_DB_URL_HOST=db
ENV KC_DB_URL_PORT=3306
ENV KC_HOSTNAME=localhost
ENV KC_HEALTH_ENABLED=true
ENV QUARKUS_TRANSACTION_MANAGER_ENABLE_RECOVERY=true
ENV QUARKUS_DATASOURCE_JDBC_ACQUISITION_TIMEOUT=300000


ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]