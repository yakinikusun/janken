package oit.is.z3065.kaizi.janken.model;

public class Janken {

  String player;
  String computer;
  String result;

  public Janken() {

  }

  public Janken() {

  }

  public void buttle(String player, String computer) {
        this.player = player;
        this.computer = computer;
        if (player.equals(computer)) {
            this.result = "あいこ";
        } else if ((player.equals("グー") && computer.equals("チョキ")) || (player.equals("チョキ") && computer.equals("パー")) || (player.equals("パー") && computer.equals("グー"))) {
            this.result = "あなたの勝ち";
        } else {
            this.result = "あなたの負け";
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
}
