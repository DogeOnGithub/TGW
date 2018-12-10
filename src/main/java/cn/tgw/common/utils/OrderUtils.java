package cn.tgw.common.utils;

import java.util.Date;

/*
 * @Project:tgw
 * @Description:order number generate tools
 * @Author:TjSanshao
 * @Create:2018-12-10 11:26
 *
 **/
public class OrderUtils {

    public static String getOrderNumber(String mobile) {

        String numberTail = mobile.substring(7);

        String orderNumber = new Date().getTime() + numberTail;

        return orderNumber;
    }

    public static void main(String[] args) {
        System.out.println(getOrderNumber("13420120424"));
    }

}
