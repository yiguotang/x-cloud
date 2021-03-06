package org.waddys.xcloud.res.serviceImpl.impl.venv;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.waddys.xcloud.res.bo.venv.Cluster;
import org.waddys.xcloud.res.bo.venv.Host;
import org.waddys.xcloud.res.bo.venv.ResourcePool;
import org.waddys.xcloud.res.bo.venv.VenvConfig;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.po.dao.venv.ClusterDaoService;
import org.waddys.xcloud.res.po.dao.venv.VenvConfigDaoService;
import org.waddys.xcloud.res.po.entity.venv.CloudvmStoragePoolE;
import org.waddys.xcloud.res.po.entity.venv.ClusterE;
import org.waddys.xcloud.res.po.entity.venv.HostE;
import org.waddys.xcloud.res.po.entity.venv.ResourcePoolE;
import org.waddys.xcloud.res.po.entity.venv.VenvConfigE;
import org.waddys.xcloud.res.service.service.venv.ClusterService;
import org.waddys.xcloud.res.service.service.venv.HostService;
import org.waddys.xcloud.res.service.service.venv.ResourcePoolService;
import org.waddys.xcloud.res.serviceImpl.utils.CommonUtils;

@Component("clusterServiceImpl")
public class ClusterServiceImpl implements ClusterService {

    @Autowired
    private ClusterDaoService clusterDaoService;
    @Autowired
    private VenvConfigDaoService venvConfigDaoService;
    @Autowired
    private HostService hostService;
    @Autowired
    private ResourcePoolService resourcePoolService;
    private static Logger logger = LoggerFactory
            .getLogger(VenvConfigServiceImpl.class);

    @Override
    public Cluster addCluster(Cluster cluster, String venvConfigId)
            throws VenvException {
        logger.debug("stetp into method addCluster(),params1:" + cluster
                + "param2:" + venvConfigId);
        if (cluster != null && venvConfigId != "") {
            VenvConfigE venvConfigE = venvConfigDaoService
                    .findVenvConfigById(venvConfigId);
            ClusterE clusterE = new ClusterE();
            CommonUtils.converterMethod(clusterE, cluster);
            clusterE.setConfigInfo(venvConfigE);
            CloudvmStoragePoolE cloudvmStoragePoolE = new CloudvmStoragePoolE();
            cloudvmStoragePoolE.setName("存储池aa");
            cloudvmStoragePoolE.setSpAvlCapacity(150);
            cloudvmStoragePoolE.setSpTotalCapacity(200);
            cloudvmStoragePoolE.setSpUsedCapacity(50);
            cloudvmStoragePoolE.setSynDate(new Date());
            cloudvmStoragePoolE.setConfigInfo(venvConfigE);
            HashSet<CloudvmStoragePoolE> hashSet = new HashSet<CloudvmStoragePoolE>();
            hashSet.add(cloudvmStoragePoolE);
            clusterE.setCloudvmStoragePoolE(hashSet);
            ClusterE clusterE1 = clusterDaoService.addCluster(clusterE);

            Host host = new Host();
            host.setHostname("主机1");
            host.setSynDate(new Date());
            hostService.addHost(host, venvConfigId, clusterE1.getClusterId());

            ResourcePool resourcePool = new ResourcePool();
            resourcePool.setName("资源池1");
            resourcePool.setCpuTotCapacity(300.0);
            resourcePool.setCpuUsedCapacity(250.0);
            resourcePool.setCpuAvlCapacity(50.0);
            resourcePool.setMemoryTotCapacity(100);
            resourcePool.setMemoryUsedCapacity(50);
            resourcePool.setMemoryAvlCapacity(50);
            resourcePool.setSynDate(new Date());
            resourcePoolService.addResourcePool(resourcePool, venvConfigId,
                    clusterE1.getClusterId());
        }
        return null;
    }

    @Override
    public Cluster findByclusterId(String clusterId) throws VenvException {
        ClusterE clusterE = clusterDaoService.findByclusterId(clusterId);
        List<HostE> hostSet = clusterE.getHost();
        List<ResourcePoolE> resourcePoolSet = clusterE.getResourcePool();
        System.out.println(hostSet.size());
        System.out.println(resourcePoolSet.size());
        Cluster cluster = new Cluster();
        if (clusterE != null) {
            VenvConfigE venvConfigE = clusterE.getConfigInfo();
            if (venvConfigE != null) {
                VenvConfig venvConfig = new VenvConfig();
                venvConfig.setConfigId(venvConfigE.getConfigId());
                venvConfig.setConfigName(venvConfigE.getConfigName());
                venvConfig.setUserName(venvConfigE.getUserName());
                venvConfig.setPassword(venvConfigE.getPassword());
                venvConfig.setCreateDate(venvConfigE.getCreateDate());
                cluster.setConfigInfo(venvConfig);
            }
            cluster.setClusterId(clusterE.getClusterId());
            cluster.setName(clusterE.getName());
            cluster.setCpuTotCapacity(clusterE.getCpuTotCapacity());
            cluster.setCpuUsedCapacity(clusterE.getCpuUsedCapacity());
            cluster.setCpuAvlCapacity(clusterE.getCpuAvlCapacity());
            cluster.setMemoryAvlCapacity(clusterE.getMemoryAvlCapacity());
            cluster.setMemoryTotCapacity(clusterE.getMemoryTotCapacity());
            cluster.setMemoryUsedCapacity(clusterE.getMemoryUsedCapacity());
            cluster.setSynDate(clusterE.getSynDate());
        }
        return cluster;
    }

    @Override
    public Iterable<Cluster> findAll() throws VenvException {
        Iterator<ClusterE> it = clusterDaoService.findAll().iterator();
        while (it.hasNext()) {
            ClusterE clusterE = it.next();
            List<HostE> hostEs = clusterE.getHost();
            for (HostE hostE : hostEs) {
                System.out.println(hostE.getHostId());
            }
            List<ResourcePoolE> resourcePoolEs = clusterE.getResourcePool();
            for (ResourcePoolE resourcePoolE : resourcePoolEs) {
                System.out.println(resourcePoolE.getRpId());
            }

        }

        return null;
    }

    @Override
    public List<Cluster> findByName(String name) {
        @SuppressWarnings("unused")
        List<ClusterE> clusterEs = clusterDaoService.findByName(name);
        return null;
    }

}
