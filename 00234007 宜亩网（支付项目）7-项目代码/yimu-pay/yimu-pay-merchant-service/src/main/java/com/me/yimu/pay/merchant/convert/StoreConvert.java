package com.me.yimu.pay.merchant.convert;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.merchant.api.dto.StoreDTO;
import com.me.yimu.pay.merchant.entity.Store;

import java.util.List;

@Mapper
public interface StoreConvert {

    StoreConvert INSTANCE = Mappers.getMapper(StoreConvert.class);

    StoreDTO entity2dto(Store entity);

    Store dto2entity(StoreDTO dto);

    List<StoreDTO> listentity2dto(List<Store> staff);
}
