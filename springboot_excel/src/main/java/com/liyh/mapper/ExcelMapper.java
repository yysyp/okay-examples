package com.liyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyh.entity.ProjectItem;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: liyh
 * @Date: 2020/10/23 17:46
 */
public interface ExcelMapper extends BaseMapper<ProjectItem> {

    int insertProjectItem(@Param("order") String order, @Param("name") String name, @Param("content") String content,
                          @Param("type") String type, @Param("unit") String unit, @Param("price") String price, @Param("count") String count);
}
