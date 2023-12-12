package com.inspur.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inspur.cms.entity.CrmBanner;
import com.inspur.cms.service.CrmBannerService;
import com.inspur.core.domain.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/cms/banner")
public class CrmBannerController {

    @Autowired
    private CrmBannerService crmBannerService;


    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page, @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        Page<CrmBanner> pageParam = new Page<>(page, limit);
        crmBannerService.page(pageParam);
        return R.ok().data("items",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    @ApiOperation(value = "更新或修改")
    @PostMapping("save")
    public R save(@RequestBody CrmBanner banner){
        if(Objects.equals(banner.getId(), "''")){
            banner.setId(null);
            crmBannerService.saveOrUpdate(banner);
        }else {
            crmBannerService.saveOrUpdate(banner);
        }
        return R.ok();
    }

    @ApiOperation(value = "更新或修改")
    @DeleteMapping("")
    public R remove(String id){
        crmBannerService.removeBannerById(id);
        return R.ok();
    }
}
