package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.authorization.RoleDTO;
import com.me.yimu.pay.user.entity.AuthorizationRole;

import java.util.List;

@Mapper
public interface AuthorizationRoleConvert {

    AuthorizationRoleConvert INSTANCE = Mappers.getMapper(AuthorizationRoleConvert.class);

    @Mappings({
            @Mapping(target="name",source = "name"),
            @Mapping(target="code",source = "code"),
            @Mapping(target="tenantId",source = "tenantId")}
    )
    RoleDTO entity2dto(AuthorizationRole entity);

    @Mappings({
            @Mapping(target="name",source = "name"),
            @Mapping(target="code",source = "code"),
            @Mapping(target="tenantId",source = "tenantId")}
    )
    AuthorizationRole dto2entity(RoleDTO dto);

    List<RoleDTO> entitylist2dto(List<AuthorizationRole>  authorizationRole);

    List<AuthorizationRole> dtolist2entity(List<RoleDTO>  roleDTOS);

}
