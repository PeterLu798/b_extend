package com.edu.bsaas.extend.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.merge.AbstractMergeStrategy;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayList;
import java.util.List;

public class MultiColumnMergeStrategy extends AbstractMergeStrategy {

    // 合并的列编号，从0开始，指定的index或自己按字段顺序数
    private Integer startCellIndex = 0;
    private Integer endCellIndex = 0;

    // 数据集大小，用于区别结束行位置
    private Integer maxRow = 0;

    // 禁止无参声明
    private MultiColumnMergeStrategy() {
    }


    public MultiColumnMergeStrategy(Integer maxRow, Integer startCellIndex, Integer endCellIndex) {
        this.startCellIndex = startCellIndex;
        this.endCellIndex = endCellIndex;
        this.maxRow = maxRow;
    }

    // 记录上一次合并的信息
    private final List<List<String>> dataList = new ArrayList<>();

    /**
     * 每行每列都会进入，循环注意条件限制
     */
    @Override
    protected void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex) {
        int currentCellIndex = cell.getColumnIndex();
        int currentRowIndex = cell.getRowIndex();

        // 判断该列是否需要合并
        if (currentCellIndex < startCellIndex || currentCellIndex > endCellIndex) {
            return;
        }

        Object curData = cell.getCellTypeEnum() == CellType.STRING ? cell.getStringCellValue() : cell.getNumericCellValue();
        String currentCellValue = curData.toString();

        List<String> rowList;
        if (dataList.size() > currentRowIndex - 1) {
            rowList = dataList.get(currentRowIndex - 1);
        } else {
            rowList = new ArrayList<>();
            dataList.add(rowList);
        }
        rowList.add(currentCellValue);

        // 结束的位置触发下最后一次没完成的合并
        if (relativeRowIndex == (maxRow - 1) && currentCellIndex == endCellIndex) {
            System.out.println(JSONObject.toJSONString(dataList));
            List<String> tempList = null;
            Integer tempIndex = null;
            for (int i = 0; i < dataList.size(); i++) {
                if (tempList == null) {
                    tempList = dataList.get(i);
                    tempIndex = i;
                    continue;
                }
                List<String> currList = dataList.get(i);
                if (tempList.equals(currList)) {
                    if (i >= dataList.size() - 1) {
                        // 结束的位置触发下最后一次没完成的合并
                        for (int j = 0; j < tempList.size(); j++) {
                            sheet.addMergedRegionUnsafe(new CellRangeAddress(tempIndex + 1, i + 1, startCellIndex + j, startCellIndex + j));
                        }
                    }
                    continue;
                }

                // 当前行数据和上一行数据不同且上面有多行相同数据时触发合并
                if (i - tempIndex > 1) {
                    for (int j = 0; j < tempList.size(); j++) {
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(tempIndex + 1, i, startCellIndex + j, startCellIndex + j));
                    }
                }


                tempIndex = i;
                tempList = currList;


            }

        }
    }
}