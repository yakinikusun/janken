package oit.is.z3065.kaizi.janken.model;

public class Janken {

  String id;
  String player;
  String computer;
  String result;

  public Janken() {

  }

  public void set_id(String id) {
    this.id = id;
  }

  public void buttle(String player, String computer) {
    this.player = player;
    this.computer = computer;
    if (player.equals(computer)) {
      this.result = "あいこ";
    } else if ((player.equals("グー") && computer.equals("チョキ")) || (player.equals("チョキ") && computer.equals("パー"))
        || (player.equals("パー") && computer.equals("グー"))) {
      this.result = "あなたの勝ち";
    } else {
      this.result = "あなたの負け";
    }
  }

  public String getID() {
    return id;
  }

  public String getPlayer() {
    return player;
  }

  public String getComputer() {
    return computer;
  }

  public String getResult() {
    return result;
  }
}
