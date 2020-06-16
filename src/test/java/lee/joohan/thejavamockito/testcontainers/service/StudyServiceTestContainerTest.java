package lee.joohan.thejavamockito.testcontainers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.repository.StudyRepository;
import lee.joohan.thejavamockito.service.MemberService;
import lee.joohan.thejavamockito.service.StudyService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// 테스트를 위한 컨테이너를 띄워서 테스트를 해볼 수 있음
// 단점은 좀 느리다는게 문제
// docker가 설치되어 있어야 테스트 가능



@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Testcontainers
public class StudyServiceTestContainerTest {
    @Mock
    MemberService memberService;

    @Autowired
    StudyRepository studyRepository;

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer()
        .withDatabaseName("studytest");



// 매 테스트 마다 컨테이너를 새로 띄우면 너무 오래 걸리기 때문에 위에 static 변수로 하나만 쓰고 각 테스트마다 데이터를 지워주는 방식으로 처리
  @BeforeEach
  void setUp() {
    studyRepository.deleteAll();
  }

  // 이 부분은 클래스에 @TestContainers와 바로 위에 @Container 어노테이션으로 대체 가능
//  @BeforeAll
//  static void beforeAll() {
//    postgreSQLContainer.start();
//  }
//
//  @AfterAll
//  static void afterAll() {
//    postgreSQLContainer.stop();
//  }

  @Test
    void homework() {
      //TODO: MemberService 객체에 findById 메소드를 1L 값으로 호출하면 member 객체를 리턴하도록 stubbing
      //TODO: StudyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 stubbing

      Member member = new Member();

      member.setId(1L);
      member.setEmail("joohan@email.com");

      when(memberService.findById(1L))
          .thenReturn(Optional.of(member));

      Study study = new Study(10, "테스트");

      study.setOwner(member);

      when(studyRepository.save(study))
          .thenReturn(study);

      member = memberService.findById(1L).get();
      study = studyRepository.save(study);

      assertNotNull(study.getOwner());
      assertEquals(member, study.getOwner());
    }

}
