package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.tenant.TenantDTO;
import com.me.yimu.pay.user.entity.Tenant;

@Mapper
public interface TenantConvert {

    TenantConvert INSTANCE = Mappers.getMapper(TenantConvert.class);

    TenantDTO entity2dto(Tenant entity);

    Tenant dto2entity(TenantDTO dto);
}
