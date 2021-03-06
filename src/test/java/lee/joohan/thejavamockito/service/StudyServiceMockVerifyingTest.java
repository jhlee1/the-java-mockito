package lee.joohan.thejavamockito.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.domain.StudyStatus;
import lee.joohan.thejavamockito.repository.StudyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Created by Joohan Lee on 2020/06/15
 */

// 객체에 무슨 일이 일어났는지 확인
@ExtendWith(MockitoExtension.class)
public class StudyServiceMockVerifyingTest {
  @Mock
  MemberService memberService;

  @Mock
  StudyRepository studyRepository;


  @Test
  void createNewStudy() {
    StudyService studyService = new StudyService(studyRepository, memberService);
    assertNotNull(studyService);

    Member member = new Member();

    member.setId(1L);
    member.setEmail("joohan@email.com");

    Study study = new Study(10, "java");

    when(memberService.findById(1L)).thenReturn(Optional.of(member));

    studyService.createNewStudy(1L, study);

    assertEquals(member, study.getOwner());

    // notify가 1번만 불리는지 검사.
    // 한번도 불리지 않은 경우 or 1번 넘게 불린 경우 에러.
    verify(memberService, times(1)).notify(study);
    verify(memberService, times(1)).notify(member);

    // 전혀 호출되면 안되는 경우 never로 처리
    verify(memberService, never()).validate(any());

    // 호출되는 순서가 중요한 경우
    InOrder inOrder = Mockito.inOrder(memberService);
    inOrder.verify(memberService).notify(study);
    inOrder.verify(memberService).notify(member);

    // 더 이상 어떤 액션 이후에 다른 액션이 일어나면 안되는 경우
    verifyNoMoreInteractions(memberService);
  }

  // BDD API 사용해보기
  @Test
  void bddAPI() {

    //Given
    StudyService studyService = new StudyService(studyRepository, memberService);
    assertNotNull(studyService);

    Member member = new Member();

    member.setId(1L);
    member.setEmail("joohan@email.com");

    Study study = new Study(10, "java");

    // Given 절에 있지만 when이라고 쓰여진게 어색해보임 -> bdd mockito를 이용하자
//    when(memberService.findById(1L)).thenReturn(Optional.of(member));
    given(memberService.findById(1L)).willReturn(Optional.of(member));


    // When
    studyService.createNewStudy(1L, study);


    // Then
    assertEquals(member, study.getOwner());

    verify(memberService, times(1)).notify(study);
    verify(memberService, never()).validate(any());

    // BDD 스타일로
    then(memberService).should(times(1)).notify(study);
    then(memberService).shouldHaveNoMoreInteractions();

    InOrder inOrder = Mockito.inOrder(memberService);
    inOrder.verify(memberService).notify(study);
    inOrder.verify(memberService).notify(member);

    // 더 이상 어떤 액션 이후에 다른 액션이 일어나면 안되는 경우
    verifyNoMoreInteractions(memberService);
  }

  @DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
  @Test
  void homework() {
    // Given
    StudyService studyService = new StudyService(studyRepository, memberService);
    Study study = new Study(10, "The java, test");

    //TODO: StudyRepository Mock 객체의 save 메소드를 호출 시 study를 리턴하기
    given(studyRepository.save(study)).willReturn(study);

    // When
    studyService.openStudy(study);

    //Then
    //TODO: study의 status가 OPENED로 변경됐는지 확인
    assertEquals(StudyStatus.OPENED, study.getStatus());
    //TODO: study의 openedDateTime이 null이 아닌지 확인
    assertNotNull(study.getOpenedDateTime());
    //TODO: memberService의 notify(study)가 호출 됐는지 확인
    then(memberService).should().notify(study);

  }



}
