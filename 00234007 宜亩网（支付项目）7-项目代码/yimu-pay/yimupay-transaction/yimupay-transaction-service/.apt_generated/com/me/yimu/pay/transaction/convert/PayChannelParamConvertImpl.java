package com.me.yimu.pay.transaction.convert;

import com.me.yimu.pay.transaction.api.dto.PayChannelParamDTO;
import com.me.yimu.pay.transaction.entity.PayChannelParam;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:24:58+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class PayChannelParamConvertImpl implements PayChannelParamConvert {

    @Override
    public PayChannelParamDTO entity2dto(PayChannelParam entity) {
        if ( entity == null ) {
            return null;
        }

        PayChannelParamDTO payChannelParamDTO = new PayChannelParamDTO();

        payChannelParamDTO.setAppPlatformChannelId( entity.getAppPlatformChannelId() );
        payChannelParamDTO.setChannelName( entity.getChannelName() );
        payChannelParamDTO.setId( entity.getId() );
        payChannelParamDTO.setMerchantId( entity.getMerchantId() );
        payChannelParamDTO.setParam( entity.getParam() );
        payChannelParamDTO.setPayChannel( entity.getPayChannel() );

        return payChannelParamDTO;
    }

    @Override
    public PayChannelParam dto2entity(PayChannelParamDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PayChannelParam payChannelParam = new PayChannelParam();

        payChannelParam.setAppPlatformChannelId( dto.getAppPlatformChannelId() );
        payChannelParam.setChannelName( dto.getChannelName() );
        payChannelParam.setId( dto.getId() );
        payChannelParam.setMerchantId( dto.getMerchantId() );
        payChannelParam.setParam( dto.getParam() );
        payChannelParam.setPayChannel( dto.getPayChannel() );

        return payChannelParam;
    }

    @Override
    public List<PayChannelParamDTO> listentity2listdto(List<PayChannelParam> PlatformChannel) {
        if ( PlatformChannel == null ) {
            return null;
        }

        List<PayChannelParamDTO> list = new ArrayList<PayChannelParamDTO>( PlatformChannel.size() );
        for ( PayChannelParam payChannelParam : PlatformChannel ) {
            list.add( entity2dto( payChannelParam ) );
        }

        return list;
    }

    @Override
    public List<PayChannelParam> listdto2listentity(List<PayChannelParamDTO> PlatformChannelDTO) {
        if ( PlatformChannelDTO == null ) {
            return null;
        }

        List<PayChannelParam> list = new ArrayList<PayChannelParam>( PlatformChannelDTO.size() );
        for ( PayChannelParamDTO payChannelParamDTO : PlatformChannelDTO ) {
            list.add( dto2entity( payChannelParamDTO ) );
        }

        return list;
    }
}
