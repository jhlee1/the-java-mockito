package lee.joohan.thejavamockito.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;
import lee.joohan.thejavamockito.domain.Member;
import lee.joohan.thejavamockito.domain.Study;
import lee.joohan.thejavamockito.repository.StudyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class StudyServiceTest {

  @Test
  void createStudyService() {
    MemberService memberService = new MemberService() {
      @Override
      public Optional<Member> findById(Long memberId) {
        return Optional.empty();
      }

      @Override
      public void validate(Long memberId) {

      }

      @Override
      public void notify(Study study) {

      }

      @Override
      public void notify(Member member) {

      }
    };

    StudyRepository studyRepository = new StudyRepository() {
      @Override
      public List<Study> findAll() {
        return null;
      }

      @Override
      public List<Study> findAll(Sort sort) {
        return null;
      }

      @Override
      public List<Study> findAllById(Iterable<Long> longs) {
        return null;
      }

      @Override
      public <S extends Study> List<S> saveAll(Iterable<S> entities) {
        return null;
      }

      @Override
      public void flush() {

      }

      @Override
      public <S extends Study> S saveAndFlush(S entity) {
        return null;
      }

      @Override
      public void deleteInBatch(Iterable<Study> entities) {

      }

      @Override
      public void deleteAllInBatch() {

      }

      @Override
      public Study getOne(Long aLong) {
        return null;
      }

      @Override
      public <S extends Study> List<S> findAll(Example<S> example) {
        return null;
      }

      @Override
      public <S extends Study> List<S> findAll(Example<S> example, Sort sort) {
        return null;
      }

      @Override
      public Page<Study> findAll(Pageable pageable) {
        return null;
      }

      @Override
      public <S extends Study> S save(S entity) {
        return null;
      }

      @Override
      public Optional<Study> findById(Long aLong) {
        return Optional.empty();
      }

      @Override
      public boolean existsById(Long aLong) {
        return false;
      }

      @Override
      public long count() {
        return 0;
      }

      @Override
      public void deleteById(Long aLong) {

      }

      @Override
      public void delete(Study entity) {

      }

      @Override
      public void deleteAll(Iterable<? extends Study> entities) {

      }

      @Override
      public void deleteAll() {

      }

      @Override
      public <S extends Study> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
      }

      @Override
      public <S extends Study> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
      }

      @Override
      public <S extends Study> long count(Example<S> example) {
        return 0;
      }

      @Override
      public <S extends Study> boolean exists(Example<S> example) {
        return false;
      }
    };

    StudyService studyService = new StudyService(studyRepository, memberService);

    assertNotNull(studyService);
  }

  @Test
  void createStudyServiceUsingMock() {
    MemberService memberService = mock(MemberService.class);
    StudyRepository studyRepository = mock(StudyRepository.class);

    StudyService studyService = new StudyService(studyRepository, memberService);

    assertNotNull(studyService);
  }
}