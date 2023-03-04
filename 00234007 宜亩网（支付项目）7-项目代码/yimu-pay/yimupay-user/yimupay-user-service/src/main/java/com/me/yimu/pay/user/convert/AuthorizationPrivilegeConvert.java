package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.authorization.PrivilegeDTO;
import com.me.yimu.pay.user.entity.AuthorizationPrivilege;

import java.util.List;

@Mapper
public interface AuthorizationPrivilegeConvert {
    AuthorizationPrivilegeConvert INSTANCE = Mappers.getMapper(AuthorizationPrivilegeConvert.class);

    PrivilegeDTO entity2dto(AuthorizationPrivilege entity);

    AuthorizationPrivilege dto2entity(PrivilegeDTO dto);

    List<PrivilegeDTO> entitylist2dto(List<AuthorizationPrivilege>  authorizationRole);
}
