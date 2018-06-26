package com.xsx.ncd.ncd_manager.Tools;

import android.content.Context;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.net.LinkAddress;
import android.net.StaticIpConfiguration;

import com.xsx.ncd.ncd_manager.Defines.EthernetConfig;

import java.lang.reflect.Constructor;
import java.net.Inet4Address;
import java.net.InetAddress;

import static com.xsx.ncd.ncd_manager.Tools.NetUtils.getIPv4Address;


public class EthernetSet {

    public static void setSystemEthernet(Context context, EthernetConfig ethernetConfig) throws Exception {

        EthernetManager mEthManager = (EthernetManager)context.getSystemService("ethernet");
        IpConfiguration mIpConfiguration;
        StaticIpConfiguration mStaticIpConfiguration = new StaticIpConfiguration();
        /*
        * get ip address, netmask,dns ,gw etc.
        */
        Inet4Address inetAddr = getIPv4Address(ethernetConfig.getIpv4());
        int prefixLength = NetUtils.maskStr2InetMask(ethernetConfig.getNetmask());
        InetAddress gatewayAddr = getIPv4Address(ethernetConfig.getGateway());
        InetAddress dnsAddr = getIPv4Address(ethernetConfig.getDns());

        if (inetAddr==null || prefixLength == 0 || gatewayAddr == null || dnsAddr == null) {
            return;
        }

        Class<?> clazz = null;

        clazz = Class.forName("android.net.LinkAddress");


        Class[] cl = new Class[]{InetAddress.class, int.class};
        Constructor cons = null;
        //取得所有构造函数
        cons = clazz.getConstructor(cl);

        //给传入参数赋初值
        Object[] x = {inetAddr, prefixLength};

        //mStaticIpConfiguration.ipAddress = new LinkAddress(inetAddr, prefixLength);
        mStaticIpConfiguration.ipAddress = (LinkAddress) cons.newInstance(x);

        mStaticIpConfiguration.gateway = gatewayAddr;
        mStaticIpConfiguration.dnsServers.add(dnsAddr);

        mIpConfiguration = new IpConfiguration(IpConfiguration.IpAssignment.STATIC, IpConfiguration.ProxySettings.NONE, mStaticIpConfiguration, null);
        mEthManager.setConfiguration(mIpConfiguration);
    }
}
