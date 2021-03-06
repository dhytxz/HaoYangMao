package org.hding.HaoYangMao.API.Huobi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.hding.HaoYangMao.API.util.JsonParser;

/**
 * @author yanjg 2014年11月22日
 */
public class HuobiService extends Base {
    public HuobiService() throws Exception {
        File directory = new File("");
        String parentPath = "";
        String filePath = "";
        try {
            parentPath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        filePath += parentPath + "/config";
        File file = new File(filePath);
        BufferedReader reader = null;
        String lineStr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                lineStr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        HUOBI_ACCESS_KEY = JsonParser.getValue(lineStr,"Huobi.ACCESS_KEY");
        HUOBI_SECRET_KEY = JsonParser.getValue(lineStr,"Huobi.ACCESS_KEY");
        if (HUOBI_ACCESS_KEY == null || HUOBI_SECRET_KEY == null) {
            throw new Exception("Access or secret keys cannot be null!");
        }
    }

    public void printKey() {
        System.out.println("Access Key:" + HUOBI_ACCESS_KEY);
        System.out.println("Secret Key:" + HUOBI_SECRET_KEY);
    }

    /**
     * 下单接口
     * 
     * @param coinType
     * @param price
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String buy(int coinType, String price, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("price", price);
        paraMap.put("amount", amount);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 提交市价单接口
     * 
     * @param coinType
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String buyMarket(int coinType, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("amount", amount);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 撤销订单
     * 
     * @param coinType
     * @param id
     * @param method
     * @return
     * @throws Exception
     */
    public String cancelOrder(int coinType, long id, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("id", id);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 获取账号详情
     * 
     * @param method
     * @return
     * @throws Exception
     */
    public String getAccountInfo(String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 查询个人最新10条成交订单
     * 
     * @param coinType
     * @param method
     * @return
     * @throws Exception
     */
    public String getNewDealOrders(int coinType, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 根据trade_id查询oder_id
     * 
     * @param coinType
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrderIdByTradeId(int coinType, long tradeid, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("trade_id", tradeid);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 获取所有正在进行的委托
     * 
     * @param coinType
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrders(int coinType, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 获取订单详情
     * 
     * @param coinType
     * @param id
     * @param method
     * @return
     * @throws Exception
     */
    public String getOrderInfo(int coinType, long id, String method) throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("id", id);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 限价卖出
     * 
     * @param coinType
     * @param price
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String sell(int coinType, String price, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("price", price);
        paraMap.put("amount", amount);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return post(paraMap, HUOBI_API_URL);
    }

    /**
     * 市价卖出
     * 
     * @param coinType
     * @param amount
     * @param tradePassword
     * @param tradeid
     * @param method
     * @return
     * @throws Exception
     */
    public String sellMarket(int coinType, String amount, String tradePassword, Integer tradeid, String method)
            throws Exception {
        TreeMap<String, Object> paraMap = new TreeMap<String, Object>();
        paraMap.put("method", method);
        paraMap.put("created", getTimestamp());
        paraMap.put("access_key", HUOBI_ACCESS_KEY);
        paraMap.put("secret_key", HUOBI_SECRET_KEY);
        paraMap.put("coin_type", coinType);
        paraMap.put("amount", amount);
        String md5 = sign(paraMap);
        paraMap.remove("secret_key");
        paraMap.put("sign", md5);
        if (StringUtils.isNotEmpty(tradePassword)) {
            paraMap.put("trade_password", tradePassword);
        }
        if (null != tradeid) {
            paraMap.put("trade_id", tradeid);
        }
        return post(paraMap, HUOBI_API_URL);
    }

    public String getCurrPrice() throws Exception {
        return post(HUOBI_BTC_MARKET_URL);
    }
}
