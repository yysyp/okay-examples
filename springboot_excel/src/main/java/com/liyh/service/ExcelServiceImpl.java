package com.liyh.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liyh.entity.ProjectItem;
import com.liyh.entity.Result;
import com.liyh.mapper.ExcelMapper;
import com.liyh.utils.ExcelTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liyh
 * @Date: 2020/10/23 17:44
 */
@Service
public class ExcelServiceImpl extends ServiceImpl<ExcelMapper, ProjectItem> implements ExcelService {

    @Resource
    private ExcelMapper excelMapper;

    @Override
    public Result importProject(MultipartFile file) {
        // 解析Excel数据
        Result result = readDataFromExcel(file, 0, 1);

        if (result.isFlag()) {
            List<ProjectItem> items = (List<ProjectItem>) result.getData();

            if (items == null || items.size() <= 0) {
                return Result.error("没有数据！！！");
            }

            // 插入数据表格中的数据
            this.saveBatch(items);

//        for (ProjectItem item : items) {
//            // 保存数据
//            int insert = baseMapper.insertProjectItem(item.getOrder(), item.getName(), item.getContent(), item.getType(), item.getUnit(), item.getPrice(), item.getCount());
//            this.saveBatch(items);
//            if (insert <= 0) {
//                return Result.error("导入失败");
//            }
//        }

            return Result.success("导入成功");
        } else return result;

    }

    /**
     * 解析Excel数据
     *
     * @param file 文件
     * @param sheetNum 第几个sheet
     * @param startRow 从第几行开始读
     * @return
     */
    public Result readDataFromExcel(MultipartFile file, int sheetNum, int startRow) {
        POIFSFileSystem pfs = null;
        Workbook workbook = null;
        try {
            // 解析xls和xlsx不兼容问题
            workbook = ExcelTool.getWorkBook(pfs, workbook, file);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("模板保存异常。");
        }
        if (workbook == null) {
            return Result.error("请使用模板上传文件");
        }
        // 判断有记录的列数（表格的列数和表的列一样）
        if (workbook.getSheetAt(sheetNum).getRow(0).getPhysicalNumberOfCells() != 7) {
            return Result.error("请使用类型所对应的模板");
        }

        List<ProjectItem> list = new ArrayList<>();
        // 获取表格sheet的内容
        Sheet sheetAt = workbook.getSheetAt(sheetNum);
        // 获得sheet总行数
        int lastRowNum = sheetAt.getLastRowNum();
        if (lastRowNum < 1) {
            return Result.error("数据错误");
        }
        // （注意！！！！！！！！！！！！！）开始读取,不读取表头所以从第二行开始
        for (int i = startRow; i <= lastRowNum; i++) {
            // 获取每一行
            Row row = sheetAt.getRow(i);
            // 行为空不读取
            if (row == null) continue;
            Cell cell = row.getCell(0);
            // 列为空不读取
            if (cell == null || StringUtils.isEmpty(convertData(cell))) continue;

            // 创建对象封装行数据
            ProjectItem projectItem = new ProjectItem();

            // 创建一个集合根据下标来确定每个单元格对应对象的什么属性
            List<String> rowList = new ArrayList<>();
            // 添加数据
            for (int j = 0; j < 7; j++) {
                Cell cellOne = row.getCell(j);
                if (cellOne == null) {
                    rowList.add("");
                } else {
                    try {
                        String item = convertData(cellOne);
                        rowList.add(item);
                    } catch (Exception e) {
                        System.out.println("-------------------Err-----------------------");
                        System.out.println(i + "行" + j + "列数据转换出现异常");
                        rowList.add("");
                    }
                }
            }
            // 规避行数数据后几行为空
            if (rowList.size() < 7) {
                for (int k = 0; k < 7 - rowList.size(); k++) {
                    rowList.add("");
                }
            }

            // 添加数据
            projectItem.setNumber(rowList.get(0).trim());
            projectItem.setName(rowList.get(1).trim());
            projectItem.setContent(rowList.get(2).trim());
            projectItem.setType(rowList.get(3).trim());
            projectItem.setUnit(rowList.get(4).trim());
            projectItem.setPrice(rowList.get(5).trim());
            projectItem.setCount(rowList.get(6).trim());
            list.add(projectItem);
        }
        return Result.success("解析成功", list);
    }

    /**
     * 表格数据转换
     *
     * @param cell
     * @return
     */
    public String convertData(Cell cell) {
        String str = "";

        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                // 判断是否是整数
                str = NumberFormat.getNumberInstance().format(cell.getNumericCellValue());
                break;
            case STRING:
                str = cell.getStringCellValue();
                break;
            case _NONE:
                str = "";
                break;
            case BLANK:
                str = "";
                break;
            case FORMULA:
                try {
                    str = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalArgumentException e) {
                    str = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            default:
                str = "";
        }
        return str;
    }
}
