package com.pm.billing.service.grpc;

import billing.BillingResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(
            billing.BillingRequest billingRequest,
            StreamObserver<BillingResponse> billingResponseStreamObserver) {
        log.info("CreateBillingAccount request {}",billingRequest.toString());
        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("101")
                .setStatus("ACTIVE")
                .build();
        billingResponseStreamObserver.onNext(billingResponse);
        billingResponseStreamObserver.onCompleted();
    }

}
