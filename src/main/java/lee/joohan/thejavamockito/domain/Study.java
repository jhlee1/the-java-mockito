package lee.joohan.thejavamockito.domain;

import lombok.Data;

@Data
public class Study {
  private Member owner;
  private int limitCount;
  private String name;


  public Study(int limitCount, String name) {
    this.limitCount = limitCount;
    this.name = name;
  }
}
