
package org.hding.HaoYangMao.API.Huobi;

import org.hding.HaoYangMao.API.util.JsonParser;


/**
 * @author hding 2015年12月30日
 */
public class HuobiMain {
    private static String BUY = "buy";
    private static String BUY_MARKET = "buy_market";
    private static String CANCEL_ORDER = "cancel_order";
    private static String ACCOUNT_INFO = "get_account_info";
    private static String NEW_DEAL_ORDERS = "get_new_deal_orders";
    private static String ORDER_ID_BY_TRADE_ID = "get_order_id_by_trade_id";
    private static String GET_ORDERS = "get_orders";
    private static String ORDER_INFO = "order_info";
    private static String SELL = "sell";
    private static String SELL_MARKET = "sell_market";
    private static String CURR_PRICE = "http://api.huobi.com/staticmarket/ticker_btc_json.js";


    public static void main(String[] args) {
        HuobiService service = new HuobiService();
        try {
            System.out.println("Huobi.com API Testing...");
            //service.printKey();

            // 提交市价单接口
            //System.out.println(service.buyMarket(1, "2", null, null, BUY_MARKET));
            // 取消订单接口
            //System.out.println(service.cancelOrder(1, 160801, CANCEL_ORDER));
            // 获取账号详情
            System.out.println("Getting Account Information...");
            String res = service.getAccountInfo(ACCOUNT_INFO);
            String status = "";
            System.out.println("Total Asset: " + JsonParser.getValue(res, "total"));
            System.out.println("Net Asset: " + JsonParser.getValue(res, "net_asset"));
            System.out.println("Available CNY: " + JsonParser.getValue(res, "available_cny_display"));

            System.out.println("Fetching Current BIT Coin Prices...");
            for (int i = 0; i < 10; i++) {
                long ts = System.currentTimeMillis();
                res = service.getCurrPrice(CURR_PRICE);
                long te = System.currentTimeMillis();
                System.out.print("Network Delay for Fetching Prices: " + (te - ts) + "ms   \r");
            }
            System.out.println();
            //while (true) {
                //System.out.print("System Time Stamp: " + System.currentTimeMillis() + "  ");
                long timeStart = System.currentTimeMillis();
                res = service.getCurrPrice(CURR_PRICE);
                double currPrice = Double.parseDouble(JsonParser.getValue(res, "ticker.last"));
                //System.out.println(currPrice);
                res = service.buy(1, Double.toString(currPrice), "0.001", null, null, BUY);
                status = JsonParser.getValue(res, "result");
                if (status == null || !status.equals("success")) {
                    System.out.println("Error when buying.");
                    return;
                } else {
                    System.out.println("Put Transaction (BUY)!");
                }
                status = "";
                String[] doublePart = Double.toString(currPrice + 0.01).split("\\.");
                String sellPrice = doublePart[0] + '.' +
                                   doublePart[1].charAt(0);
                if (doublePart[1].length() >= 2) {
                    sellPrice += doublePart[1].charAt(1);
                }
                System.out.println("Waiting for concluding current transaction...");
                while (status == null || !status.equals("success")) {
                    res = service.sell(1, sellPrice, "0.001", null, null, SELL);
                    status = JsonParser.getValue(res, "result");
                    //System.out.println(res);
                    //System.out.println(status);
                }
                //System.out.println("Checking order history...");
                System.out.println("Buy price: " + currPrice);
                System.out.println("Sell price: " + sellPrice);
                String orderID = JsonParser.getValue(res, "id");
                boolean isNotComplete = true;
                while (isNotComplete) {
                    res = service.getOrders(1, GET_ORDERS);
                    String[] token = res.split("\\},\\{");
                    for (String s : token) {
                        status = JsonParser.getValue(s, "id");
                        if (status != null && status.equals(orderID)) {
                            isNotComplete = true;
                            break;
                        } else {
                            isNotComplete = false;
                        }
                    }
                }
                long timeEnd = System.currentTimeMillis();
                System.out.println("Valid Transaction (SELL)!");
                System.out.print("Network Delay: ");
                System.out.println((timeEnd - timeStart) + "ms  ");
                //System.out.print("Current Price (CNY): ");
                //System.out.print(JsonParser.getValue(res, "ticker.last") + "      " + "\r");
            //}
            //{"time":"1451503632","ticker":
            // {"open":2774.36,"vol":1304832.7993,"last":2783.77,"buy":2783.67,"sell":2783.77,"high":2794.12,"low":2773} }
            // Request latest 10 valid transactions
            //System.out.println(service.getNewDealOrders(1, NEW_DEAL_ORDERS));
            // base on trade_id to check oder_id
            //System.out.println(service.getOrderIdByTradeId(1, 2, ORDER_ID_BY_TRADE_ID));
            // Request all floating transactions
            //res = service.getOrders(1, GET_ORDERS);

            // Request order details
            //System.out.println(service.getOrderInfo(1, 160802, ORDER_INFO));

            // Sell at market price
            //System.out.println(service.sellMarket(1, "2", null, null, SELL_MARKET));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
