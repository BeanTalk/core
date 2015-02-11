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
import com.saituo.talk.modules.sys.entity.Product;
import com.saituo.talk.modules.sys.entity.ProductBrand;
import com.saituo.talk.modules.sys.service.ProductBrandService;
import com.saituo.talk.modules.sys.service.ProductService;

@Controller
@RequestMapping(value = "${adminPath}/order/brand")
public class ProductBrandController extends BaseController {

	@Autowired
	private ProductBrandService productBrandService;

	@Autowired
	private ProductService productService;

	@ModelAttribute("productBrand")
	public ProductBrand get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productBrandService.get(Integer.valueOf(id));
		} else {
			return new ProductBrand();
		}
	}

	@RequiresPermissions("order:brand:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductBrand productBrand, HttpServletRequest request, HttpServletResponse response, Model model) {

		Page<ProductBrand> page = productBrandService.findAllProductBrand(new Page<ProductBrand>(request, response),
				productBrand);
		model.addAttribute("page", page);
		return "modules/order/brand/productBrandList";
	}

	@RequiresPermissions("order:brand:view")
	@RequestMapping(value = "form")
	public String form(ProductBrand productBrand, Model model) {
		model.addAttribute("productBrand", productBrand);
		return "modules/order/brand/productBrandForm";
	}

	@RequiresPermissions("order:brand:edit")
	@RequestMapping(value = "save")
	public String save(ProductBrand productBrand, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productBrand)) {
			return form(productBrand, model);
		}

		if (productBrandService.count(productBrand) != 0) {
			addMessage(model, "品牌编码名称 '" + productBrand.getUniqueBrandName() + "'重复!");
			return form(productBrand, model);
		} else {
			productBrandService.save(productBrand);
			addMessage(redirectAttributes, "保存品牌编码名称'" + productBrand.getUniqueBrandName() + "'成功");
		}

		return "redirect:" + Global.getAdminPath() + "/order/brand/";
	}

	@RequiresPermissions("order:brand:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {

		ProductBrand brand = new ProductBrand();
		brand.setId(Integer.valueOf(id));

		Product product = new Product();
		product.setBrand(brand);

		if (productService.count(product) != 0) {
			addMessage(redirectAttributes, "该品牌下存在产品信息");
		} else {
			productBrandService.delete(Integer.valueOf(id));
			addMessage(redirectAttributes, "删除品牌成功");
		}
		return "redirect:" + Global.getAdminPath() + "/order/brand/";
	}

	@RequiresPermissions("order:brand:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(ProductBrand productBrand, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "品牌数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<ProductBrand> page = productBrandService.findAll();
			new ExportExcel("", ProductBrand.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/brand/?repage";
	}

	@RequiresPermissions("order:brand:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<ProductBrand> list = ei.getDataList(ProductBrand.class);
			for (ProductBrand productBrand : list) {
				try {
					if (productBrandService.count(productBrand) == 0) {
						BeanValidators.validateWithException(validator, productBrand);
						productBrandService.save(productBrand);
						successNum++;
					} else {
						failureMsg.append("<br/>品牌编码名称, " + productBrand.getUniqueBrandName() + " 已存在; ");
						failureNum++;
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>品牌 " + productBrand.getUniqueBrandName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>品牌 " + productBrand.getUniqueBrandName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, ", 失败 " + failureNum + " 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/brand/?repage";
	}

	@RequiresPermissions("order:brand:view")
	@RequestMapping("import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "品牌数据导入模板.xlsx";
			List<ProductBrand> list = Lists.newArrayList();
			list.add(productBrandService.get(1));
			new ExportExcel("", ProductBrand.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/brand/?repage";
	}
}
