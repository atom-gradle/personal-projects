package com.ganzhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ganzhi.domain.DataCollection;
import com.ganzhi.domain.UploadRequest;
import com.ganzhi.domain.ai.ToAi;
import com.ganzhi.mapper.DataCollectionMapper;
import com.ganzhi.mapper.TaskMapper;
import com.ganzhi.mapper.TaskReceiptMapper;
import com.ganzhi.service.DataCollectionService;
import com.ganzhi.util.AliOssUtil;
import com.ganzhi.util.DataParser;
import com.ganzhi.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("dataCollectionService")
public class DataCollectionServiceImpl extends ServiceImpl<DataCollectionMapper, DataCollection> implements DataCollectionService {

    @Autowired
    private DataCollectionMapper dataCollectionMapper;
    @Autowired
    private TaskReceiptMapper taskReceiptMapper;
    @Autowired
    private TaskMapper taskMapper;

    AliOssUtil aliOssUtil = new AliOssUtil();

    @Override
    public ResponseResult upload(MultipartFile file, String jsonData) {
        //0.转换为类
        // 反序列化 JSON 字符串为 Java 对象
        ObjectMapper mapper = new ObjectMapper();
        UploadRequest request = null;
        try {
            request = mapper.readValue(jsonData, UploadRequest.class);
        } catch (JsonProcessingException e) {
            return ResponseResult.errorResult(400, "上传失败：" + e.getMessage());
        }
        Integer trId = request.getTrId();
        Map<String, String> content = request.getContent();

        // 1. 检查文件是否为空
        if (file.isEmpty()) {
            return ResponseResult.okResult(400, "上传文件不能为空");
        }

        // 2. 检查文件类型
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(".jpg", ".jpeg", ".png", ".gif").contains(fileExtension)) {
            return ResponseResult.okResult(400, "只支持JPG/JPEG/PNG/GIF格式的图片");
        }

        try {
            // 3. 创建存储目录
            String uploadDir = UUID.randomUUID().toString();

            // 4. 生成唯一文件名
            String fileName = fileExtension;
            String filePath = uploadDir + fileName;

            // 5. 保存文件
            String upload = aliOssUtil.upload(file.getBytes(), filePath);

            // 6. 存入数据库
            //文件访问路径规则 https://BucketName.Endpoint/ObjectName
            content.put("url",upload);
            String contents = DataParser.formatToString(content);
            DataCollection data = new DataCollection(null, contents, "null:null", trId, null, null);
            dataCollectionMapper.insert(data);
            System.out.println("content Map: " + content);
            System.out.println("formatToString(content): " + DataParser.formatToString(content));


            return ResponseResult.okResult(data.getId());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.okResult("出现错误");
        }


    }

    @Override
    public ResponseResult getList(Integer trId) {
        //判断参数是否为空
        if (trId==null){
            return ResponseResult.okResult(400,"参数不能为空");
        }
        //获取用户id
        Object id = StpUtil.getLoginId();

        //根据trid获取所有dc数据
        LambdaQueryWrapper<DataCollection> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DataCollection::getTrId,trId);
        List<DataCollection> list = list(queryWrapper);

        //转换数据
        for (DataCollection collection : list) {
            //1.换content
            String content = collection.getContent();
            Map<String, String> map = DataParser.parseToMap(content);
            collection.setContentMap(map);

            //2.换answerContent
            String answerContent = collection.getAnswerContent();
            if (!Objects.isNull(answerContent)){
                Map<String, String> map1 = DataParser.parseToMap(answerContent);
                collection.setAnswerContentMap(map1);
            }
        }

        return ResponseResult.okResult(list);

    }


    @Override
    public ResponseResult getUnfinishedCollection() {
        //获取dc的answer为空的
        LambdaQueryWrapper<DataCollection> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DataCollection::getAnswerContent,"null:null");
        List<DataCollection> dataCollections = dataCollectionMapper.selectList(queryWrapper);


        List<ToAi> toAiList=new ArrayList<>();
        for (DataCollection dataCollection : dataCollections) {
            //填入map集合
            dataCollection.setContentMap(DataParser.parseToMap(dataCollection.getContent()));

            //通过trId获取taskId
            Integer trId = dataCollection.getTrId();
            Integer taskId = taskReceiptMapper.selectById(trId).getTaskId();


            //通过taskId获取docker
            String docker = taskMapper.selectById(taskId).getAlgorithmDocker();

            //填入ToAi
            toAiList.add(new ToAi(dataCollection,docker));
        }

        return ResponseResult.okResult(toAiList);



    }

    @Override
    public ResponseResult uploadFinishedCollection(DataCollection dataCollection) {
        if (Objects.isNull(dataCollection)||Objects.isNull(dataCollection.getAnswerContentMap())||Objects.isNull(dataCollection.getId())){
            return ResponseResult.okResult(400,"数据不能为空");
        }

        //转换数据
        dataCollection.setAnswerContent(DataParser.formatToString(dataCollection.getAnswerContentMap()));
        dataCollection.setContentMap(null);
        dataCollection.setAnswerContentMap(null);
        //存入数据库
        dataCollectionMapper.updateById(dataCollection);

        return ResponseResult.okResult("添加成功");

    }

}
