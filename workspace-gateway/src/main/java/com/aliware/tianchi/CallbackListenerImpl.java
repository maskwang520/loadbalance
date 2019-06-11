package com.aliware.tianchi;

import com.aliware.tianchi.HostInfo.HostMap;
import org.apache.dubbo.rpc.listener.CallbackListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author daofeng.xjf
 *
 * 客户端监听器
 * 可选接口
 * 用户可以基于获取获取服务端的推送信息，与 CallbackService 搭配使用
 *
 */
public class CallbackListenerImpl implements CallbackListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallbackListenerImpl.class);

    @Override
    public void receiveServerMsg(String msg) {
        HostMap hostMap = new HostMap();
        String[] hostInfo = msg.split(";");
        hostMap.saveHostInfo(hostInfo[0], Integer.valueOf(hostInfo[1]));
        LOGGER.info("hostMap's size is {}", hostMap.getSize());
    }

}
