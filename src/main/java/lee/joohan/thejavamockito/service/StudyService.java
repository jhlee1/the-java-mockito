package lee.joohan.thejavamockito.service;

import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.repository.StudyRepository;
import lombok.RequiredArgsConstructor;

public class StudyService {
  private final StudyRepository studyRepository;
  private final MemberService memberService;

  public StudyService(StudyRepository studyRepository, MemberService memberService) {
    assert memberService != null;
    assert studyRepository != null;

    this.studyRepository = studyRepository;
    this.memberService = memberService;
  }

  public Study createNewStudy(Long memberId, Study study) {
    Member member = memberService.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("Member does not exist for id: '" + memberId + "'"));

    study.setOwner(member);

    Study newStudy = studyRepository.save(study);
    memberService.notify(newStudy);
    memberService.notify(member);

    return studyRepository.save(study);
  }

  public Study openStudy(Study study) {
    study.open();

    Study openedStudy = studyRepository.save(study);
    memberService.notify(openedStudy);

    return openedStudy;
  }
}
