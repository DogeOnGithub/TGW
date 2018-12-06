package cn.tgw.common.service;

import com.alipay.api.AlipayApiException;

public interface AlipayService {

    String alipayWithBaseParam(String out_trade_no, String total_amount, String subject) throws AlipayApiException;

}
