package io.mwi.traintracker.api.login;

import io.mwi.traintracker.keycloak.AccessTokenResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AccessTokenMapper {

    @Mapping(target = "expiresInSeconds", source = "expiresIn")
    LoginResponse mapAccessTokenResponse(AccessTokenResponse accessTokenResponse);

}
