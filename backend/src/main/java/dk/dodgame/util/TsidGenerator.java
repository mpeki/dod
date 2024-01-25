package dk.dodgame.util;

import io.hypersistence.tsid.TSID;

public class TsidGenerator {
  public static String createTsid(){
    return TSID.Factory.getTsid().toString();
  }

}
