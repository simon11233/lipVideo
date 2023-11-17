package com.inspur.msm.service;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface MsmService {
    boolean send(String phoneNumber, String templeCode, Map<String,Object> params);
}
