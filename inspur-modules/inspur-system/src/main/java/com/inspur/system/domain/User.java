package com.inspur.system.domain;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.stereotype.Repository;
import java.util.Date;



@Data
@TableName("acl_user")
@ApiModel(value="User对象", description="用户")
@Repository
public class User {
    @TableId(value = "id",type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String username;

    private String password;

    private String nickName;

    private String salt;

    private String token;

    @TableLogic
    private Integer isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
}
