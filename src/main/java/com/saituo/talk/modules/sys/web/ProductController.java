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
import com.saituo.talk.modules.sys.service.ProductBrandService;
import com.saituo.talk.modules.sys.service.ProductInfoWriter;
import com.saituo.talk.modules.sys.service.ProductService;

@Controller
@RequestMapping(value = "${adminPath}/order/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductInfoWriter productInfoWriter;

	@Autowired
	private ProductBrandService productBrandService;

	@ModelAttribute("product")
	public Product get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			return productService.get(Integer.valueOf(id));
		} else {
			return new Product();
		}
	}

	@RequiresPermissions("order:product:view")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, HttpServletRequest request, HttpServletResponse response, Model model) {

		model.addAttribute("productBrandMap", productBrandService.getProductBrandMap());
		Page<Product> page = productService.findAllProduct(new Page<Product>(request, response), product);
		model.addAttribute("page", page);
		return "modules/order/product/productList";
	}

	@RequiresPermissions("order:product:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		model.addAttribute("product", product);
		model.addAttribute("productBrandMap", productBrandService.getProductBrandMap());
		return "modules/order/product/productForm";
	}

	@RequiresPermissions("order:product:edit")
	@RequestMapping(value = "save")
	public String save(Product product, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, product)) {
			return form(product, model);
		}

		if (productService.count(product) != 0) {
			addMessage(model, "该产品,'" + product.getProductName() + "的货号与品牌已经存在!");
			return form(product, model);
		}

		BeanValidators.validateWithException(validator, product);

		productService.save(product);
		addMessage(redirectAttributes, "保存产品'" + product.getProductName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/order/product/";
	}

	@RequiresPermissions("order:product:edit")
	@RequestMapping(value = "delete")
	public String delete(String id, RedirectAttributes redirectAttributes) {
		productService.delete(Integer.valueOf(id));
		addMessage(redirectAttributes, "删除产品成功");
		return "redirect:" + Global.getAdminPath() + "/order/product/";
	}

	@RequiresPermissions("order:product:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(Product product, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "产品数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<Product> page = productService.findAll();
			new ExportExcel("", Product.class).setDataList(page).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/product/?repage";
	}

	@RequiresPermissions("order:product:edit")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {

		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 0, 0);
			List<Product> list = ei.getDataList(Product.class);
			for (Product product : list) {
				try {
					if (product.getBrand() == null) {
						failureMsg.append("<br/>产品名 " + product.getProductName() + " 的品牌不存在; ");
						failureNum++;
						continue;
					}
					if (productService.count(product) != 0) {
						failureMsg.append("<br/>产品名 " + product.getProductName() + " 的货号与品牌已存在; ");
						failureNum++;
						continue;
					}

					BeanValidators.validateWithException(validator, product);
					productService.save(product);
					successNum++;

				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>产品 " + product.getProductName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>产品 " + product.getProductName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条产品，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条产品" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入产品失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/product/?repage";
	}

	@RequiresPermissions("order:product:view")
	@RequestMapping("import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "产品数据导入模板.xlsx";
			List<Product> list = Lists.newArrayList();
			list.add(productService.get(1));
			new ExportExcel("", Product.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/order/product/?repage";
	}

	@RequiresPermissions("order:product:edit")
	@RequestMapping(value = "create/index")
	public String createIndex(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		productInfoWriter.createIndex();
		addMessage(redirectAttributes, "产品索引已重建完毕!");
		return "redirect:" + Global.getAdminPath() + "/order/product/";
	}

	@RequiresPermissions("order:product:edit")
	@RequestMapping(value = "truncate")
	public String truncateProduct(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		productService.truncate();
		return "redirect:" + Global.getAdminPath() + "/order/product/";
	}
}
