package com.mraft.common.util;

/**
 * Created by wenan.mr on 2017/11/26.
 *
 * @author wenan.mr
 * @date 2017/11/26
 */
public class IpWrapper {

    private static final String SPLIT = ":";


    private String ip;

    private int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public IpWrapper(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public static IpWrapper convert(String ip){
        String[] array = ip.split(SPLIT);
        IpWrapper ipWapper = new IpWrapper(array[0],Integer.parseInt(array[1]));
        return ipWapper;
    }

    @Override
    public String toString() {
        return "IpWrapper{" +
            "ip='" + ip + '\'' +
            ", port=" + port +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        IpWrapper ipWrapper = (IpWrapper)o;

        if (port != ipWrapper.port) { return false; }
        return ip != null ? ip.equals(ipWrapper.ip) : ipWrapper.ip == null;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }
}
