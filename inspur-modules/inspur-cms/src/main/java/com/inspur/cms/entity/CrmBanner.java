package com.inspur.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CrmBanner对象", description="门户")
@TableName("crm_banner")
public class CrmBanner implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "BannerId")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "图片地址")
    private String imageUrl;
    @ApiModelProperty(value = "链接地址")
    private String linkUrl;
    @ApiModelProperty(value = "排序字段")
    private Integer sort;
    @TableLogic
    private Integer isDeleted;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
