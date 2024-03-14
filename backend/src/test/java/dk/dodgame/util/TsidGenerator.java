package dk.dodgame.util;

import io.hypersistence.tsid.TSID;

public class TsidGenerator {

  public static String createTsid(){
    return TSID.Factory.getTsid().toString();
  }

  //This can be used to generate tsids for test and configuration data
  public static void main(String[] args) {
    for(int i=0; i<20; i++){
      String tsid = TSID.Factory.getTsid().toString();
      System.out.println(tsid);
    }
  }
}
