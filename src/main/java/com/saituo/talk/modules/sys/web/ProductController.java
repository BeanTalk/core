package com.saituo.talk.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.saituo.talk.common.config.Global;
import com.saituo.talk.common.persistence.Page;
import com.saituo.talk.common.utils.StringUtils;
import com.saituo.talk.common.web.BaseController;
import com.saituo.talk.modules.sys.entity.Product;
import com.saituo.talk.modules.sys.entity.ProductBrand;
import com.saituo.talk.modules.sys.service.ProductBrandService;
import com.saituo.talk.modules.sys.service.ProductService;

@Controller
@RequestMapping(value = "${adminPath}/order/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

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

		String brandId = request.getParameter("brand.id");
		if (StringUtils.isNotEmpty(brandId)) {
			product.setBrand(new ProductBrand(Integer.valueOf(brandId)));
		}

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
}
