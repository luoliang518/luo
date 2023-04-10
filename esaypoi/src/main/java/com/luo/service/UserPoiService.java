package com.luo.service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserPoiService {
    void exportExcel(HttpServletResponse response, List<String> ids);
}
