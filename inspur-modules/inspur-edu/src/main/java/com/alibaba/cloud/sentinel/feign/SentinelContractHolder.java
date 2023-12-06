package com.alibaba.cloud.sentinel.feign;

import feign.Contract;
import feign.MethodMetadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentinelContractHolder implements Contract {

    private final Contract delegate;
    public final static Map<String, MethodMetadata> METADATA_MAP = new HashMap<>();
    public SentinelContractHolder(Contract delegate) {
        this.delegate = delegate;
    }
    @Override
    public List<MethodMetadata> parseAndValidatateMetadata(Class<?> targetType) {
        List<MethodMetadata> metadatas  = delegate.parseAndValidatateMetadata(targetType);
        metadatas.forEach(methodMetadata -> METADATA_MAP.put(targetType.getName()+methodMetadata.configKey(),methodMetadata));
        return metadatas;
    }
}
