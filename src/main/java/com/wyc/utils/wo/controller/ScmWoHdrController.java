package com.wyc.utils.wo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyc.utils.wo.entity.ScmWoHdr;
import com.wyc.utils.wo.service.ScmWoHdrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wyc
 * @since 2021-07-26
 */
@RestController
@RequestMapping("/wo/scm-wo-hdr")
public class ScmWoHdrController {

    @Autowired
    ScmWoHdrService scmWoHdrService;

    @PutMapping("/saveWoList")
    @Transactional(rollbackFor = Exception.class)
    public void saveWoList(@RequestBody List<ScmWoHdr> woHdrList){
        scmWoHdrService.saveBatch(woHdrList);
    }

    @GetMapping("/getWoList")
    public List<ScmWoHdr> saveWoList(String id){
        List<ScmWoHdr> list = scmWoHdrService.list(new QueryWrapper<ScmWoHdr>().lambda().eq(ScmWoHdr::getsWoCode, id));
        return list;
    }

}

