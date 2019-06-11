package com.aliware.tianchi;

import com.aliware.tianchi.HostInfo.HostMap;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.qos.server.handler.HttpProcessHandler;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author daofeng.xjf
 * <p>
 * 负载均衡扩展接口
 * 必选接口，核心接口
 * 此类可以修改实现，不可以移动类或者修改包名
 * 选手需要基于此类实现自己的负载均衡算法
 */
public class UserLoadBalance implements LoadBalance {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoadBalance.class);
    private List<Invoker> invokerList;
    private AtomicInteger counter = new AtomicInteger(0);
    private HostMap hostMap = new HostMap();

    @Override
    public <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException {

        //Invoker invoker = invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));
        //LOGGER.info("the first invoker url is {}", invoker.getUrl());
        if (invokerList == null) {
            synchronized (UserLoadBalance.class) {
                if (invokerList == null) {
                    setInvokerList(invokers);
                }
            }
        }
        if (hostMap.getSize() != 0) {
            int count = counter.getAndIncrement();
            if (count == invokerList.size()) {
                counter.set(0);
            }
            return invokerList.get(count);
        } else {
            Invoker invoker = invokers.get(ThreadLocalRandom.current().nextInt(invokers.size()));
            return invoker;
        }

    }

    private void setInvokerList(List<?> invokers) {
        for (Invoker invoker : (List<Invoker>) invokers) {
            int count = hostMap.getHostInfo(invoker.getUrl().getPath());
            for (int i = 0; i < count; i++) {
                invokerList.add(invoker);
            }
        }
    }
}
