package lee.joohan.thejavamockito.testcontainers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.repository.StudyRepository;
import lee.joohan.thejavamockito.service.MemberService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;



@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@Testcontainers
public class StudyServiceTestContainer2Test {
  static Logger LOGGER = LoggerFactory.getLogger(StudyServiceTestContainer2Test.class); // lombok의 @Slf4j 써도됨

  @Mock
  MemberService memberService;

  @Autowired
  StudyRepository studyRepository;


  // Test Container가 기본적으로 module을 제공하지 않는 경우 docker image 이름만 있으면 불러올 수 있음
  @Container
  static GenericContainer postgreSQLContainer = new GenericContainer("postgres")
      .withExposedPorts(5432) // host 포트가 아니라 docker 내부 포트임. host포트는 항상 랜덤으로 사용가능한 포트를 연결하도록 설정되어있음
      .withEnv("POSTGRES_DB", "studytest")
      .waitingFor(Wait.forListeningPort());


  @BeforeAll
  static void beforeAll() {
    // Test Container 로거 설정하기
    Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(LOGGER);
    postgreSQLContainer.followOutput(logConsumer); // stream 방식으로 컨테이너 로그를 보여준다

  }

  // 매 테스트 마다 컨테이너를 새로 띄우면 너무 오래 걸리기 때문에 위에 static 변수로 하나만 쓰고 각 테스트마다 데이터를 지워주는 방식으로 처리
  @BeforeEach
  void setUp() {
    System.out.println("======================");
    System.out.println(postgreSQLContainer.getMappedPort(5432)); // 자동으로 생성된 호스트 포트를 확인할 수 있음
    System.out.println(postgreSQLContainer.getLogs()); // 컨테이너 내부의 모든 로그를 가져옴
    studyRepository.deleteAll();
  }


  @Test
  void homework() {
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
