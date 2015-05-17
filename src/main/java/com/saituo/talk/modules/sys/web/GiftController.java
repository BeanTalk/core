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
import com.saituo.talk.modules.sys.entity.Gift;
import com.saituo.talk.modules.sys.entity.Product;
import com.saituo.talk.modules.sys.service.GiftService;

@Controller
@RequestMapping(value = "${adminPath}/order/gift")
public class GiftController extends BaseController {

	@Autowired
	private GiftService giftService;

	@ModelAttribute("gift")
	public Gift get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return giftService.get(Integer.valueOf(id));
		} else {
			return new Gift();
		}
	}

	@RequiresPermissions("order:gift:view")
	@RequestMapping(value = {"list", ""})
	public String list(Gift gift, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gift> page = giftService.find(new Page<Gift>(request, response), gift);
		model.addAttribute("page", page);
		return "modules/order/gift/giftList";
	}

	@RequiresPermissions("order:gift:view")
	@RequestMapping(value = "form")
	public String form(Gift gift, Model model) {
		model.addAttribute("gift", gift);
		return "modules/order/gift/giftForm";
	}

	@RequiresPermissions("order:gift:edit")
	@RequestMapping(value = "save")
	public String save(Gift gift, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gift)) {
			return form(gift, model);
		}

		giftService.save(gift);
		addMessage(redirectAttributes, "保存产品'" + gift.getGiftName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/order/gift/";
	}

	@RequiresPermissions("order:gift:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		giftService.delete(Integer.valueOf(id));
		addMessage(redirectAttributes, "删除礼品成功");
		return "redirect:" + Global.getAdminPath() + "/order/gift/";
	}

	@RequiresPermissions("order:gift:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Product product, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "礼品数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<Gift> page = giftService.findAll();
			new ExportExcel("礼品数据", Gift.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/gift/?repage";
	}

	@RequiresPermissions("order:gift:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Gift> list = ei.getDataList(Gift.class);
			for (Gift gift : list) {
				try {
					if (giftService.count(gift) == 0) {
						BeanValidators.validateWithException(validator, gift);
						giftService.save(gift);
						successNum++;
					} else {
						failureMsg.append("<br/>礼品名 " + gift.getGiftName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>礼品 " + gift.getGiftName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>礼品 " + gift.getGiftName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条礼品" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入礼品失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/product/?repage";
	}

	@RequiresPermissions("order:gift:view")
	@RequestMapping("import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "礼品数据导入模板.xlsx";
			List<Gift> list = Lists.newArrayList();
			list.add(giftService.get(1));
			new ExportExcel("产品数据", Product.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/product/?repage";
	}

}
