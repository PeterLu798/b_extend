package com.edu.bsaas.extend.modules.excel.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public interface StudentScoreService {

    void upload(InputStream inputStream) throws IOException;

    void downloadExcel(HttpServletResponse response) throws Exception;

    void uploadClassTeacher(InputStream inputStream);

    void clearData();

}
