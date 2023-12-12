package com.inspur.cms.controller;

import com.inspur.cms.entity.CrmBanner;
import com.inspur.cms.service.CrmBannerService;
import com.inspur.core.domain.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cms/")
public class BannerApiController {

    @Autowired
    private CrmBannerService crmBannerService;


    @ApiOperation(value = "获取首页banner")
    @GetMapping("getAllBanner")
    @Cacheable(value = "banner", key = "'selectIndexList'")
    public R getAllBanner(){
        List<CrmBanner> crmBanners = crmBannerService.getAllBanner();
        return R.ok().data("items",crmBanners);
    }


}
