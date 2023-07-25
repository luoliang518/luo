package com.luo.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONArray;
import com.luo.excelModel.UserExportVO;
import com.luo.excelModel.UserFiledMapper;
import com.luo.excelModel.UserRoleVo;
import com.luo.login.mapper.UserMapper;
import com.luo.login.mapper.UserRoleMapper;
import com.luo.model.user.entity.UserDo;
import com.luo.model.user.entity.UserRoleDo;
import com.luo.service.UserPoiService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserPoiServiceImpl implements UserPoiService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void exportExcel(HttpServletResponse response,List<String> ids) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("content-disposition",
                    "attachment;fileName=" + URLEncoder.encode("批量导出-内部用户信息-" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xlsx", "UTF-8"));
            ServletOutputStream outputStream = response.getOutputStream();

            List<UserDo> userDos = userMapper.selectList(null);
            ArrayList<UserExportVO> userExportVOS = new ArrayList<>();
            for (UserDo userDo : userDos) {
                UserExportVO userExportVO = UserFiledMapper.INSTANCE.userDoDto2Vo(userDo);
                String userRolesIds = userExportVO.getUserRolesIds();
                JSONArray objects = JSONArray.parseArray(userRolesIds);
                ArrayList<UserRoleVo> userRoleVos = new ArrayList<>();
                for (Object object : objects) {
                    UserRoleDo userRoleDo = userRoleMapper.selectById(object.toString());
                    UserRoleVo userRoleVo = UserFiledMapper.INSTANCE.userRoleDoDto2Vo(userRoleDo);
                    userRoleVos.add(userRoleVo);
                }
                userExportVO.setUserRoleDos(userRoleVos);
                userExportVOS.add(userExportVO);
            }

//            for (int i = 0; i < 5000; i++) {
//                UserExportVO userExportVO = new UserExportVO();
//                userExportVO.setUserId(String.valueOf(i));
//                ArrayList<UserRoleVo> userRoleDos = new ArrayList<>();
//                for (int j = 0; j < 3; j++) {
//                    UserRoleVo userRoleDo = new UserRoleVo();
//                    userRoleDo.setRoleId(String.valueOf(j));
//                    userRoleDo.setRoleName(ids.toString());
//                    userRoleDos.add(userRoleDo);
//                }
//                userExportVO.setUserRoleDos(userRoleDos);
//                userExportVOS.add(userExportVO);
//            }

            ExportParams exportParams = new ExportParams();
            exportParams.setSheetName("用户信息");
//            exportParams.setTitle("导出用户信息");
//            Workbook workbook = ExcelExportUtil.exportExcel(exportParams, UserExportVO.class, userExportVOS);
            int totalPage = (userExportVOS.size() / 1000) + 1;
            int pageSize = 1000;
            Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, UserExportVO.class, (queryParams, page) -> {
                if (page > totalPage) {
                    return null;
                }
                // fromIndex开始索引，toIndex结束索引
                int fromIndex = (page - 1) * pageSize;
                int toIndex = page != totalPage ? fromIndex + pageSize : userExportVOS.size();
                return new ArrayList<>(userExportVOS.subList(fromIndex, toIndex));
            }, totalPage);
            // 输出
            workbook.write(outputStream);
            // 关闭资源
            outputStream.close();
            workbook.close();
        } catch (Exception e) {
        }
    }
}
