package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.resource.ApplicationDTO;
import com.me.yimu.pay.user.entity.ResourceApplication;

import java.util.List;

@Mapper
public interface ResourceApplicationConvert {


    ResourceApplicationConvert INSTANCE = Mappers.getMapper(ResourceApplicationConvert.class);

    ApplicationDTO entity2dto(ResourceApplication entity);

    ResourceApplication dto2entity(ApplicationDTO dto);

    List<ApplicationDTO> entitylist2dto(List<ResourceApplication> bundle);
}
