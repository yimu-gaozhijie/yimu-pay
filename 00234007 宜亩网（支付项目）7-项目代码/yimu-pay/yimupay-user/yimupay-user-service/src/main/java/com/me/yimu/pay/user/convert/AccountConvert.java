package com.me.yimu.pay.user.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.me.yimu.pay.user.api.dto.tenant.AccountDTO;
import com.me.yimu.pay.user.entity.Account;

@Mapper
public interface AccountConvert {

    AccountConvert INSTANCE = Mappers.getMapper(AccountConvert.class);

    //@Mappings({
    //        @Mapping(target="username", source="username")
    //})
    AccountDTO entity2dto(Account entity);


    //@Mappings({
    //        @Mapping(target="username", source="username"),
    //        @Mapping(target = "mobile",source="mobile"),
    //        @Mapping(target = "password",source="password"),
    //        @Mapping(target = "salt",source="salt")
    //})
    Account dto2entity(AccountDTO dto);

}
