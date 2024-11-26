package com.liyh.excelfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liyh.excelfile.entity.ProjectItem;
import com.liyh.excelfile.entity.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: liyh
 * @Date: 2020/10/23 17:44
 */
public interface ExcelService extends IService<ProjectItem> {

    Result importProject(MultipartFile file);

    String importProjects(MultipartFile[] files);

}
