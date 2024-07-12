package com.luo.auth.infrastructure.converter;

import com.luo.auth.domain.userAggregate.entity.Tenant;
import com.luo.auth.infrastructure.repository.po.TenantPO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-12T15:44:20+0800",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class TenantConverterImpl implements TenantConverter {

    @Override
    public List<Tenant> tenantPOsToTenants(List<TenantPO> tenantPOs) {
        if ( tenantPOs == null ) {
            return null;
        }

        List<Tenant> list = new ArrayList<Tenant>( tenantPOs.size() );
        for ( TenantPO tenantPO : tenantPOs ) {
            list.add( tenantPOToTenant( tenantPO ) );
        }

        return list;
    }

    protected Tenant tenantPOToTenant(TenantPO tenantPO) {
        if ( tenantPO == null ) {
            return null;
        }

        Tenant.TenantBuilder tenant = Tenant.builder();

        tenant.tenantId( tenantPO.getTenantId() );
        tenant.tenantName( tenantPO.getTenantName() );
        tenant.contactName( tenantPO.getContactName() );
        tenant.contactPhone( tenantPO.getContactPhone() );
        tenant.contactEmail( tenantPO.getContactEmail() );
        tenant.cert( tenantPO.getCert() );
        tenant.certIssueDate( tenantPO.getCertIssueDate() );
        tenant.certExpiryDate( tenantPO.getCertExpiryDate() );
        if ( tenantPO.getCertStatus() != null ) {
            tenant.certStatus( tenantPO.getCertStatus().name() );
        }

        return tenant.build();
    }
}
