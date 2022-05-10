package com.wissem.produits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wissem.produits.entities.Categorie;
import com.wissem.produits.repos.CategorieRepository;

@Service
public class CategorieServicelmpl implements CategorieService {

	@Autowired
	private CategorieRepository CategorieRepository;

	@Override
	public List<Categorie> findAll() {
		return CategorieRepository.findAll();
	}

	@Override
	public Categorie saveCategorie(Categorie c) {
		return CategorieRepository.save(c);
	}

	@Override
	public Categorie updateCategorie(Categorie c) {
		return CategorieRepository.save(c);
	}

	@Override
	public void deleteCategorie(Categorie c) {
		CategorieRepository.delete(c);
	}

	@Override
	public void deleteCategorieById(Long idCat) {
		CategorieRepository.deleteById(idCat);
	}

	@Override
	public Categorie getCategorie(Long idCat) {
		return CategorieRepository.findById(idCat).get();
	}

}