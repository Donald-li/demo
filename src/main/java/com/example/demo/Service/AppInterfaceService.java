package com.example.demo.Service;

import com.example.demo.Dao.AppInterfaceDao;
import com.example.demo.pojo.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppInterfaceService {

    @Autowired
    private AppInterfaceDao appInterfaceDao;

    public String queryAppInterfaceList(String querystr, String moduleId) {

        List<AppConfig> mapConfig = appInterfaceDao
                .getAppConfigsByModuleID(moduleId);
        Map<String, Object> mapZtbmAndkm = null;
        String str = "";
        // 获取访问配置信息
        String strUrl = "";
        String result = "";
        Map<String, Object> querymap = AppInterfaceService
                .ForeachStrToMap(querystr);
//
//        String kjnd = querymap.get("kjnd") == null ? "0" : querymap.get("kjnd")
//                .toString().trim();
//        String kjyf = querymap.get("kjyf") == null ? "0" : querymap.get("kjyf")
//                .toString().trim();
//        String kjqjq = querymap.get("kjqjq") == null ? "0" : querymap
//                .get("kjqjq").toString().trim();
//        String kjqjz = querymap.get("kjqjz") == null ? "0" : querymap
//                .get("kjqjz").toString().trim();
//
//        if(moduleId.equals("zwmx") || moduleId.equals("zqmx")){
//            kjnd = kjqjq.substring(0,4);//2017-10-01
//            kjqjq = kjqjq.substring(5,7);
//            kjqjz = kjqjz.substring(5,7);
//        }
//        if(moduleId.equals("zcfzb")){
//            kjqjq = kjyf;
//        }
//
//        // 包含ztbm,kmdm字段值的map集合
//        mapZtbmAndkm = appInterfaceDao.queryZtbmAndkm(
//                moduleId, zzdwdm, kjnd, kjqjq, kjqjz);
////		}
//
//        if (mapZtbmAndkm.get("KM_DM") != null) {
//            querymap.put("kmdm", mapZtbmAndkm.get("KM_DM"));
//        }
//        if (mapZtbmAndkm.get("KM_JC") != null) {
//            querymap.put("kmjc", mapZtbmAndkm.get("KM_JC"));
//        }
//        // 集合合并
//        querymap.put("ztbm", mapZtbmAndkm.get("ZT_BM"));
//        querymap.put("moduleId", moduleId);

        for (AppConfig temp : mapConfig) {
            String system_no = temp.getSystem_no() == null ? "" : temp.getSystem_no();
            String ip = temp.getIp() == null ? "" : temp.getIp();
            String port = temp.getPort() == 0 ? "" : Integer.toString(temp.getPort()).trim();
            String path = temp.getPath() == null ? "" : temp.getPath().trim();
            String fieldstrIn = temp.getField_in() == null ? "" : temp.getField_in().trim();
            String fieldstrOut = temp.getField_out() == null ? "" : temp.getField_out().trim();
            //System.out.println(str);
            // 查询字符串拼接
//            str = ForeachMapToStr(querymap, fieldstrIn, fieldstrOut);
            //System.out.println(str);
            strUrl = "http://" + ip + ":" + port + "/" + system_no + "/" + path;
            System.out.println(strUrl);
            result = sendPost(strUrl,querymap,fieldstrIn,fieldstrOut);
//            result = HttpRequest.sendPost(strUrl,
//                    str.substring(0, str.length() - 1));

        }
        return result;
    }



    /**
     * 将接收到的查询字段集合解析出按照三资平台后台类字段格式初始化相关字段并赋值
     *
     * @param querymap
     *            查询集合
     * @param fieldstrIn
     *            输入字段
     * @param fieldstrOut
     *            格式化输出字段
     * @return 格式化字符串
     */
    public static String ForeachMapToStr(Map<String, Object> querymap,
                                         String fieldstrIn, String fieldstrOut) {
        String[] arrayIn = fieldstrIn.split(",");
        String[] arrayOut = fieldstrOut.split(",");
        String str = "";// 返回的拼接字符串变量
        for (int i = 0; i < arrayIn.length; i++) {
            str += arrayOut[i] + "=" + querymap.get(arrayIn[i]) + "&";
        }
        return str;
    }

    public static MultiValueMap<String,String> ForeachMapToMultiValueMap(Map<String, Object> querymap,
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

    public static HashMap<String, Object> ForeachStrToMap(String querystr) {

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
    public static String sendPost(String url,Map<String,Object> querymap,String fieldstrIn, String fieldstrOut){
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
