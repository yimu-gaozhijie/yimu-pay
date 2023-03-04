package com.me.yimu.pay.transaction.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.transaction.api.dto.PlatformChannelDTO;
import com.me.yimu.pay.transaction.entity.PlatformChannel;

import java.util.List;

@Mapper
public interface PlatformChannelConvert {

    PlatformChannelConvert INSTANCE = Mappers.getMapper(PlatformChannelConvert.class);

    PlatformChannelDTO entity2dto(PlatformChannel entity);

    PlatformChannel dto2entity(PlatformChannelDTO dto);

    List<PlatformChannelDTO> listentity2listdto(List<PlatformChannel> PlatformChannel);
}
