package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.tenant.CreateTenantRequestDTO;
import com.me.yimu.pay.user.entity.Tenant;

@Mapper
public interface TenantRequestConvert {

    TenantRequestConvert INSTANCE = Mappers.getMapper(TenantRequestConvert.class);

    CreateTenantRequestDTO entity2dto(Tenant entity);

    Tenant dto2entity(CreateTenantRequestDTO dto);
}
