package com.me.yimu.pay.transaction.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.transaction.api.dto.PayOrderDTO;
import com.me.yimu.pay.transaction.entity.PayOrder;
import com.me.yimu.pay.transaction.vo.OrderConfirmVO;

@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    PayOrderDTO entity2dto(PayOrder entity);

    PayOrder dto2entity(PayOrderDTO dto);

    //忽略totalAmount，不做映射
    @Mapping(target = "totalAmount", ignore = true)
    PayOrderDTO vo2dto(OrderConfirmVO vo);

}
