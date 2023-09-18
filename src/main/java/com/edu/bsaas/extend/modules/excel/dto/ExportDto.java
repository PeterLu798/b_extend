package com.edu.bsaas.extend.modules.excel.dto;

import com.edu.bsaas.extend.excel.MultiColumnMergeStrategy;
import lombok.Data;

import java.util.List;

@Data
public class ExportDto {
    private Class t;
    private List list;
    private String sheetName;
    private MultiColumnMergeStrategy strategy;
}
