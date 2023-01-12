package com.karfield.automatrix;

import com.karfield.automatrix.impl.AmContextImpl;
import com.karfield.automatrix.ipc.*;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AmRuntime {
    private final AmTask task;

    public AmRuntime(AmTask task) {
        this.task = task;
    }

    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        ManagedChannel channel = Grpc.newChannelBuilder("localhost:" + System.getenv("AM_PORT"), InsecureChannelCredentials.create())
                .executor(executor).build();
        BaseIpcGrpc.BaseIpcBlockingStub baseIpcStub = BaseIpcGrpc.newBlockingStub(channel);
        Base.CapabilitiesResponse capabilities = baseIpcStub.capabilities(Base.CapabilitiesRequest.newBuilder().build());

        CdpIpcGrpc.CdpIpcBlockingStub cdpIpcStub = null;
        if (capabilities.getCdp()) {
            cdpIpcStub = CdpIpcGrpc.newBlockingStub(channel);
        }
        OcrIpcGrpc.OcrIpcBlockingStub ocrIpcStub = null;
        if (capabilities.getOcr()) {
            ocrIpcStub = OcrIpcGrpc.newBlockingStub(channel);
        }
        SqlIpcGrpc.SqlIpcBlockingStub sqlIpcStub = null;
        if (capabilities.getSql()) {
            sqlIpcStub = SqlIpcGrpc.newBlockingStub(channel);
        }

        Iterator<Base.ConsumeTaskResponse> taskIter = baseIpcStub.consumeTask(Base.ConsumeTaskRequest.newBuilder().build());
        while (taskIter.hasNext()) {
            Base.ConsumeTaskResponse response = taskIter.next();

            AmContextImpl ctx = new AmContextImpl(baseIpcStub, cdpIpcStub, ocrIpcStub, sqlIpcStub, response.getTraceId(), response.getPayload());
            try {
                AmTaskResult result = task.run(ctx);
                if (result.getError() != null) {
                    baseIpcStub.finishWithFailure(Base.ExecuteFailure.newBuilder()
                            .setTraceId(response.getTraceId())
                            .setError(result.getError()).build());
                } else {
                    baseIpcStub.finishWithResult(Base.ExecuteResult.newBuilder()
                            .setTraceId(response.getTraceId())
                            .setOutput(result.getData())
                            .setPortIndicator(result.getDirection()).build());
                }
            } catch (Exception exception) {
                baseIpcStub.finishWithFailure(Base.ExecuteFailure.newBuilder()
                        .setTraceId(response.getTraceId())
                        .setError(exception.getLocalizedMessage()).build());
            }
        }
    }
}
