package org.waddys.xcloud.monitor.serviceImpl.service;

import java.util.List;

import org.waddys.xcloud.monitor.bo.AlarmEntity;
import org.waddys.xcloud.monitor.serviceImpl.entity.Cluster;
import org.waddys.xcloud.monitor.serviceImpl.entity.DataCenter;
import org.waddys.xcloud.monitor.serviceImpl.entity.Host;
import org.waddys.xcloud.monitor.serviceImpl.entity.VM;


public interface ResourceServiceI {

    // 获取虚拟机基本信息
    VM getVmInfo(String name);

    VM getVmInfoEx(String vmId);

    // 获取物理机基本信息
    Host getHostInfo(String name);
    
    Host getHostInfoEx(String hostId);

    Host getHostBasicInfo(String name);

    // 获取集群基本信息
    Cluster getClusterInfo(String name);

    // 获取集群列表
    List<Cluster> getClustersList();

    // 返回只包含名称属性的群集列表
    List<Cluster> getSimpleClusterList();

    // 获取数据中心信息
    DataCenter getDataCenterInfo();
    
    List<AlarmEntity> getClusterAlarm(Cluster cu);

    // 以群集对象的形式获取所有存储信息
    Cluster getAllDataStoreInfo();
}
