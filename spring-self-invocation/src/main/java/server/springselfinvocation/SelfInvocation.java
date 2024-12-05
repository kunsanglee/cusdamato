package server.springselfinvocation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SelfInvocation {

    private final MemberRepository memberRepository;

    public void outerSaveWithPublic(Member member) {
        saveWithPrivate(member);
    }

    @Transactional
    public void saveWithPublic(Member member) {
        log.info("call saveWithPublic");
        memberRepository.save(member);
        throw new RuntimeException("rollback test");
    }

    public void outerSaveWithPrivate(Member member) {
        saveWithPrivate(member);
    }

    @Transactional
    private void saveWithPrivate(Member member) {
        log.info("call saveWithPrivate");
        memberRepository.save(member);
        throw new RuntimeException("rollback test");
    }
}
