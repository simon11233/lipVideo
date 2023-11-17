package com.inspur.ucenter.mapper;

import com.inspur.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-03-09
 */
@Mapper
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    Integer selectRegisterCount(@Param("day") String day);
}
