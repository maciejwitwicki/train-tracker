package io.mwi.traintracker.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.ClientRepresentation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakPreConfiguration implements ApplicationRunner {

    private final static String REALM_MANAGEMENT_CLIENT_ID_SUFFIX = "-realm";

    private final KeycloakProperties keycloakProperties;

    @Override
    public void run(ApplicationArguments args) {
        var keycloak = getKeycloakInstance();
        var realmApi = keycloak.realms().realm(keycloakProperties.realm());
        var clientsApi = realmApi.clients();
        if (noSuperUserCreated(clientsApi)) {
            createSuperUser(clientsApi);
            var usersApi = realmApi.users();
            setupSuperUserRoles(clientsApi, usersApi);
        }
    }

    private boolean noSuperUserCreated(ClientsResource clientsApi) {
        return clientsApi.findByClientId(keycloakProperties.superUser().id()).isEmpty();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakProperties.url(),
                keycloakProperties.realm(),
                keycloakProperties.admin().user(),
                keycloakProperties.admin().password(),
                keycloakProperties.admin().id()
        );
    }


    private void createSuperUser(ClientsResource clientsApi) {
        var client = new ClientRepresentation();
        client.setClientId(keycloakProperties.superUser().id());
        client.setSecret(keycloakProperties.superUser().secret());
        client.setEnabled(true);
        client.setProtocol("openid-connect");
        client.setClientAuthenticatorType("client-secret");
        client.setServiceAccountsEnabled(true);
        client.setDirectAccessGrantsEnabled(false);
        client.setAuthorizationServicesEnabled(true);
        client.setStandardFlowEnabled(false);
        client.setPublicClient(false);

        try (Response r = clientsApi.create(client)) {
            if (r.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                log.error("Client creation failed {}", r.getStatusInfo().getReasonPhrase());
            }
        }
    }

    private void setupSuperUserRoles(ClientsResource clientsApi, UsersResource usersApi) {
        var clientIdentifier = clientsApi.findByClientId(keycloakProperties.superUser().id()).get(0).getId();
        var client = clientsApi.get(clientIdentifier);
        var serviceUserId = client.getServiceAccountUser().getId();

        // copy realm-management roles to service user
        var userResource = usersApi.get(serviceUserId);
        var realmManagementClientId = keycloakProperties.realm() + REALM_MANAGEMENT_CLIENT_ID_SUFFIX;
        var realmManagementId = clientsApi.findByClientId(realmManagementClientId).get(0).getId();
        var availableRoles = userResource.roles().clientLevel(realmManagementId).listAvailable();
        userResource.roles().clientLevel(realmManagementId).add(availableRoles);
        log.info("Provided realm management roles to service user {} of client {}", serviceUserId, keycloakProperties.superUser().id());
    }
}
