package com.me.yimu.pay.merchant.convert;

import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.entity.Merchant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:25:05+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class MerchantConvertImpl implements MerchantConvert {

    @Override
    public Merchant dto2entity(MerchantDTO merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        Merchant merchant = new Merchant();

        merchant.setAuditStatus( merchantDTO.getAuditStatus() );
        merchant.setBusinessLicensesImg( merchantDTO.getBusinessLicensesImg() );
        merchant.setContactsAddress( merchantDTO.getContactsAddress() );
        merchant.setId( merchantDTO.getId() );
        merchant.setIdCardAfterImg( merchantDTO.getIdCardAfterImg() );
        merchant.setIdCardFrontImg( merchantDTO.getIdCardFrontImg() );
        merchant.setMerchantAddress( merchantDTO.getMerchantAddress() );
        merchant.setMerchantName( merchantDTO.getMerchantName() );
        merchant.setMerchantNo( merchantDTO.getMerchantNo() );
        merchant.setMerchantType( merchantDTO.getMerchantType() );
        merchant.setMobile( merchantDTO.getMobile() );
        merchant.setTenantId( merchantDTO.getTenantId() );
        merchant.setUsername( merchantDTO.getUsername() );

        return merchant;
    }

    @Override
    public MerchantDTO entity2dto(Merchant merchant) {
        if ( merchant == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setAuditStatus( merchant.getAuditStatus() );
        merchantDTO.setBusinessLicensesImg( merchant.getBusinessLicensesImg() );
        merchantDTO.setContactsAddress( merchant.getContactsAddress() );
        merchantDTO.setId( merchant.getId() );
        merchantDTO.setIdCardAfterImg( merchant.getIdCardAfterImg() );
        merchantDTO.setIdCardFrontImg( merchant.getIdCardFrontImg() );
        merchantDTO.setMerchantAddress( merchant.getMerchantAddress() );
        merchantDTO.setMerchantName( merchant.getMerchantName() );
        merchantDTO.setMerchantNo( merchant.getMerchantNo() );
        merchantDTO.setMerchantType( merchant.getMerchantType() );
        merchantDTO.setMobile( merchant.getMobile() );
        merchantDTO.setTenantId( merchant.getTenantId() );
        merchantDTO.setUsername( merchant.getUsername() );

        return merchantDTO;
    }

    @Override
    public List<MerchantDTO> entityList2dtoList(List<Merchant> merchants) {
        if ( merchants == null ) {
            return null;
        }

        List<MerchantDTO> list = new ArrayList<MerchantDTO>( merchants.size() );
        for ( Merchant merchant : merchants ) {
            list.add( entity2dto( merchant ) );
        }

        return list;
    }
}
