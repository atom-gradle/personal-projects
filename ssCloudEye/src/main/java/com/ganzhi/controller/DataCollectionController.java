package com.ganzhi.controller;

import com.ganzhi.domain.DataCollection;
import com.ganzhi.domain.UploadRequest;
import com.ganzhi.service.DataCollectionService;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Map;

@RestController
@RequestMapping("/dataCollection")
public class DataCollectionController {

    @Autowired
    private DataCollectionService dataCollectionService;


    //单个照片的上传
    @PostMapping("/upload")//map集合在其中）都设置成param会有问题，spring自动放入map中
    public ResponseResult upload(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData){
        return dataCollectionService.upload(file,jsonData);

    }

    //通过trid获取dc集合
    @GetMapping("/getList")
    public ResponseResult getList(@RequestParam Integer trId){
        return dataCollectionService.getList(trId);
    }


    ///////////////////////////与school交互

    //获取未完成的数据收集
    @GetMapping("/getUnfinishedCollection")
    public ResponseResult getUnfinishedCollection(){
        return dataCollectionService.getUnfinishedCollection();
    }


    //上传数据采集的结果
    @PostMapping ("/uploadFinishedCollection")
    public ResponseResult uploadFinishedCollection(@RequestBody DataCollection dataCollection){
        return dataCollectionService.uploadFinishedCollection(dataCollection);
    }


}

