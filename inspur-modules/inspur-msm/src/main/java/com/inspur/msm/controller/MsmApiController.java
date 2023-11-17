package com.inspur.msm.controller;

import com.inspur.core.domain.R;
import com.inspur.core.utils.StringUtils;
import com.inspur.msm.service.MsmService;
import com.inspur.msm.utils.RandomUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@CrossOrigin
@RestController
@RequestMapping("/edu/msm")
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> template;

    @GetMapping("/send/{phone}")
    public R code(@PathVariable String phone){
        String code = template.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        code = RandomUtil.getSixBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean isSend = msmService.send(phone, "SMS_463705304", param);
        if(isSend) {
            template.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("发送短信失败");
        }
    }

}
