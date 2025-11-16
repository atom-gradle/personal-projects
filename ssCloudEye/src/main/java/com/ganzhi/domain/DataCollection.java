package com.ganzhi.domain;

import java.io.Serializable;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("data_collection")
public class DataCollection {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private String answerContent;

    private Integer trId;

    @TableField(select = false)
    private Map<String,String> contentMap;

    @TableField(select = false)
    private Map<String,String> answerContentMap;

}

