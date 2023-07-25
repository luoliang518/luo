package com.luo.controller;

import com.luo.service.UserPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/test/poi")
public class UserPoiController {
    @Autowired
    private UserPoiService userPoiService;

    @GetMapping(value = "/excel")
    public void excel(@RequestBody List<String> ids , HttpServletResponse response) {
            userPoiService.exportExcel(response,ids);
    }
}
