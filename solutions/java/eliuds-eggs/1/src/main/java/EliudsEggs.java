public class EliudsEggs {
  public int eggCount(int number) {
    return Integer.toBinaryString(number).replace("0", "").length();
  }
}