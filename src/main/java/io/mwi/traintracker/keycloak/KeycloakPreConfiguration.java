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
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
class KeycloakPreConfiguration implements ApplicationRunner {

    private final static String REALM_MANAGEMENT_CLIENT_ID = "realm-management";

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
        if (noTrainTrackerClientCreated(clientsApi)) {
            createTrainTrackerClient(clientsApi);
        }

    }

    private boolean noTrainTrackerClientCreated(ClientsResource clientsApi) {
        return clientNotCreated(keycloakProperties.clientId(), clientsApi);
    }

    private boolean noSuperUserCreated(ClientsResource clientsApi) {
        return clientNotCreated(keycloakProperties.superUser().id(), clientsApi);
    }

    private boolean clientNotCreated(String id, ClientsResource clientsApi) {
        return clientsApi.findByClientId(id).isEmpty();
    }

    private Keycloak getKeycloakInstance() {
        return Keycloak.getInstance(
                keycloakProperties.url(),
                "master",
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

    private void createTrainTrackerClient(ClientsResource clientsApi) {
        var client = new ClientRepresentation();
        client.setClientId(keycloakProperties.clientId());
        client.setSecret(keycloakProperties.clientSecret());
        client.setName("Train Tracker");
        client.setEnabled(true);
        client.setBaseUrl("http://localhost:4200");
        client.setRedirectUris(List.of("http://localhost:4200/*"));
        client.setWebOrigins(List.of("*"));
        client.setProtocol("openid-connect");
        client.setClientAuthenticatorType("client-secret");
        client.setStandardFlowEnabled(true);
        client.setImplicitFlowEnabled(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setServiceAccountsEnabled(true);
        client.setAuthorizationServicesEnabled(true);
        client.setPublicClient(false);
        client.setFrontchannelLogout(true);
        client.setFullScopeAllowed(true);

        client.setAttributes(Map.of("post.logout.redirect.uris", "+" ));

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
        var realmManagementId = clientsApi.findByClientId(REALM_MANAGEMENT_CLIENT_ID).get(0).getId();
        var availableRoles = userResource.roles().clientLevel(realmManagementId).listAvailable();
        userResource.roles().clientLevel(realmManagementId).add(availableRoles);
        log.info("Provided realm management roles to service user {} of client {}", serviceUserId, keycloakProperties.superUser().id());
    }
}
