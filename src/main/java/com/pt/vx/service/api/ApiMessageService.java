package com.pt.vx.service.api;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.stream.CollectorUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pt.vx.config.KeyConfig;
import com.pt.vx.config.MainConfig;
import com.pt.vx.pojo.BirthDay;
import com.pt.vx.pojo.User;
import com.pt.vx.pojo.KeyDTO;
import com.pt.vx.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class ApiMessageService {

    private final SecureRandom random = new SecureRandom();

    public static List<KeyDTO> keyDTOS = new ArrayList<>();

    static {
        keyDTOS.add(KeyConfig.KEY_QING_HUA);
    }
    public String getApiMessage(KeyDTO keyDTO, User user){
        String result = null;
        if (KeyConfig.KEY_QING_HUA.equalsKey(keyDTO)){
            result =  getQinghua();
        }
        log.info("随机API接口为：{},获取的结果为：{}",keyDTO.getKey(),result);
        return result;
    }

    /**
     *
     * @return 随机一个API访问
     */
    public KeyDTO getRandomKey(){
        List<KeyDTO> collect = keyDTOS.stream().filter(KeyDTO::isOpen).collect(Collectors.toList());
        if(collect.isEmpty()){
            return null;
        }
        int i = random.nextInt(collect.size());
        return collect.get(i);
    }

    /**
     * 随机情话
     *
     * @return
     */
    private String getQinghua() {
        String url = "https://api.uomg.com/api/rand.qinghua?format=json";
        String s = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(s);
        return jsonObject.getStr("content");
    }

}
