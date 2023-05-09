package com.rvalencia.contador.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class CountController {

	private void setContadorSesion(HttpSession sesion, int numeroVeces) {
		sesion.setAttribute("contador", numeroVeces);
//		sesion.setAttribute("contador2", 1000);
	}

	private int getContadorSesion(HttpSession sesion) {
		Object sesionVal = sesion.getAttribute("contador");
		if (sesionVal == null) {
			setContadorSesion(sesion, 0);
			sesionVal = sesion.getAttribute("contador");
		}
		return (Integer) sesionVal;
	}

	@RequestMapping("/")
	public String index(HttpSession sesion) {
		int conteoActual = getContadorSesion(sesion);
		setContadorSesion(sesion, conteoActual + 1);

		return "index.jsp";
	}

	@RequestMapping("/contador")
	public String Contador(HttpSession sesion, Model modelo) {
		modelo.addAttribute("contador", getContadorSesion(sesion));
		return "counter.jsp";
	}

	@RequestMapping("/reset")
	public String reset(HttpSession sesion) {
		sesion.invalidate();
		return "redirect:/contador";
//		return "counter.jsp";

	}

	@RequestMapping("/contador2")
	public String contador2(HttpSession s) {
		if (s.getAttribute("contador") == null) {
			s.setAttribute("contador", 0);
		}
		Integer valorActual = (Integer) s.getAttribute("contador");
		s.setAttribute("contador", valorActual + 2);

		return "counter2.jsp";
	}

	@RequestMapping("/agregar/{veces}")
	public String agregar(@PathVariable("veces") String veces, HttpSession s) {
		int temporal = 1;
		try {
			temporal = Integer.parseInt(veces);
		} catch (NumberFormatException e) {
			System.out.println(String.format("Aqui error %s", e.getMessage()));
			return "redirect:/contador";
		}
		int valorActual = getContadorSesion(s);
		valorActual += temporal;
		setContadorSesion(s, valorActual);
		return "redirect:/contador";
	}

}
