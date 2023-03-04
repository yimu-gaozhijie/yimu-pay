package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.menu.MenuDTO;
import com.me.yimu.pay.user.entity.ResourceMenu;

import java.util.List;

@Mapper
public interface ResourceMenuConvert {

    ResourceMenuConvert INSTANCE = Mappers.getMapper(ResourceMenuConvert.class);

    MenuDTO entity2dto(ResourceMenu entity);

    ResourceMenu dto2entity(MenuDTO dto);

    List<MenuDTO> entitylist2dto(List<ResourceMenu> resourceMenu);

    List<ResourceMenu> dtolist2entity(List<MenuDTO> menuDTO);

}
