package cn.tgw.common.service.serviceImpl;

import cn.tgw.common.service.AlipayService;
import cn.tgw.config.AlipayConfiguration;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @Project:tgw
 * @Description:ali pay service
 * @Author:TjSanshao
 * @Create:2018-12-06 09:59
 *
 **/
@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayConfiguration alipayConfiguration;

    @Override
    public String alipayWithBaseParam(
            String out_trade_no,
            String total_amount,
            String subject) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfiguration.getGatewayUrl(), alipayConfiguration.getApp_id(), alipayConfiguration.getMerchant_private_key(), "json", alipayConfiguration.getCharset(), alipayConfiguration.getAlipay_public_key(), alipayConfiguration.getSign_type());
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfiguration.getCommonUrlPrefix() + alipayConfiguration.getReturn_url());
        alipayRequest.setNotifyUrl(alipayConfiguration.getCommonUrlPrefix() + alipayConfiguration.getNotify_url());

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayClient.pageExecute(alipayRequest).getBody();
    }

    @Override
    public String alipayWithBizParam(
            String out_trade_no,
            String total_amount,
            String subject,
            String body,
            String goods_detail) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfiguration.getGatewayUrl(), alipayConfiguration.getApp_id(), alipayConfiguration.getMerchant_private_key(), "json", alipayConfiguration.getCharset(), alipayConfiguration.getAlipay_public_key(), alipayConfiguration.getSign_type());
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfiguration.getCommonUrlPrefix() + alipayConfiguration.getReturn_url());
        alipayRequest.setNotifyUrl(alipayConfiguration.getCommonUrlPrefix() + alipayConfiguration.getNotify_url());

        alipayRequest.setBizContent(
                "{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\"" + body + "\","
                + "\"goods_detail\":\"" + goods_detail + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayClient.pageExecute(alipayRequest).getBody();
    }

}
