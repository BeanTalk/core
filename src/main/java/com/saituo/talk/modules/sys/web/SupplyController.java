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
import com.saituo.talk.modules.sys.entity.Supply;
import com.saituo.talk.modules.sys.service.SupplyService;

@Controller
@RequestMapping(value = "${adminPath}/order/supply")
public class SupplyController extends BaseController {

	@Autowired
	private SupplyService supplyService;

	@ModelAttribute("supply")
	public Supply get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return supplyService.get(Integer.valueOf(id));
		} else {
			return new Supply();
		}
	}

	@RequiresPermissions("order:supply:view")
	@RequestMapping(value = {"list", ""})
	public String list(Supply supply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supply> page = supplyService.findAllSupply(new Page<Supply>(request, response), supply);
		model.addAttribute("page", page);
		return "modules/order/supply/SupplyList";
	}

	@RequiresPermissions("order:supply:view")
	@RequestMapping(value = "form")
	public String form(Supply supply, Model model) {
		model.addAttribute("supply", supply);
		return "modules/order/supply/SupplyForm";
	}

	@RequiresPermissions("order:supply:edit")
	@RequestMapping(value = "save")
	public String save(Supply supply, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, supply)) {
			return form(supply, model);
		}
		supplyService.save(supply);
		addMessage(redirectAttributes, "保存供应商'" + supply.getSupplyName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/order/supply/";
	}

	@RequiresPermissions("order:supply:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		supplyService.delete(Integer.valueOf(id));
		addMessage(redirectAttributes, "删除供应商成功");
		return "redirect:" + Global.getAdminPath() + "/order/supply/";
	}

	@RequiresPermissions("order:supply:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Agency agency, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<Supply> page = supplyService.findAll();
			new ExportExcel("", ProductBrand.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/supply/?repage";
	}

	@RequiresPermissions("order:supply:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Supply> list = ei.getDataList(Supply.class);
			for (Supply supply : list) {
				try {
					if (supplyService.count(supply) == 0) {
						BeanValidators.validateWithException(validator, supply);
						supplyService.save(supply);
						successNum++;
					} else {
						failureMsg.append("<br/>供应商名 " + supply.getSupplyName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>供应商名 " + supply.getSupplyName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>供应商名 " + supply.getSupplyName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/supply/?repage";
	}

	@RequiresPermissions("order:supply:view")
	@RequestMapping("import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "数据导入模板.xlsx";
			List<Supply> list = Lists.newArrayList();
			list.add(supplyService.get(1));
			new ExportExcel("", Agency.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/supply/?repage";
	}
}
