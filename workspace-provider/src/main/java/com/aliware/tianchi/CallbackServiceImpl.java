package com.aliware.tianchi;

import org.apache.dubbo.rpc.listener.CallbackListener;
import org.apache.dubbo.rpc.service.CallbackService;

import java.net.InetAddress;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author daofeng.xjf
 * <p>
 * 服务端回调服务
 * 可选接口
 * 用户可以基于此服务，实现服务端向客户端动态推送的功能
 */
public class CallbackServiceImpl implements CallbackService {

    public CallbackServiceImpl() {
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                if (!listeners.isEmpty()) {
//                    for (Map.Entry<String, CallbackListener> entry : listeners.entrySet()) {
//                        try {
//                            int coreNum = Runtime.getRuntime().availableProcessors();
//                            String ip = InetAddress.getLocalHost().getHostAddress();
//                            entry.getValue().receiveServerMsg(coreNum + ";" + ip );
//                        } catch (Throwable t1) {
//                            listeners.remove(entry.getKey());
//                        }
//                    }
//                }
//            }
//        }, 5000, 5000);
        // only push the notice once
        if (!listeners.isEmpty()) {
            for (Map.Entry<String, CallbackListener> entry : listeners.entrySet()) {
                try {
                    int coreNum = Runtime.getRuntime().availableProcessors();
                    String ip = InetAddress.getLocalHost().getHostAddress();
                    entry.getValue().receiveServerMsg(coreNum + ";" + ip);
                } catch (Throwable t1) {
                    listeners.remove(entry.getKey());
                }
            }
        }
    }

    //private Timer timer = new Timer();

    /**
     * key: listener type
     * value: callback listener
     */
    private final Map<String, CallbackListener> listeners = new ConcurrentHashMap<>();

    @Override
    public void addListener(String key, CallbackListener listener) {
        listeners.put(key, listener);
        //初始时候没有通知
        //listener.receiveServerMsg(new Date().toString()); // send notification for change
    }
}
