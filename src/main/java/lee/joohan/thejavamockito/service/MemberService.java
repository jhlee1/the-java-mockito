package lee.joohan.thejavamockito.service;

import java.util.Optional;
import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.exception.InvalidMemberException;
import lee.joohan.thejavamockito.exception.MemberNotFoundException;

public interface MemberService {
  Optional<Member> findById(Long memberId);

  void validate(Long memberId);

  void notify(Study study);
}
