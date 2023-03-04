package com.me.yimu.pay.merchant.convert;

import javax.annotation.Generated;

import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.vo.MerchantRegisterVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-12-29T17:14:10+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class MerchantRegisterConvertImpl implements MerchantRegisterConvert {

    @Override
    public MerchantRegisterVO dto2vo(MerchantDTO merchantDTO) {
        if ( merchantDTO == null ) {
            return null;
        }

        MerchantRegisterVO merchantRegisterVO = new MerchantRegisterVO();

        merchantRegisterVO.setMobile( merchantDTO.getMobile() );
        merchantRegisterVO.setUsername( merchantDTO.getUsername() );

        return merchantRegisterVO;
    }

    @Override
    public MerchantDTO vo2dto(MerchantRegisterVO merchantRegisterVO) {
        if ( merchantRegisterVO == null ) {
            return null;
        }

        MerchantDTO merchantDTO = new MerchantDTO();

        merchantDTO.setUsername(merchantRegisterVO.getUsername());
        merchantDTO.setMobile(merchantRegisterVO.getMobile());
        merchantDTO.setPassword(merchantRegisterVO.getPassword());

        return merchantDTO;
    }
}
