package lee.joohan.thejavamockito.service;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudyServiceMockStubbingTest {
  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;

  // 모든 Mock 객체의 행동
  // 기본적으로 null을 리턴
  // primitive type은 기본 primitive 값
  // 콜렉션은 비어있는 콜렉션
  // Void 메소드는 예외를 던지지 않고 아무런 일도 발생하지 않음
  // Optional인 경우는 Optional.empty

  // Mock 객체를 조작해서
  // 특정 매개변수를



  @Test
  public void defaultStubbingAction() {
    Optional<Member> memberOptioanl = memberService.findById(1L);

    memberService.validate(2L);
  }

  @Test
  void defineCustomAction() {
    StudyService studyService = new StudyService(studyRepository, memberService);

    Member member = new Member();

    member.setId(1L);
    member.setEmail("joohan@email.com");

    when(memberService.findById(any())) // 반드시 1이라는 parameter 값을 보내야만 Optional.of(member)가 리턴됨. 2L로 보낸 경우 안됨
        .thenReturn(Optional.of(member));
    // 아무 parameter나 작동되게 하려면 any()를 넣어줌. when(memberService.findById(any()))

    Study study = new Study(10, "java");

    studyService.createNewStudy(1L, study); // 내부에서 memberService.findById(1L) 이 호출되면 위의 then 다음이 실행됨

    Optional<Member> findById = memberService.findById(2L);

    assertEquals("joohan@email.com", findById.get().getEmail());

  }

  @Test
  void handleException() {
    when(memberService.findById(1L)) // return 대신 exception을 던질 수  있음
        .thenThrow(new RuntimeException());
    // 아무 parameter나 작동되게 하려면 any()를 넣어줌. when(memberService.findById(any()))

    doThrow(new IllegalArgumentException()).when(memberService)
        .validate(1L);

    assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        memberService.validate(2L); // 위에 선언한 body가 아니므로 에러가 나오지 않음
  }

  @Test
  void composite() {
    Member member = new Member();

    member.setId(1L);
    member.setEmail("joohan@email.com");

    when(memberService.findById(any()))
        .thenReturn(Optional.of(member)) // 첫번째 호출 때는 정상적인 값 Optional을 return
        .thenThrow(new RuntimeException()) // 두번째 호출 때는 에러 발생
        .thenReturn(Optional.empty()); // 세번째 호출 때는 비어있는 Optional.empty()값 return

    Optional<Member> byId = memberService.findById(1L);

    assertEquals("joohan@email.com", byId.get().getEmail());

    assertThrows(RuntimeException.class, () -> memberService.findById(2L));
    assertEquals(Optional.empty(), memberService.findById(3L));
  }

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
  
  // 객체에 무슨 일이 일어났는지 확인
  @Test
  void verifying() {

  }
}
