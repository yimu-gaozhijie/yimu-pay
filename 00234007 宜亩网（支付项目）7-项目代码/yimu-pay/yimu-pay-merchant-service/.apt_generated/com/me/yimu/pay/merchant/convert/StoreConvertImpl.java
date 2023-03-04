package com.me.yimu.pay.merchant.convert;

import com.me.yimu.pay.merchant.api.dto.StoreDTO;
import com.me.yimu.pay.merchant.entity.Store;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-03T16:25:06+0800",
    comments = "version: 1.3.0.Final, compiler: Eclipse JDT (IDE) 3.21.0.v20200304-1404, environment: Java 1.8.0_301 (Oracle Corporation)"
)
public class StoreConvertImpl implements StoreConvert {

    @Override
    public StoreDTO entity2dto(Store entity) {
        if ( entity == null ) {
            return null;
        }

        StoreDTO storeDTO = new StoreDTO();

        storeDTO.setId( entity.getId() );
        storeDTO.setMerchantId( entity.getMerchantId() );
        storeDTO.setParentId( entity.getParentId() );
        storeDTO.setStoreAddress( entity.getStoreAddress() );
        storeDTO.setStoreName( entity.getStoreName() );
        storeDTO.setStoreNumber( entity.getStoreNumber() );
        storeDTO.setStoreStatus( entity.getStoreStatus() );

        return storeDTO;
    }

    @Override
    public Store dto2entity(StoreDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Store store = new Store();

        store.setId( dto.getId() );
        store.setMerchantId( dto.getMerchantId() );
        store.setParentId( dto.getParentId() );
        store.setStoreAddress( dto.getStoreAddress() );
        store.setStoreName( dto.getStoreName() );
        store.setStoreNumber( dto.getStoreNumber() );
        store.setStoreStatus( dto.getStoreStatus() );

        return store;
    }

    @Override
    public List<StoreDTO> listentity2dto(List<Store> staff) {
        if ( staff == null ) {
            return null;
        }

        List<StoreDTO> list = new ArrayList<StoreDTO>( staff.size() );
        for ( Store store : staff ) {
            list.add( entity2dto( store ) );
        }

        return list;
    }
}
