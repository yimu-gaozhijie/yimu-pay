package com.me.yimu.pay.transaction.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.transaction.api.dto.PayChannelParamDTO;
import com.me.yimu.pay.transaction.entity.PayChannelParam;

import java.util.List;

@Mapper
public interface PayChannelParamConvert {

    PayChannelParamConvert INSTANCE= Mappers.getMapper(PayChannelParamConvert.class);

    PayChannelParamDTO entity2dto(PayChannelParam entity);

    PayChannelParam dto2entity(PayChannelParamDTO dto);

    List<PayChannelParamDTO> listentity2listdto(List<PayChannelParam> PlatformChannel);

    List<PayChannelParam> listdto2listentity(List<PayChannelParamDTO> PlatformChannelDTO);
}
