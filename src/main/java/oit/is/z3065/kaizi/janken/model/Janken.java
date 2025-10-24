package oit.is.z3065.kaizi.janken.model;

public class Janken {

  String player;
  String computer;
  String result;
  int resultCode;

  public Janken() {

  }

  public void buttle(String player, String computer) {
    this.player = player;
    this.computer = computer;
    if (player.equals(computer)) {
      this.result = "あいこ";
      this.resultCode = 0;
    } else if ((player.equals("グー") && computer.equals("チョキ")) || (player.equals("チョキ") && computer.equals("パー"))
        || (player.equals("パー") && computer.equals("グー"))) {
      this.result = "あなたの勝ち";
      this.resultCode = 1;
    } else {
      this.result = "あなたの負け";
      this.resultCode = 2;
    }
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

  public int getResultCode() {
    return resultCode;
  }
}
