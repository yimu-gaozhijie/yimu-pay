package com.me.yimu.pay.merchant.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.merchant.api.dto.StaffDTO;
import com.me.yimu.pay.merchant.entity.Staff;

import java.util.List;

@Mapper
public interface StaffConvert {

    StaffConvert INSTANCE = Mappers.getMapper(StaffConvert.class);

    StaffDTO entity2dto(Staff entity);

    Staff dto2entity(StaffDTO dto);

    List<StaffDTO> listentity2dto(List<Staff> staff);

}
