package oit.is.z3065.kaizi.janken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    janken.buttle("グー", CPU_hand());
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  @PostMapping("tyo")
  public String tyoki(ModelMap model) {
    if (!janken.getID().isEmpty())
      model.addAttribute("ID", janken.getID());
    janken.buttle("チョキ", CPU_hand());
    model.addAttribute("janken", janken);
    return "janken.html";
  }

  @PostMapping("pa")
  public String paa(Principal prin, ModelMap model) {

    String loginUser = prin.getName();
    Entry newRoom = new Entry();
    newRoom.addUser(loginUser);
    model.addAttribute("new_room", newRoom);
    janken.buttle("パー", CPU_hand());
    model.addAttribute("janken", janken);

    return "janken.html";
  }

  @GetMapping("step9")
  public String sample39(ModelMap model) {

    return "sample37.html";
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
