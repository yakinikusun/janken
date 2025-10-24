package oit.is.z3065.kaizi.janken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3065.kaizi.janken.model.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Random;

@Controller
@RequestMapping("/janken")
public class JankenController {


  @Autowired
  private Entry entry;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  UserMapper userMapper;


  Janken janken = new Janken();

  @GetMapping
  public String janken(Principal prin, ModelMap model) {
    /*
    String loginUser = prin.getName();
    entry.addUser(loginUser);
    model.addAttribute("room", entry);
    */

    ArrayList<User> Users = userMapper.selectAllUser();
    model.addAttribute("users", Users);
    
    
    ArrayList<Match> Matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", Matches);
  

    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {
    
    String enemy = userMapper.selectById(id);
    model.addAttribute("enemy", enemy);

    return "match.html";
  }

  @PostMapping("/match")
  public String poyon(@RequestParam Integer id,@RequestParam String p1hand, ModelMap model) {
    String P1;

    if (p1hand == "pa") {
      P1 = "パー";
    } else if (p1hand == "cho") {
      P1 = "チョキ";
    } else {
      P1 = "グー";
    }
    janken.buttle(P1, CPU_hand());
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
