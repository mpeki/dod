package dk.pekilidi.dod.util.rules;

public class RulesUtil {
  public static int calculateGroupValue(int value){
    if (value < 0){
      return -1;
    }
    switch(value) {
      case 0,1,2,3: return 0;
      case 4,5,6,7,8: return 1;
      case 9,10,11,12: return 2;
      case 13,14,15,16: return 3;
      case 17,18,19,20: return 4;
      case 21,22,23,24,25: return 5;
      case 26,27,28,29,30: return 6;
      default:
        return value / 10 + (value % 10 == 0 ? 3 : 4);
    }
  }
}
