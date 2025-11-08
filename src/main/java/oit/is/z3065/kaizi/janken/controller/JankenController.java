package oit.is.z3065.kaizi.janken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z3065.kaizi.janken.model.*;
import oit.is.z3065.kaizi.janken.service.AsyncKekka;

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

  @Autowired
  AsyncKekka asyncKekka;

  Janken janken = new Janken();

  // メイン画面の処理
  @GetMapping("/janken")
  public String janken(Principal prin, ModelMap model) {
    /*
     * String loginUser = prin.getName();
     * entry.addUser(loginUser);
     * model.addAttribute("room", entry);
     */

    // ユーザ登録
    if (userMapper.selectByName(prin.getName()) == null) {
      userMapper.insertUser(prin.getName());
    }

    // ユーザ一覧の取得
    ArrayList<User> Users = userMapper.selectAllUser();
    model.addAttribute("users", Users);

    // 試合一覧の取得
    ArrayList<Match> Matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", Matches);

    // アクティブ試合一覧の取得
    ArrayList<MatchInfo> MatchInfos = matchinfoMapper.selectActives();
    model.addAttribute("matchinfos", MatchInfos);

    return "janken.html";
  }

  // 対戦画面の処理
  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {

    String enemy = userMapper.selectById(id);
    model.addAttribute("enemy", enemy);
    model.addAttribute("ID", id);

    return "match.html";
  }

  // 対戦処理
  @PostMapping("/fight")
  @Transactional
  public String fight(@RequestParam Integer id, @RequestParam String Uhand, Principal prin, ModelMap model) {

   
    // CPUと対戦
    if (userMapper.selectById(id).equals("CPU")) {

      // 値の準備
      Match match = new Match();
      String Ehand = CPU_hand();

      // じゃんけんの勝敗判定
      janken.buttle(Uhand, Ehand);

      // 結果の格納
      match.setUser1(userMapper.selectByName(prin.getName()));
      match.setUser2(id);
      match.setUser1Hand(Uhand);
      match.setUser2Hand(Ehand);

      if (janken.getResult() == 1) {
        match.setResult(userMapper.selectByName(prin.getName()));
      } else if (janken.getResult() == 2) {
        match.setResult(id);
      } else {
        match.setResult(0);
      }

      // DBへの格納
      matchMapper.insertMatch(match);
      asyncKekka.UpdateMatchDb();

      // モデルへの格納
      model.addAttribute("matches", match);

    } else {
      // 人間同士の対戦
      if (matchinfoMapper.searchActive(id, userMapper.selectByName(prin.getName())) != null) {
        // アクティブ試合が存在する場合、対戦開始
        MatchInfo matchInfo = matchinfoMapper
            .selectbyId(matchinfoMapper.searchActive(id, userMapper.selectByName(prin.getName())));
        Match match = new Match();

        // じゃんけんの勝敗判定
        String Ehand = matchInfo.getUser1Hand();
        janken.buttle(Ehand, Uhand);

        match.setUser1(id);
        match.setUser2(userMapper.selectByName(prin.getName()));
        match.setUser1Hand(Ehand);
        match.setUser2Hand(Uhand);

        if (janken.getResult() == 1) {
          match.setResult(id);
        } else if (janken.getResult() == 2) {
          match.setResult(userMapper.selectByName(prin.getName()));
        } else {
          match.setResult(0);
        }

        match.setActive(true);

        // DBへの格納
        matchMapper.insertMatch(match);
        matchinfoMapper.updateActive(matchInfo.getId(), false);

      } else {
        // アクティブ試合が存在しない場合、新規作成
        MatchInfo matchinfo = new MatchInfo();
        matchinfo.setUser1(userMapper.selectByName(prin.getName()));
        matchinfo.setUser2(id);
        matchinfo.setUser1Hand(Uhand);
        matchinfo.setActive(true);
        matchinfoMapper.insertMatchInfo(matchinfo);
      }
    }

    return "wait.html";

  }

  // 非同期処理のメソッド
  @GetMapping("/wait")
  public SseEmitter asyncMethod(Principal prin) {
    final SseEmitter sseEmitter = new SseEmitter();
    asyncKekka.asyncShowMatchinfo(sseEmitter, userMapper.selectByName(prin.getName()));
    return sseEmitter;
  }

  @GetMapping("/allmatches")
  public SseEmitter asyncAllMatch() {
    final SseEmitter sseEmitter = new SseEmitter();
    asyncKekka.asyncShowMatch(sseEmitter);
    return sseEmitter;
  }

  // CPUの手をランダムに決定するメソッド
  public String CPU_hand() {
    Random random = new Random();
    int n = random.nextInt(3);
    if (n == 0) {
      return "Pa";
    } else if (n == 1) {
      return "Choki";
    }
    return "Gu";
  }
}
