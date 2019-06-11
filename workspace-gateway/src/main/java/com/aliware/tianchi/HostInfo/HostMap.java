package com.aliware.tianchi.HostInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Maskwang on 2019/6/10.
 */
public class HostMap {

    private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>(4);

    public void saveHostInfo(String key, int value) {
        map.put(key, value);
    }

    public int getHostInfo(String key) {

        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            if (key.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return 0;
    }

    public int getSize() {
        return map.size();
    }

}
