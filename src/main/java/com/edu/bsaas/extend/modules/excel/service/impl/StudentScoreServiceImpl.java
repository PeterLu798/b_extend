package com.edu.bsaas.extend.modules.excel.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.edu.bsaas.extend.excel.MultiColumnMergeStrategy;
import com.edu.bsaas.extend.modules.excel.dto.ExportDto;
import com.edu.bsaas.extend.modules.excel.entity.AnalysisPersonal;
import com.edu.bsaas.extend.modules.excel.entity.AnalysisScore;
import com.edu.bsaas.extend.modules.excel.entity.ClassTeacher;
import com.edu.bsaas.extend.modules.excel.entity.StudentScore;
import com.edu.bsaas.extend.modules.excel.mapper.AnalysisPersonalMapper;
import com.edu.bsaas.extend.modules.excel.mapper.AnalysisScoreMapper;
import com.edu.bsaas.extend.modules.excel.mapper.ClassTeacherMapper;
import com.edu.bsaas.extend.modules.excel.service.StudentScoreService;
import com.google.api.client.util.ArrayMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentScoreServiceImpl implements StudentScoreService {
    @Autowired
    private AnalysisScoreMapper analysisScoreMapper;
    @Autowired
    private AnalysisPersonalMapper analysisPersonalMapper;
    @Autowired
    private ClassTeacherMapper classTeacherMapper;

    @Override
    public void upload(InputStream inputStream) throws IOException {
        Map<String, List<StudentScore>> classMap = new ArrayMap<>();

        List<Double> total = new ArrayList<>();
        List<Double> totalChinese = new ArrayList<>();
        List<Double> totalMathematics = new ArrayList<>();
        List<Double> totalEnglish = new ArrayList<>();
        List<Double> totalPhysics = new ArrayList<>();
        List<Double> totalChemistry = new ArrayList<>();
        List<Double> totalOrganism = new ArrayList<>();
        List<Double> totalPolitics = new ArrayList<>();
        List<Double> totalHistory = new ArrayList<>();
        List<Double> totalGeography = new ArrayList<>();

        // ReadListener不是必须的，它主要的设计是读取excel数据的后置处理(并考虑一次性读取到内存潜在的内存泄漏问题)
        EasyExcelFactory.read(inputStream, StudentScore.class, new ReadListener<StudentScore>() {
            @Override
            public void invoke(StudentScore user, AnalysisContext analysisContext) {
                List<StudentScore> scoreList = classMap.get(user.getClassName());
                if (scoreList == null) {
                    scoreList = new ArrayList<>();
                }
                scoreList.add(user);
                classMap.put(user.getClassName(), scoreList);

                total.add(user.getChinese() + user.getMathematics() + user.getEnglish() + user.getPhysics() + user.getChemistry()
                        + user.getOrganism() + user.getPolitics() + user.getHistory() + user.getGeography());
                totalChinese.add(user.getChinese());
                totalMathematics.add(user.getMathematics());
                totalEnglish.add(user.getEnglish());
                totalPhysics.add(user.getPhysics());
                totalChemistry.add(user.getChemistry());
                totalOrganism.add(user.getOrganism());
                totalPolitics.add(user.getPolitics());
                totalHistory.add(user.getHistory());
                totalGeography.add(user.getGeography());
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                Map<String, ClassTeacher> classTeacherMap = classTeacherMapper.selectAll().stream().collect(Collectors.toMap(ClassTeacher::getClassName, a -> a));
                double totalMean = Mean(total);
                double totalStd = POP_STD_dev(total);
                double totalChineseMean = Mean(totalChinese);
                double totalChineseStd = POP_STD_dev(totalChinese);
                double totalMathematicsMean = Mean(totalMathematics);
                double totalMathematicsStd = POP_STD_dev(totalMathematics);
                double totalEnglishMean = Mean(totalEnglish);
                double totalEnglishStd = POP_STD_dev(totalEnglish);
                double totalPhysicsMean = Mean(totalPhysics);
                double totalPhysicsStd = POP_STD_dev(totalPhysics);
                double totalChemistryMean = Mean(totalChemistry);
                double totalChemistryStd = POP_STD_dev(totalChemistry);
                double totalOrganismMean = Mean(totalOrganism);
                double totalOrganismStd = POP_STD_dev(totalOrganism);
                double totalPoliticsMean = Mean(totalPolitics);
                double totalPoliticsStd = POP_STD_dev(totalPolitics);
                double totalHistoryMean = Mean(totalHistory);
                double totalHistoryStd = POP_STD_dev(totalHistory);
                double totalGeographyMean = Mean(totalGeography);
                double totalGeographyStd = POP_STD_dev(totalGeography);

                saveAnalysisScore("2023级", "总分", totalMean, totalStd, null, null);
                saveAnalysisScore("2023级", "语文", totalChineseMean, totalChineseStd, null, null);
                saveAnalysisScore("2023级", "数学", totalMathematicsMean, totalMathematicsStd, null, null);
                saveAnalysisScore("2023级", "英语", totalEnglishMean, totalEnglishStd, null, null);
                saveAnalysisScore("2023级", "物理", totalPhysicsMean, totalPhysicsStd, null, null);
                saveAnalysisScore("2023级", "化学", totalChemistryMean, totalChemistryStd, null, null);
                saveAnalysisScore("2023级", "生物", totalOrganismMean, totalOrganismStd, null, null);
                saveAnalysisScore("2023级", "政治", totalPoliticsMean, totalPoliticsStd, null, null);
                saveAnalysisScore("2023级", "历史", totalHistoryMean, totalHistoryStd, null, null);
                saveAnalysisScore("2023级", "地理", totalGeographyMean, totalGeographyStd, null, null);

                for (Map.Entry<String, List<StudentScore>> entry : classMap.entrySet()) {
                    String className = entry.getKey();
                    List<StudentScore> scoreList = entry.getValue();

                    double classTotalStd = 0;
                    double classChineseStd = 0;
                    double classMathematicsStd = 0;
                    double classEnglishStd = 0;
                    double classPhysicsStd = 0;
                    double classChemistryStd = 0;
                    double classOrganismStd = 0;
                    double classPoliticsStd = 0;
                    double classHistoryStd = 0;
                    double classGeographyStd = 0;
                    for (StudentScore user : scoreList) {
                        double personalTotalStd = computeStdScore(user.getChinese() + user.getMathematics() + user.getEnglish() + user.getPhysics() +
                                        user.getChemistry() + user.getOrganism() + user.getPolitics() + user.getHistory() + user.getGeography(),
                                totalMean, totalStd);
                        saveAnalysisPersonal("总分", totalMean, totalStd, className, user, personalTotalStd, user.getMiddleSchoolScore());
                        classTotalStd = classTotalStd + personalTotalStd;

                        double personalChineseStd = computeStdScore(user.getChinese(), totalChineseMean, totalChineseStd);
                        saveAnalysisPersonal("语文", totalChineseMean, totalChineseStd, className, user, personalChineseStd, user.getChinese());
                        classChineseStd = classChineseStd + personalChineseStd;

                        double personalMathematicsStd = computeStdScore(user.getMathematics(), totalMathematicsMean, totalMathematicsStd);
                        saveAnalysisPersonal("数学", totalMathematicsMean, totalMathematicsStd, className, user, personalMathematicsStd, user.getMathematics());
                        classMathematicsStd = classMathematicsStd + personalMathematicsStd;

                        double personalEnglishStd = computeStdScore(user.getEnglish(), totalEnglishMean, totalEnglishStd);
                        saveAnalysisPersonal("英语", totalEnglishMean, totalEnglishStd, className, user, personalEnglishStd, user.getEnglish());
                        classEnglishStd = classEnglishStd + personalEnglishStd;


                        double personalPhysicsStd = computeStdScore(user.getPhysics(), totalPhysicsMean, totalPhysicsStd);
                        classPhysicsStd = classPhysicsStd + personalPhysicsStd;
                        saveAnalysisPersonal("物理", totalPhysicsMean, totalPhysicsStd, className, user, personalPhysicsStd, user.getPhysics());

                        double personalChemistryStd = computeStdScore(user.getChemistry(), totalChemistryMean, totalChemistryStd);
                        classChemistryStd = classChemistryStd + personalChemistryStd;
                        saveAnalysisPersonal("化学", totalChemistryMean, totalChemistryStd, className, user, personalChemistryStd, user.getChemistry());

                        double personalOrganismStd = computeStdScore(user.getOrganism(), totalOrganismMean, totalOrganismStd);
                        classOrganismStd = classOrganismStd + personalOrganismStd;
                        saveAnalysisPersonal("生物", totalOrganismMean, totalOrganismStd, className, user, personalOrganismStd, user.getOrganism());

                        double personalPoliticsStd = computeStdScore(user.getPolitics(), totalPoliticsMean, totalPoliticsStd);
                        classPoliticsStd = classPoliticsStd + personalPoliticsStd;
                        saveAnalysisPersonal("政治", totalPoliticsMean, totalPoliticsStd, className, user, personalPoliticsStd, user.getPolitics());

                        double personalHistoryStd = computeStdScore(user.getHistory(), totalHistoryMean, totalHistoryStd);
                        classHistoryStd = classHistoryStd + personalHistoryStd;
                        saveAnalysisPersonal("历史", totalHistoryMean, totalHistoryStd, className, user, personalHistoryStd, user.getHistory());

                        double personalGeographyStd = computeStdScore(user.getGeography(), totalGeographyMean, totalGeographyStd);
                        classGeographyStd = classGeographyStd + personalGeographyStd;
                        saveAnalysisPersonal("地理", totalGeographyMean, totalGeographyStd, className, user, personalGeographyStd, user.getGeography());
                    }
                    saveAnalysisScore(className, "总分", totalMean, totalStd, classTotalStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "语文", totalChineseMean, totalChineseStd, classChineseStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "数学", totalMathematicsMean, totalMathematicsStd, classMathematicsStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "英语", totalEnglishMean, totalEnglishStd, classEnglishStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "物理", totalPhysicsMean, totalPhysicsStd, classPhysicsStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "化学", totalChemistryMean, totalChemistryStd, classChemistryStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "生物", totalOrganismMean, totalOrganismStd, classOrganismStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "政治", totalPoliticsMean, totalPoliticsStd, classPoliticsStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "历史", totalHistoryMean, totalHistoryStd, classHistoryStd / scoreList.size(), classTeacherMap);
                    saveAnalysisScore(className, "地理", totalGeographyMean, totalGeographyStd, classGeographyStd / scoreList.size(), classTeacherMap);
                }
            }
        }).sheet().doRead();


    }

    @Override
    public void downloadExcel(HttpServletResponse response) throws Exception {
        List<ExportDto> exportDtoList = new ArrayList<>();
        List<AnalysisScore> analysisScoreList = analysisScoreMapper.selectAll();
        ExportDto exportDto1 = new ExportDto();
        exportDto1.setT(AnalysisScore.class);
        exportDto1.setList(analysisScoreList);
        exportDto1.setSheetName("分析表");
        exportDto1.setStrategy(new MultiColumnMergeStrategy(analysisScoreList.size(), 0, 1));
        exportDtoList.add(exportDto1);

        List<AnalysisPersonal> totalList = analysisPersonalMapper.selectByCourse("总分");
        ExportDto exportDto2 = new ExportDto();
        exportDto2.setT(AnalysisPersonal.class);
        exportDto2.setList(totalList);
        exportDto2.setSheetName("总分");
        exportDtoList.add(exportDto2);

        List<AnalysisPersonal> chineseList = analysisPersonalMapper.selectByCourse("语文");
        ExportDto exportDto3 = new ExportDto();
        exportDto3.setT(AnalysisPersonal.class);
        exportDto3.setList(chineseList);
        exportDto3.setSheetName("语文");
        exportDtoList.add(exportDto3);

        List<AnalysisPersonal> mathematicsList = analysisPersonalMapper.selectByCourse("数学");
        ExportDto exportDto4 = new ExportDto();
        exportDto4.setT(AnalysisPersonal.class);
        exportDto4.setList(mathematicsList);
        exportDto4.setSheetName("数学");
        exportDtoList.add(exportDto4);

        List<AnalysisPersonal> englishList = analysisPersonalMapper.selectByCourse("英语");
        ExportDto exportDto5 = new ExportDto();
        exportDto5.setT(AnalysisPersonal.class);
        exportDto5.setList(englishList);
        exportDto5.setSheetName("英语");
        exportDtoList.add(exportDto5);

        List<AnalysisPersonal> physicsList = analysisPersonalMapper.selectByCourse("物理");
        ExportDto exportDto6 = new ExportDto();
        exportDto6.setT(AnalysisPersonal.class);
        exportDto6.setList(physicsList);
        exportDto6.setSheetName("物理");
        exportDtoList.add(exportDto6);

        List<AnalysisPersonal> chemistryList = analysisPersonalMapper.selectByCourse("化学");
        ExportDto exportDto7 = new ExportDto();
        exportDto7.setT(AnalysisPersonal.class);
        exportDto7.setList(chemistryList);
        exportDto7.setSheetName("化学");
        exportDtoList.add(exportDto7);

        List<AnalysisPersonal> organismList = analysisPersonalMapper.selectByCourse("生物");
        ExportDto exportDto8 = new ExportDto();
        exportDto8.setT(AnalysisPersonal.class);
        exportDto8.setList(organismList);
        exportDto8.setSheetName("生物");
        exportDtoList.add(exportDto8);

        List<AnalysisPersonal> politicsList = analysisPersonalMapper.selectByCourse("政治");
        ExportDto exportDto9 = new ExportDto();
        exportDto9.setT(AnalysisPersonal.class);
        exportDto9.setList(politicsList);
        exportDto9.setSheetName("政治");
        exportDtoList.add(exportDto9);

        List<AnalysisPersonal> historyList = analysisPersonalMapper.selectByCourse("历史");
        ExportDto exportDto10 = new ExportDto();
        exportDto10.setT(AnalysisPersonal.class);
        exportDto10.setList(historyList);
        exportDto10.setSheetName("历史");
        exportDtoList.add(exportDto10);

        List<AnalysisPersonal> geographyList = analysisPersonalMapper.selectByCourse("地理");
        ExportDto exportDto11 = new ExportDto();
        exportDto11.setT(AnalysisPersonal.class);
        exportDto11.setList(geographyList);
        exportDto11.setSheetName("地理");
        exportDtoList.add(exportDto11);

        download(response, exportDtoList);
    }

    @Override
    public void uploadClassTeacher(InputStream inputStream) {
        List<ClassTeacher> list = new ArrayList<>();
        EasyExcelFactory.read(inputStream, ClassTeacher.class, new ReadListener<ClassTeacher>() {
            @Override
            public void invoke(ClassTeacher classTeacher, AnalysisContext analysisContext) {
                list.add(classTeacher);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                classTeacherMapper.deleteAll();
                for (ClassTeacher classTeacher : list) {
                    classTeacherMapper.insert(classTeacher);
                }
            }
        }).sheet().doRead();
    }

    @Override
    public void clearData() {
        analysisScoreMapper.deleteAll();
        analysisPersonalMapper.deleteAll();
    }

    private void saveAnalysisPersonal(String course, Double totalMean, Double totalStd, String className, StudentScore user, Double personalTotalStd, Double score) {
        AnalysisPersonal analysisPersonal = new AnalysisPersonal();
        analysisPersonal.setClassName(className);
        analysisPersonal.setStudentId(user.getStudentId());
        analysisPersonal.setName(user.getName());
        analysisPersonal.setMiddleSchoolScore(score);
        analysisPersonal.setGradeMean(totalMean);
        if (totalStd <= 100) {
            totalStd = 100D;
        } else if (totalStd > 900) {
            totalStd = 900D;
        }
        analysisPersonal.setGradeStd(totalStd); //小于等于100取100 高于900取900
        analysisPersonal.setPersonalStd(personalTotalStd);
        analysisPersonal.setCourse(course);
        analysisPersonalMapper.insert(analysisPersonal);
    }

    private void saveAnalysisScore(String className, String courseName, Double gradeMean, Double gradeStd, Double classStd, Map<String, ClassTeacher> classTeacherMap) {
        AnalysisScore analysisScore = new AnalysisScore();
        analysisScore.setClassName(className);
        if (classTeacherMap != null) {
            ClassTeacher classTeacher = classTeacherMap.get(className);
            analysisScore.setClassType(classTeacher.getClassType());
            if (courseName.equals("总分")) {
                analysisScore.setTeacher(classTeacher.getClassTeacher());
            } else if (courseName.equals("语文")) {
                analysisScore.setTeacher(classTeacher.getChineseTeacher());
            } else if (courseName.equals("数学")) {
                analysisScore.setTeacher(classTeacher.getMathematicsTeacher());
            } else if (courseName.equals("英语")) {
                analysisScore.setTeacher(classTeacher.getEnglishTeacher());
            } else if (courseName.equals("物理")) {
                analysisScore.setTeacher(classTeacher.getPhysicsTeacher());
            } else if (courseName.equals("化学")) {
                analysisScore.setTeacher(classTeacher.getChemistryTeacher());
            } else if (courseName.equals("生物")) {
                analysisScore.setTeacher(classTeacher.getOrganismTeacher());
            } else if (courseName.equals("政治")) {
                analysisScore.setTeacher(classTeacher.getPoliticsTeacher());
            } else if (courseName.equals("历史")) {
                analysisScore.setTeacher(classTeacher.getHistoryTeacher());
            } else if (courseName.equals("地理")) {
                analysisScore.setTeacher(classTeacher.getGeographyTeacher());
            }

        } else {
            analysisScore.setClassType("/");
            analysisScore.setTeacher("/");
        }
        analysisScore.setCourse(courseName);
        analysisScore.setGradeMean(gradeMean);
        analysisScore.setGradeStd(gradeStd);
        analysisScore.setClassStd(classStd);
        analysisScoreMapper.insert(analysisScore);
    }

    double computeStdScore(double score, double mean, double totalStd) {
        return (score - mean) / totalStd * 100 + 500;
    }

    double Sum(List<Double> data) {
        double sum = 0;
        for (double datum : data) sum = sum + datum;
        return sum;
    }

    double Mean(List<Double> data) {
        double mean = 0;
        mean = Sum(data) / data.size();
        return mean;
    }

    // population variance 总体方差
    double POP_Variance(List<Double> data) {
        double variance = 0;
        double mean = Mean(data);
        for (int i = 0; i < data.size(); i++) {
            variance = variance + (Math.pow((data.get(i) - mean), 2));
        }
        variance = variance / data.size();
        return variance;
    }

    // population standard deviation 总体标准差
    double POP_STD_dev(List<Double> data) {
        double std_dev;
        std_dev = Math.sqrt(POP_Variance(data));
        return std_dev;
    }

    private void download(HttpServletResponse response, List<ExportDto> exportDtoList) throws Exception {
        String fileName = URLEncoder.encode("成绩分析表" + System.currentTimeMillis(), StandardCharsets.UTF_8);
        response.setContentType("application/vnd.ms-excel");// 设置文本内省
        response.setCharacterEncoding("utf-8");// 设置字符编码
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx"); // 设置响应头
        ExcelWriter excelWriter = EasyExcelFactory.write(response.getOutputStream()).build();

        for (int i = 0; i < exportDtoList.size(); i++) {
            ExportDto exportDto = exportDtoList.get(i);
            WriteSheet writeSheet;
            if (exportDto.getStrategy() != null) {
                writeSheet = EasyExcelFactory.writerSheet(i, exportDto.getSheetName())
                        .registerWriteHandler(exportDto.getStrategy())
                        .head(exportDto.getT()).build();
            } else {
                writeSheet = EasyExcelFactory.writerSheet(i, exportDto.getSheetName()).head(exportDto.getT()).build();
            }
            excelWriter.write(exportDto.getList(), writeSheet);
        }
        excelWriter.finish();
    }
}
