package com.saituo.talk.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.saituo.talk.common.beanvalidator.BeanValidators;
import com.saituo.talk.common.config.Global;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.DateUtils;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.common.utils.excel.ExportExcel;
import com.saituo.talk.common.utils.excel.ImportExcel;
import com.saituo.talk.common.web.BaseController;
import com.saituo.talk.modules.sys.entity.Agency;
import com.saituo.talk.modules.sys.entity.ProductBrand;
import com.saituo.talk.modules.sys.service.AgencyService;

@Controller
@RequestMapping(value = "${adminPath}/order/agent")
public class AgencyController extends BaseController {

	@Autowired
	private AgencyService agencyService;

	@ModelAttribute("agency")
	public Agency get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return agencyService.get(Integer.valueOf(id));
		} else {
			return new Agency();
		}
	}

	@RequiresPermissions("order:agent:view")
	@RequestMapping(value = {"list", ""})
	public String list(Agency agency, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<Agency> page = agencyService.findAllAgency(new Page<Agency>(request, response), agency);
		model.addAttribute("page", page);
		return "modules/order/agent/AgentList";
	}

	@RequiresPermissions("order:agent:view")
	@RequestMapping(value = "form")
	public String form(Agency agency, Model model) {
		model.addAttribute("agency", agency);
		return "modules/order/agent/AgentForm";
	}

	@RequiresPermissions("order:agent:edit")
	@RequestMapping(value = "save")
	public String save(Agency agency, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, agency)) {
			return form(agency, model);
		}
		agencyService.save(agency);
		addMessage(redirectAttributes, "保存品牌'" + agency.getAgentName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/order/agent/";
	}

	@RequiresPermissions("order:agent:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		agencyService.delete(Integer.valueOf(id));
		addMessage(redirectAttributes, "删除品牌成功");
		return "redirect:" + Global.getAdminPath() + "/order/agent/";
	}

	@RequiresPermissions("order:agent:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Agency agency, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<Agency> page = agencyService.findAll();
			new ExportExcel("数据", ProductBrand.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/agent/?repage";
	}

	@RequiresPermissions("order:agent:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Agency> list = ei.getDataList(Agency.class);
			for (Agency agency : list) {
				try {
					if (agencyService.count(agency) == 0) {
						BeanValidators.validateWithException(validator, agency);
						agencyService.save(agency);
						successNum++;
					} else {
						failureMsg.append("<br/>经销商名 " + agency.getAgentName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>经销商名 " + agency.getAgentName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>经销商名 " + agency.getAgentName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/agent/?repage";
	}

	@RequiresPermissions("order:agent:view")
	@RequestMapping("import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "数据导入模板.xlsx";
			List<Agency> list = Lists.newArrayList();
			list.add(agencyService.get(1));
			new ExportExcel("经销商数据", Agency.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/agent/?repage";
	}
}
