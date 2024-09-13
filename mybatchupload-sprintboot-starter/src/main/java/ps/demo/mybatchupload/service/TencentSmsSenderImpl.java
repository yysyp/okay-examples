package ps.demo.mybatchupload.service;

import ps.demo.mybatchupload.config.SmsProperties;

public class TencentSmsSenderImpl implements SmsSender {

    private SmsProperties.SmsMessage smsMessage;

    public TencentSmsSenderImpl(SmsProperties.SmsMessage smsProperties) {
        this.smsMessage = smsProperties;
    }

    @Override
    public boolean send(String message) {
        System.out.println(smsMessage.toString()+" send message: "+message);
        return true;
    }
}