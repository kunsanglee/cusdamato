package server.springselfinvocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomMemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void saveWithPublic(Member member) {
        memberRepository.save(member);

        throw new RuntimeException("rollback test");
    }

    @Transactional
    protected void saveWithProtected(Member member) {
        memberRepository.save(member);

        throw new RuntimeException("rollback test");
    }

    @Transactional
    void saveWithPackage(Member member) {
        memberRepository.save(member);

        throw new RuntimeException("rollback test");
    }

    public void saveOuterMethod(Member member) {
        saveWithPrivate(member);
    }

    @Transactional
    private void saveWithPrivate(Member member) {
        memberRepository.save(member);

        throw new RuntimeException("rollback test");
    }
}
