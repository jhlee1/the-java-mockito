package lee.joohan.thejavamockito.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Study {
  private Member owner;
  private int limitCount;
  private String name;
  private StudyStatus status;
  private LocalDateTime openedDateTime;


  public Study(int limitCount, String name) {
    this.limitCount = limitCount;
    this.name = name;
  }

  public void open() {
    openedDateTime = LocalDateTime.now();
    status = StudyStatus.OPENED;
  }
}
