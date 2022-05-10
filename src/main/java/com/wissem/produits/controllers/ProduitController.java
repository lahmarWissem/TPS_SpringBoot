package com.wissem.produits.controllers;

import com.wissem.produits.entities.Categorie;
import com.wissem.produits.service.CategorieService;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.wissem.produits.entities.Produit;
import com.wissem.produits.service.ProduitService;

@Controller
public class ProduitController {
	@Autowired
	ProduitService produitService;
	
	@Autowired
	CategorieService CategorieService;
	
	

	@RequestMapping("/showCreate")
	public String showCreate(ModelMap modelMap) {
		List<Categorie> cats = produitService.getAllCategories();
		Produit prod = new Produit();
		Categorie cat = new Categorie();
		cat = cats.get(0); // prendre la première catégorie de la liste
		prod.setCategorie(cat); // affedter une catégorie par défaut au produit pour éviter le pb avec une
								// catégorie null
		modelMap.addAttribute("produit", prod);
		modelMap.addAttribute("mode", "new");
		modelMap.addAttribute("categories", cats);
		return "formProduit";
	}


	@RequestMapping("/saveProduit")
	public String saveProduit(@Valid Produit produit, BindingResult bindingResult)

	{
		if (bindingResult.hasErrors())
			return "formProduit";
		produitService.saveProduit(produit);
		
		return "formProduit";
	}

	// @RequestMapping("/showCreate")
		// public String showCreate() {
		// return "createProduit";
		// }

		// @RequestMapping("/saveProduit")
		// public String saveProduit(@ModelAttribute("produit") Produit produit)
		// {
		// produitService.saveProduit(produit);
		// return "createProduit";
		// }

		// @RequestMapping("/saveProduit")
		// public String saveProduit(@ModelAttribute("produit") Produit produit,
		// @RequestParam("date") String date,
		// ModelMap modelMap) throws ParseException {
		// conversion de la date
		// SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		// Date dateCreation = dateformat.parse(String.valueOf(date));
		// produit.setDateCreation(dateCreation);

		// Produit saveProduit = produitService.saveProduit(produit);
		// String msg = "produit enregistré avec Id " + saveProduit.getIdProduit();
		// modelMap.addAttribute("msg", msg);
		// return "createProduit";
		// }

		@RequestMapping("/ListeProduits")
		public String listeProduits(ModelMap modelMap, @RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "2") int size) {
			Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
			modelMap.addAttribute("produits", prods);
			modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			return "listeProduits";
		}

		@RequestMapping("/supprimerProduit")
		public String supprimerProduit(@RequestParam("id") Long id, ModelMap modelMap,
				@RequestParam(name = "page", defaultValue = "0") int page,
				@RequestParam(name = "size", defaultValue = "2") int size) {
			produitService.deleteProduitById(id);
			Page<Produit> prods = produitService.getAllProduitsParPage(page, size);
			modelMap.addAttribute("produits", prods);
			modelMap.addAttribute("pages", new int[prods.getTotalPages()]);
			modelMap.addAttribute("currentPage", page);
			modelMap.addAttribute("size", size);
			return "listeProduits";
		}

		@RequestMapping("/modifierProduit")
		public String editerProduit(@RequestParam("id") Long id, ModelMap modelMap) {
			Produit p = produitService.getProduit(id);
			modelMap.addAttribute("produit", p);
			modelMap.addAttribute("mode", "edit");
			return "formProduit";
		}

		@RequestMapping("/updateProduit")
		public String updateProduit(@ModelAttribute("produit") Produit produit, @RequestParam("date") String date,
				ModelMap modelMap) throws ParseException {

			// conversion de la date
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date dateCreation = dateformat.parse(String.valueOf(date));
			produit.setDateCreation(dateCreation);

			produitService.updateProduit(produit);
			List<Produit> prods = produitService.getAllProduits();
			modelMap.addAttribute("produits", prods);

			return "listeProduits";
		}
		
		@RequestMapping("/showCreateCategorie")
		public String showCreateCat(ModelMap modelMap)
		{
		modelMap.addAttribute("categories", new Categorie());
		modelMap.addAttribute("mode", "new");
		return "formCategorie";
		}
		
		@RequestMapping("/ListeCategorie")
		public String listeProduits(ModelMap modelMap)
		{
		List <Categorie> cats = produitService.getAllCategories();
		modelMap.addAttribute("categories", cats);
		return "ListeCategorie";
		}
		
		@RequestMapping("/supprimerCategorie")
		public String supprimerCategorie(@RequestParam("id") Long id,
		 ModelMap modelMap)
		{ 
			CategorieService.deleteCategorieById(id);
		List<Categorie> cats = CategorieService.findAll();
		modelMap.addAttribute("categories", cats);
		return "ListeCategorie";
		}
		
		@RequestMapping("/saveCategorie")
		public String saveCategorie(@ModelAttribute("categorie") Categorie categorie,ModelMap modelMap) throws ParseException 
		{
		Categorie saveCategorie = CategorieService.saveCategorie(categorie);
		return "redirect:/ListeCategorie";
		}
		
		@RequestMapping("/modifierCategorie")
		public String editerCategorie(@RequestParam("id") Long id,ModelMap modelMap)
		{
		Categorie c= CategorieService.getCategorie(id);
		modelMap.addAttribute("categories", c);
		modelMap.addAttribute("mode", "edit");
		return "formCategorie";
		}
		@RequestMapping("/updateCategorie")
		public String updateCategorie(@ModelAttribute("categorie") Categorie categorie,ModelMap modelMap) throws ParseException {
			CategorieService.updateCategorie(categorie);
			 List<Categorie> cats = CategorieService.findAll();
			 modelMap.addAttribute("categories", cats);
			return "ListeCat";
			}
	}
