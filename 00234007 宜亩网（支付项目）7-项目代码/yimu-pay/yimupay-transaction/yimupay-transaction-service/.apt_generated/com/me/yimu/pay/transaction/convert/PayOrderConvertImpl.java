package com.me.yimu.pay.transaction.convert;

import com.me.yimu.pay.transaction.api.dto.PayOrderDTO;
import com.me.yimu.pay.transaction.entity.PayOrder;
import com.me.yimu.pay.transaction.vo.OrderConfirmVO;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:24:58+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class PayOrderConvertImpl implements PayOrderConvert {

    @Override
    public PayOrderDTO entity2dto(PayOrder entity) {
        if ( entity == null ) {
            return null;
        }

        PayOrderDTO payOrderDTO = new PayOrderDTO();

        payOrderDTO.setAnalysis( entity.getAnalysis() );
        payOrderDTO.setAppId( entity.getAppId() );
        payOrderDTO.setBody( entity.getBody() );
        payOrderDTO.setChannel( entity.getChannel() );
        payOrderDTO.setClientIp( entity.getClientIp() );
        payOrderDTO.setCurrency( entity.getCurrency() );
        payOrderDTO.setDevice( entity.getDevice() );
        payOrderDTO.setExtra( entity.getExtra() );
        payOrderDTO.setOptional( entity.getOptional() );
        payOrderDTO.setOutTradeNo( entity.getOutTradeNo() );
        payOrderDTO.setPayChannel( entity.getPayChannel() );
        payOrderDTO.setStoreId( entity.getStoreId() );
        payOrderDTO.setSubject( entity.getSubject() );
        payOrderDTO.setTotalAmount( entity.getTotalAmount() );

        return payOrderDTO;
    }

    @Override
    public PayOrder dto2entity(PayOrderDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PayOrder payOrder = new PayOrder();

        payOrder.setAnalysis( dto.getAnalysis() );
        payOrder.setAppId( dto.getAppId() );
        payOrder.setBody( dto.getBody() );
        payOrder.setChannel( dto.getChannel() );
        payOrder.setClientIp( dto.getClientIp() );
        payOrder.setCurrency( dto.getCurrency() );
        payOrder.setDevice( dto.getDevice() );
        payOrder.setExtra( dto.getExtra() );
        payOrder.setOptional( dto.getOptional() );
        payOrder.setOutTradeNo( dto.getOutTradeNo() );
        payOrder.setPayChannel( dto.getPayChannel() );
        payOrder.setStoreId( dto.getStoreId() );
        payOrder.setSubject( dto.getSubject() );
        payOrder.setTotalAmount( dto.getTotalAmount() );

        return payOrder;
    }

    @Override
    public PayOrderDTO vo2dto(OrderConfirmVO vo) {
        if ( vo == null ) {
            return null;
        }

        PayOrderDTO payOrderDTO = new PayOrderDTO();

        payOrderDTO.setAppId( vo.getAppId() );
        payOrderDTO.setBody( vo.getBody() );
        payOrderDTO.setChannel( vo.getChannel() );
        payOrderDTO.setOpenId( vo.getOpenId() );
        if ( vo.getStoreId() != null ) {
            payOrderDTO.setStoreId( Long.parseLong( vo.getStoreId() ) );
        }
        payOrderDTO.setSubject( vo.getSubject() );

        return payOrderDTO;
    }
}
