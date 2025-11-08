package oit.is.z3065.kaizi.janken.model;

public class Janken {

  String player;
  String computer;
  int result;

  public Janken() {

  }

  public void buttle(String player, String computer) {
    this.player = player;
    this.computer = computer;
    if (player.equals(computer)) {
      this.result = 0;
    } else if ((player.equals("Gu") && computer.equals("Choki")) || (player.equals("Choki") && computer.equals("Pa"))
        || (player.equals("Pa") && computer.equals("Gu"))) {
      this.result = 1;
    } else {
      this.result = 2;
    }
  }

  public String getPlayer() {
    return player;
  }

  public String getComputer() {
    return computer;
  }

  public int getResult() {
    return result;
  }

}
