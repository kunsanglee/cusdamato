package server.springselfinvocation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ManualMemberService implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }
}
