package com.ganzhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ganzhi.domain.DataCollection;
import com.ganzhi.util.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface DataCollectionService extends IService<DataCollection> {

    ResponseResult getList(Integer trId);

    ResponseResult getUnfinishedCollection();

    ResponseResult uploadFinishedCollection(DataCollection dataCollection);

    ResponseResult upload(MultipartFile file, String jsonData);

}

