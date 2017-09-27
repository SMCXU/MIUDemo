package com.example.customrecycle.frame.retrofit;


import com.example.customrecycle.frame.utils.JsonUtil;
import com.example.customrecycle.frame.utils.StringUtils;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


/**
 * 返回结果
 *
 * @author Lenovo
 */
@SuppressWarnings("rawtypes")
public class ResultData<T> {
    private int PageSize;
    private int PageIndex;
    private int Total;//查询到数据总数
    private int ItemTotal;
    private int Code;//状态码：已在别处登录，登陆超时等等
    private String Costtime;//响应时间
    private boolean IsSuccess;
    private String Success;//错误代码 1-成功 其他失败
    private String Msg;//错误信息
    private T Data;//返回结果  返回单个对象
    private List DataList = new ArrayList();//返回结果  返回列表数据

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int pageSize) {
        PageSize = pageSize;
    }

    public int getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(int pageIndex) {
        PageIndex = pageIndex;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(int itemTotal) {
        ItemTotal = itemTotal;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getCosttime() {
        return Costtime;
    }

    public void setCosttime(String costtime) {
        Costtime = costtime;
    }

    public boolean isSuccess() {
        return IsSuccess;
    }

    public void setSuccess(boolean success) {
        IsSuccess = success;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public List getDataList() {
        return DataList;
    }

    public void setDataList(List dataList) {
        DataList = dataList;
    }

    /**
     * 字符串转实体
     *
     * @param json
     * @param clazz
     * @return
     */
    public ResultData parse(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return parse(JsonUtil.toJsonObject(json), clazz);
    }

    /**
     * JsonObject转实体
     *
     * @param jsonObject
     * @param clazz
     * @return
     */
    public ResultData parse(JsonObject jsonObject, Class<T> clazz) {
        if (null == jsonObject) {
            return null;
        }

        ResultData result = new ResultData();
        try {
            if (null != jsonObject.get("Success")) {
                result.setSuccess(jsonObject.get("Success").toString());
            }
            if (null != jsonObject.get("PageSize")) {
                result.setPageSize(Integer.parseInt(jsonObject.get("PageSize").toString()));
            }
            if (null != jsonObject.get("PageIndex")) {
                result.setPageIndex(Integer.parseInt(jsonObject.get("PageIndex").toString()));
            }
            if (null != jsonObject.get("ItemTotal")) {
                result.setItemTotal(Integer.parseInt(jsonObject.get("ItemTotal").toString()));
            }
            if (null != jsonObject.get("total")) {
                result.setTotal(Integer.parseInt(jsonObject.get("total").toString()));
            }
            if (null != jsonObject.get("IsSuccess")) {
                result.setSuccess(Boolean.valueOf(jsonObject.get("IsSuccess").toString()));
            }


            if (null != jsonObject.get("Code")) {
                int code = Integer.parseInt(jsonObject.get("Code").toString());
                result.setTotal(code);
                //拦截器处理错误码
                LoginInterceptor.parseCode(code);
            }
            if (null != jsonObject.get("Costtime")) {
                String costTime = jsonObject.get("Costtime").toString();
                result.setCosttime(costTime);
                //拦截器处理请求时间
//                LoginInterceptor.parseCosttime(costTime);
            }
            if (null != jsonObject.get("Msg")) {
                String message = jsonObject.get("Msg").toString();
                if (message.contains("\"")) {
                    message = message.replace("\"", "");
                }
                result.setMsg(message);
            }

            //一般情况下是"data"
            if (null != jsonObject.get("subjects")) {
                //判断是实体还是列表
                Object oJson = new JSONTokener(jsonObject.get("subjects").toString()).nextValue();
                if (oJson instanceof JSONObject) {
                    if (clazz == String.class) {
                        result.setData(jsonObject.get("subjects").toString());
                    } else {
                        result.setData(JsonUtil.fromJsonString(jsonObject.get("subjects").toString(), clazz));
                    }
                } else if (oJson instanceof JSONArray) {
                    List list = JsonUtil.fromJsonList(jsonObject.get("subjects").toString(), clazz);
                    result.getDataList().addAll(list);
                }else{
                    result.setData(jsonObject.get("subjects").toString());
                }
            }
            if (null != jsonObject.get("total")) {
                result.setTotal(jsonObject.get("total").getAsInt());
            }
        } catch (Exception e) {
            System.out.println("ResultData解析错误：" + e.toString());
        }

        return result;
    }

}
