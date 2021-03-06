package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.ClassificacioDao;
import es.uji.ei1027.clubesportiu.dao.NadadorDao;
import es.uji.ei1027.clubesportiu.model.Classificacio;
import es.uji.ei1027.clubesportiu.model.Nadador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/classificacio")

public class ClassificacioController {
    private ClassificacioDao classificacioDao;
    @Autowired
    public void setClassificacioDao(ClassificacioDao classificacioDao) {
        this.classificacioDao=classificacioDao;
    }

    @RequestMapping("/list")
    public String listClassificacio(Model model) {
        model.addAttribute("classificacions", classificacioDao.getClassificacions());
        return "classificacio/list";
    }
    //-------------------------------------------
    @RequestMapping(value="/add")
    public String addClassificacio(Model model) {
        model.addAttribute("classificacio", new Classificacio());
        return "classificacio/add";
    }
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("classificacio") Classificacio classificacio,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "classificacio/add";
        classificacioDao.addClassificacio(classificacio);
        return "redirect:list";
    }
    //-----------------------------------------------
    @RequestMapping(value="/update/{nom}/{prova}", method = RequestMethod.GET)
    public String editNadador(Model model, @PathVariable String nom,@PathVariable String prova) {
        model.addAttribute("classificacio", classificacioDao.getClassificacio(nom,prova));
        return "classificacio/update";
    }
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("nadador") Classificacio classificacio,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "nadador/update";
        classificacioDao.updateClassificacio(classificacio);
        return "redirect:list";
    }

    @RequestMapping(value = "/delete/{nNadador}/{nProva}")
    public String processDeleteClassif(@PathVariable String nNadador,
                                       @PathVariable String nProva) {
        classificacioDao.deleteClassificacio(nNadador, nProva);
        return "redirect:../../list";
    }


}
