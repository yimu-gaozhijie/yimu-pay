package com.me.yimu.pay.merchant.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.entity.App;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Mapper
public interface AppCovert {

    AppCovert INSTANCE = Mappers.getMapper(AppCovert.class);

    AppDTO entity2dto(App entity);

    App dto2entity(AppDTO dto);

    List<AppDTO> listentity2dto(List<App> app);

}
