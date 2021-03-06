package org.waddys.xcloud.res.serviceImpl.impl.vdc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.waddys.xcloud.res.bo.vdc.ConfigInfo;
import org.waddys.xcloud.res.exception.venv.VenvException;
import org.waddys.xcloud.res.po.dao.vdc.ConfigInfoDaoService;
import org.waddys.xcloud.res.po.dao.venv.ResourcePoolDaoService;
import org.waddys.xcloud.res.po.dao.vnet.NetPoolDaoService;
import org.waddys.xcloud.res.po.entity.vdc.ConfigInfoE;
import org.waddys.xcloud.res.service.service.vdc.ComputingPoolService;
import org.waddys.xcloud.res.service.service.vdc.ConfigInfoService;
import org.waddys.xcloud.res.service.service.vnet.NetPoolService;
import org.waddys.xcloud.res.serviceImpl.utils.CommonUtils;

@Service("configInfoServiceImpl")
public class ConfigInfoServiceImpl implements ConfigInfoService {

    @Autowired
    private ConfigInfoDaoService configInfoDaoService;
    @Autowired
    private NetPoolDaoService netPoolDaoService;
    @Autowired
    private NetPoolService netPoolService;
    @Autowired
    private ResourcePoolDaoService resourcePoolDaoService;
    @Autowired
    private ComputingPoolService computingPoolService;

    private static Logger logger = LoggerFactory.getLogger(ConfigInfoServiceImpl.class);

    @Override
    public ConfigInfo addConfigInfo(ConfigInfo configInfo) throws VenvException {

        logger.debug("step into method saveVenvConfig(),param:" + configInfo);
        try {
            if (configInfo != null) {
                ConfigInfoE configInfoE = new ConfigInfoE();
                CommonUtils.converterMethod(configInfoE, configInfo);
                ConfigInfoE configInfoAddE = configInfoDaoService.save(configInfoE);
                CommonUtils.converterMethod(configInfo, configInfoAddE);
                logger.debug("method return :" + configInfo);
                return configInfo;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("save ConfigInfo failure");
        }
        return null;
    }
    @Override
    public Page<ConfigInfo> findConfigInfos(Pageable pageable) throws VenvException {
        Page<ConfigInfoE> page = configInfoDaoService.findConfigInfos(pageable);
        Page<ConfigInfo> page2 = null;

        for (ConfigInfoE configInfoE : page) {
            ConfigInfo configInfo = new ConfigInfo();
            CommonUtils.converterMethod(configInfo, configInfoE);

        }
        return null;
    }

    @Override
    public List<ConfigInfo> findConfigInfos() {
        List<ConfigInfoE> configInfos = configInfoDaoService.findConfigInfos();
        List<ConfigInfo> configInfos1 = new ArrayList<ConfigInfo>();
        for (ConfigInfoE configInfoE : configInfos) {
            ConfigInfo configInfo = new ConfigInfo();
            CommonUtils.converterMethod(configInfo, configInfoE);
            configInfos1.add(configInfo);
        }
        
        return configInfos1;
    }

    @Override
    public ConfigInfo findByconfigId(String configId) {
        logger.debug("step into method findByconfigId(),param configId=" + configId);
        if (configId != null || configId != "") {
            ConfigInfoE configInfoE = configInfoDaoService.findByconfigId(configId);
            if (configInfoE != null) {
                ConfigInfo configInfo = new ConfigInfo();
                CommonUtils.converterMethod(configInfo, configInfoE);
                return configInfo;
            }
        }
        return null;
    }

    @Override
    public void updateConfigInfo(String configName, String configId) {
        configInfoDaoService.updateConfigInfo(configName, configId);

    }

    @Override
    public void updateConfigInfo(ConfigInfo configInfo) {
        ConfigInfoE configInfoE = new ConfigInfoE();
        CommonUtils.converterMethod(configInfoE, configInfo);
        configInfoDaoService.updateConfigInfo(configInfoE);

    }

    @Override
    public void delConfigInfo(ConfigInfo configInfo) {
        ConfigInfoE configInfoE = new ConfigInfoE();
        CommonUtils.converterMethod(configInfoE, configInfo);
        configInfoDaoService.delConfigInfo(configInfoE);

    }
}
