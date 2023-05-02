package io.mwi.traintracker.api.user;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "name", source = "username")
    UserDto mapUserRepresentation(UserRepresentation userRepresentation);

}
