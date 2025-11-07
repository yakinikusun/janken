package oit.is.z3065.kaizi.janken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3065.kaizi.janken.model.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Random;

@Controller
public class JankenController {

  // @Autowired
  // private Entry entry;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchInfoMapper matchinfoMapper;

  Janken janken = new Janken();

  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    /*
     * String loginUser = prin.getName();
     * entry.addUser(loginUser);
     * model.addAttribute("room", entry);
     */

    if (userMapper.selectByName(prin.getName()) == null) {
      userMapper.insertUser(prin.getName());
    }

    ArrayList<User> Users = userMapper.selectAllUser();
    model.addAttribute("users", Users);

    ArrayList<Match> Matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", Matches);

    ArrayList<MatchInfo> MatchInfos = matchinfoMapper.selectActives();
    model.addAttribute("matchinfos", MatchInfos);

    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {

    String enemy = userMapper.selectById(id);
    model.addAttribute("enemy", enemy);
    model.addAttribute("ID", id);

    return "match.html";
  }

  @PostMapping("/fight")
  @Transactional
  public String fight(@RequestParam Integer id, @RequestParam String p1hand, Principal prin, ModelMap model) {

    if (userMapper.selectById(id) == "CPU") {
      Match match = new Match();
      String p2hand = CPU_hand();

      if (p1hand == "Pa") {
        janken.buttle("パー", p2hand);
      } else if (p1hand == "Choki") {
        janken.buttle("チョキ", p2hand);
      } else {
        janken.buttle("グー", p2hand);
      }
      match.setUser1(userMapper.selectByName(prin.getName()));
      match.setUser2(id);
      match.setUser1Hand(p1hand);
      match.setUser2Hand(conv_hand(p2hand));
      if (janken.getResultCode() == 1) {
        match.setResult(userMapper.selectByName(prin.getName()));
      } else if (janken.getResultCode() == 2) {
        match.setResult(id);
      } else {
        match.setResult(0);
      }

      String enemy = userMapper.selectById(id);
      model.addAttribute("enemy", enemy);
      model.addAttribute("ID", id);

      matchMapper.insertMatch(match);
      model.addAttribute("janken", janken);

    } else {
      MatchInfo matchinfo = new MatchInfo();
      matchinfo.setUser1(userMapper.selectByName(prin.getName()));
      matchinfo.setUser2(id);
      matchinfo.setUser1Hand(p1hand);
      matchinfo.setActive(true);
      matchinfoMapper.insertMatchInfo(matchinfo);

      model.addAttribute("ID", id);
      model.addAttribute("matchinfo", matchinfo);
    }

    return "wait.html";

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

  public String conv_hand(String hand) {
    if (hand.equals("パー")) {
      return "Pa";
    } else if (hand.equals("チョキ")) {
      return "Choki";
    }
    return "Gu";
  }
}
