package oit.is.z3065.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3065.kaizi.janken.model.Janken;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @GetMapping
  public String janken() {
    return "janken.html";
  }

  @PostMapping
  public String janken_name(@RequestParam String ID, ModelMap model) {
    model.addAttribute("ID", ID);
    return "janken.html";
  }

  @PostMapping("gu")
  public String
}
