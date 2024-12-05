package server.springselfinvocation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ManualMemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void test() {
        System.out.println(memberService.getClass());
        assertThat(AopUtils.isAopProxy(memberService)).isTrue();
        assertThat(AopUtils.isCglibProxy(memberService)).isTrue();
    }
}
