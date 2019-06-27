package org.launchcode.controllers;



import org.launchcode.models.Menu;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.launchcode.models.data.MenuDao;
import org.launchcode.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "menu")
public class MenuController {
    @Autowired
    MenuDao menuDao;

    @Autowired
    CheeseDao cheeseDao;

    @Autowired
    CategoryDao categoryDao;

@RequestMapping(value = "")
public String index(Model model){

    model.addAttribute("menus", menuDao.findAll());
    model.addAttribute("title", "Categories");

    return "menu/index";
}

@RequestMapping(value = "add", method = RequestMethod.GET)
public String displayAddMenuForm(Model model) {

     model.addAttribute(new Menu());
     model.addAttribute("title", "Add Menu");

     return "menu/add";
}
@RequestMapping (value = "add", method = RequestMethod.POST)
public String add(Model model,@ModelAttribute @Valid Menu menu,Errors errors) {

    if (errors.hasErrors()) {
        return "menu/add";
    }

    menuDao.save(menu);
    return "redirect:/menu/view/" + menu.getId();
}

@RequestMapping(value = "view/{id}", method = RequestMethod.GET)
public String viewMenu(Model model,@PathVariable int id) {

    model.addAttribute("menu", menuDao.findOne(id));
    return "menu/view";
    }
@RequestMapping(value = "add-item/{menuId}", method = RequestMethod.GET)
public String addItem(Model model, @PathVariable int menuId, Menu menu, Errors errors){

    Menu menuToEdit = menuDao.findOne(menuId); // view this menu
    AddMenuItemForm newMenuItemForm = new AddMenuItemForm(menu, cheeseDao.findAll()); // all cheeses in menu
    String menuName = menuToEdit.getName();

    model.addAttribute("menu", menuToEdit);
    model.addAttribute("form", newMenuItemForm);
    model.addAttribute("title", "Add item to menu: " + menu.getName());

    return "menu/add-item";
    }

    }



