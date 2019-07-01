package com.Controller;

import com.Service.FlowService;
import com.model.PageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/6/26.
 */
@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    @Autowired
    private FlowService flowService;

    @GetMapping("/export/{tablename}")
    public  String  export(@PathVariable  String tablename) {
        PageModel pageModel = new PageModel();
        pageModel.setTableName(tablename.trim());
        String path = flowService.operater(pageModel);
        return path;
    }

}
