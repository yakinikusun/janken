package oit.is.z3065.kaizi.janken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import oit.is.z3065.kaizi.janken.model.Janken;
import oit.is.z3065.kaizi.janken.model.Entry;

import java.security.Principal;
import java.util.Random;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private Entry entry;

  Janken janken = new Janken();

  @GetMapping
  public String janken(Principal prin, ModelMap model) {
    String loginUser = prin.getName();

    entry.addUser(loginUser);
    model.addAttribute("room", entry);

    return "janken.html";
  }

  @PostMapping("gu")
  public String guu(Principal prin, ModelMap model) {

    janken.buttle("グー", CPU_hand());
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  @PostMapping("tyo")
  public String tyoki(Principal prin, ModelMap model) {
    String loginUser = prin.getName();

    entry.addUser(loginUser);
    model.addAttribute("room", entry);

    janken.buttle("チョキ", CPU_hand());
    model.addAttribute("janken", janken);
    return "janken.html";
  }

  @PostMapping("pa")
  public String paa(Principal prin, ModelMap model) {
    String loginUser = prin.getName();

    entry.addUser(loginUser);
    model.addAttribute("room", entry);

    janken.buttle("パー", CPU_hand());
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  public String CPU_hand() {
    Random random = new Random();
    int n = random.nextInt(3);
    if (n == 0) {
      return "グー";
    } else if (n == 1) {
      return "チョキ";
    }
    return "パー";
  }
}
