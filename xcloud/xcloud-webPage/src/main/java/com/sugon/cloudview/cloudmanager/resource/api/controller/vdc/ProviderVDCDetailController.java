package com.sugon.cloudview.cloudmanager.resource.api.controller.vdc;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProvideVDCStoragePool;
import com.sugon.cloudview.cloudmanager.resource.service.bo.vdc.ProviderVDC;
import com.sugon.cloudview.cloudmanager.resource.service.exception.vdc.VDCException;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ComputingPoolService;
import com.sugon.cloudview.cloudmanager.resource.service.service.vdc.ProviderVDCService;
import com.sugon.cloudview.cloudmanager.util.JsonUtil;
import com.sugon.cloudview.cloudmanager.util.PageUtil;
import com.sugon.cloudview.cloudmanager.vm.bo.VmHost;
import com.sugon.cloudview.cloudmanager.vm.service.VmService;

@Controller
@RequestMapping("/proVDCDetail")
public class ProviderVDCDetailController {

    @Autowired
    private ProviderVDCService providerVDCService;
    @Autowired
    private ComputingPoolService computingPoolService;
    @Autowired
    private VmService vmService;

    private static Logger logger = LoggerFactory
            .getLogger(ProviderVDCDetailController.class);

    @RequestMapping(value = "/queryPorVDCVmTable", method = RequestMethod.POST)
    public @ResponseBody String queryPorVDCTable(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", required = false) int page,
            @RequestParam(value = "rows", required = false) int rows) {
        VmHost vmHost = new VmHost();
        if (null != name) {
            vmHost.setName(name);
        }
        JSONObject json = new JSONObject();
        try {
            PageRequest pageRequest = PageUtil.buildPageRequest(page, rows);
            vmHost.setStatus("A");
            Page<VmHost> vmList = vmService.pageByVDC(pVDCId, vmHost,
                    pageRequest);
            String content = JsonUtil.toJson(vmList);
            json.put("total",
                    JSONObject.fromObject(content).get("totalElements"));
            json.put("rows", JSONObject.fromObject(content).get("content"));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return json + "";
    }

    @RequestMapping(value = "/proVDCDetailSummary", method = RequestMethod.POST)
    public String proVDCDetailSummary(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            ModelMap model) {
        ProviderVDC providerVDC = new ProviderVDC();
        try {
            providerVDC = providerVDCService.findProviderVDC(pVDCId);
            List<ProvideVDCStoragePool> proVDCSpList = providerVDCService
                    .findStoragePools(pVDCId);
            model.put("proVDCInfo", JSONObject.fromObject(providerVDC));
            model.put("proVDCSpList", JSONArray.fromObject(proVDCSpList));
        } catch (VDCException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return "provdc/proVDCDetailSummary";
    }

    @RequestMapping(value = "/proVDCDetailRelevantObjs", method = RequestMethod.POST)
    public String proVDCDetailRelevantObjs(
            @RequestParam(value = "pVDCId", required = false) String pVDCId,
            ModelMap model) {
        model.put("pVDCId", pVDCId);
        return "provdc/proVDCDetailRelevantObjs";
    }
}
