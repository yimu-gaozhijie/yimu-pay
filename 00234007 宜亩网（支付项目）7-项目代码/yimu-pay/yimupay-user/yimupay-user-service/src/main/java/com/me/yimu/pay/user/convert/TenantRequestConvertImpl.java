package com.me.yimu.pay.user.convert;

import javax.annotation.Generated;

import com.me.yimu.pay.user.api.dto.tenant.CreateTenantRequestDTO;
import com.me.yimu.pay.user.entity.Tenant;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-12-20T18:24:19+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class TenantRequestConvertImpl implements TenantRequestConvert {

    @Override
    public CreateTenantRequestDTO entity2dto(Tenant entity) {
        if ( entity == null ) {
            return null;
        }

        CreateTenantRequestDTO createTenantRequestDTO = new CreateTenantRequestDTO();

        createTenantRequestDTO.setName( entity.getName() );
        createTenantRequestDTO.setTenantTypeCode( entity.getTenantTypeCode() );
        createTenantRequestDTO.setBundleCode( entity.getBundleCode() );

        return createTenantRequestDTO;
    }

    @Override
    public Tenant dto2entity(CreateTenantRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tenant tenant = new Tenant();

        tenant.setName( dto.getName() );
        tenant.setTenantTypeCode( dto.getTenantTypeCode() );
        tenant.setBundleCode( dto.getBundleCode() );

        return tenant;
    }
}
