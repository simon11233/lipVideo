package com.inspur.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.inspur.cms.entity.CrmBanner;

import java.util.List;

public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner>  getAllBanner();
    CrmBanner getBannerById(String id);
    void saveBanner(CrmBanner banner);
    void updateBannerById(CrmBanner banner);
    void removeBannerById(String id);
}
