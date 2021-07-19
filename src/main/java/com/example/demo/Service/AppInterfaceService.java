package com.example.demo.Service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public interface AppInterfaceService {

    //向某moduleid的接口发送请求，请求参数为querystr
    String queryAppInterfaceList(String querystr, String moduleId);

    //将map的请求参数转化为String
    static String ForeachMapToStr(Map<String, Object> querymap,String fieldstrIn, String fieldstrOut){
        String[] arrayIn = fieldstrIn.split(",");
        String[] arrayOut = fieldstrOut.split(",");
        String str = "";// 返回的拼接字符串变量
        for (int i = 0; i < arrayIn.length; i++) {
            str += arrayOut[i] + "=" + querymap.get(arrayIn[i]) + "&";
        }
        return str;
    };

    //将map的请求参数转化为MultiValueMap
    static MultiValueMap<String,String> ForeachMapToMultiValueMap(Map<String, Object> querymap,
                                                                  String fieldstrIn, String fieldstrOut) {
        String[] arrayIn = fieldstrIn.split(",");
        String[] arrayOut = fieldstrOut.split(",");
//        String str = "";// 返回的拼接字符串变量
        MultiValueMap<String,String> result = new LinkedMultiValueMap<>();
        for (int i = 0; i < arrayIn.length; i++) {
//            str += arrayOut[i] + "=" + querymap.get(arrayIn[i]) + "&";
            result.add(arrayOut[i],(String)querymap.get(arrayIn[i]));
        }
        return result;
    }

    /**
     * 循环取出查询字符串中查询变量和查询变量名称的值拼写到格式查询字符串中
     *
     * @param querystr
     *            查询字符串
     * @return 拼装好的格式化查询字符串
     */

    static HashMap<String, Object> ForeachStrToMap(String querystr) {

        HashMap<String, Object> mapstr = new HashMap<String, Object>();
        String name = ""; // 查询变量名称
        String value = "";// 查询变量取值
        int index_mh = 0;// 冒号下标
        int index_dh = 0;// 逗号下标
        int indexTemp = 0; // 下标中间量
        do { // 查找查询字符串中冒号的下标
            index_mh = querystr.indexOf(":", indexTemp);
            // 截取查询字符串中变量的名称
            name = querystr.substring(index_dh, index_mh);
            // 下标进行移动
            indexTemp = index_mh + 1;
            // 取得下一个逗号存在的位置下标
            index_dh = querystr.indexOf(",", indexTemp);
            // 当取到最后没有逗号分隔时说明字符串已经取到最后一个变量，返回下标取为字符串的结束位置下标
            if (index_dh == -1) {
                index_dh = querystr.length();
            } // 取得查询字符串中的变量值
            value = querystr.substring(index_mh + 1, index_dh);
            // str += name + "=" + value + "&";
            // 变量下标+1处理：等到下次从逗号下标开始截取时不会把逗号字符截取包含到需要的值中 index_dh++;
            index_dh += 1;
            // 将取到的变量名称和变量值放到map集合中
            mapstr.put(name, value);
            // 当查询下一个变量名称不存在时退出循环
        } while (querystr.indexOf(":", indexTemp) != -1);

        return mapstr;
    }

    //发送post请求封装方法
    static String sendPost(String url,Map<String,Object> querymap,String fieldstrIn, String fieldstrOut){
        //使用RestTemplate主动发送HttpPOST请求
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map1= ForeachMapToMultiValueMap(querymap,fieldstrIn,fieldstrOut);


        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map1, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        System.out.println(response.getBody());
        return response.getBody();
    }
}
