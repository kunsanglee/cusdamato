package server.springselfinvocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnotherMemberService {

    private final MemberRepository memberRepository;

    public void saveWithPublic(Member member) {
        memberRepository.save(member);
    }

    protected void saveWithProtected(Member member) {
        memberRepository.save(member);
    }

    void saveWithPackage(Member member) {
        memberRepository.save(member);
    }

    public void saveOuterMethod(Member member) {
        saveWithPrivate(member);
    }

    private void saveWithPrivate(Member member) {
        memberRepository.save(member);
    }
}
