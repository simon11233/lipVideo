package com.inspur.edu.client;

import com.inspur.core.domain.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
