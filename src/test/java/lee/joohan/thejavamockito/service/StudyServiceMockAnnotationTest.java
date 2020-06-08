package lee.joohan.thejavamockito.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import lee.joohan.thejavamockito.repository.StudyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudyServiceMockAnnotationTest {
  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;


  @Test
  void MockExample1() {
    StudyService studyService = new StudyService(studyRepository, memberService);

    assertNotNull(studyService);

  }

//  이런식으로 필드로 선언 안하고 annotation하고 같이 parameter에 넣어도 가능
//  @Test
//  void MockExample2(@Mock MemberService memberService, StudyRepository studyRepository) {
//    StudyService studyService = new StudyService(studyRepository, memberService);
//
//    assertNotNull(studyService);
//
//  }
}
