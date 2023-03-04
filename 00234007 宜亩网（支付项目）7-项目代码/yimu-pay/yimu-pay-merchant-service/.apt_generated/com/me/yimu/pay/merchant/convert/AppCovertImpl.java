package com.me.yimu.pay.merchant.convert;

import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.entity.App;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:25:06+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class AppCovertImpl implements AppCovert {

    @Override
    public AppDTO entity2dto(App entity) {
        if ( entity == null ) {
            return null;
        }

        AppDTO appDTO = new AppDTO();

        appDTO.setAppId( entity.getAppId() );
        appDTO.setAppName( entity.getAppName() );
        appDTO.setMerchantId( entity.getMerchantId() );
        appDTO.setNotifyUrl( entity.getNotifyUrl() );
        appDTO.setPublicKey( entity.getPublicKey() );

        return appDTO;
    }

    @Override
    public App dto2entity(AppDTO dto) {
        if ( dto == null ) {
            return null;
        }

        App app = new App();

        app.setAppId( dto.getAppId() );
        app.setAppName( dto.getAppName() );
        app.setMerchantId( dto.getMerchantId() );
        app.setNotifyUrl( dto.getNotifyUrl() );
        app.setPublicKey( dto.getPublicKey() );

        return app;
    }

    @Override
    public List<AppDTO> listentity2dto(List<App> app) {
        if ( app == null ) {
            return null;
        }

        List<AppDTO> list = new ArrayList<AppDTO>( app.size() );
        for ( App app1 : app ) {
            list.add( entity2dto( app1 ) );
        }

        return list;
    }
}
