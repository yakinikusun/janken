package oit.is.z3065.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3065.kaizi.janken.model.Janken;

import java.util.Random;

@Controller
@RequestMapping("/janken")
public class JankenController {

  Janken janken = new Janken();

  @GetMapping
  public String janken() {
    return "janken.html";
  }

  @PostMapping
  public String janken_name(@RequestParam String ID, ModelMap model) {
    janken.set_id(ID);
    model.addAttribute("ID", janken.getID());
    return "janken.html";
  }

  @PostMapping("gu")
  public String guu(ModelMap model) {
    if (!janken.getID().isEmpty())
      model.addAttribute("ID", janken.getID());
    janken.buttle("グー", "グー");
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  @PostMapping("tyo")
  public String tyoki(ModelMap model) {
    if (!janken.getID().isEmpty())
      model.addAttribute("ID", janken.getID());
    janken.buttle("チョキ", "グー");
    model.addAttribute("janken", janken);
    return "janken.html";
  }

  @PostMapping("pa")
  public String paa(ModelMap model) {
    if (!janken.getID().isEmpty())
      model.addAttribute("ID", janken.getID());
    janken.buttle("パー", "グー");
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  public String 
}
