package server.springselfinvocation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomMemberServiceTest {

    private static final Logger log = LoggerFactory.getLogger(CustomMemberServiceTest.class);
    @Autowired
    private CustomMemberService customMemberService;

    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("인터페이스를 구현하지 않은 클래스는 CGLIB Proxy로 감싸진다.")
    @Test
    void createProxy() {
        assertThat(AopUtils.isAopProxy(customMemberService)).isTrue();
        assertThat(AopUtils.isCglibProxy(customMemberService)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(customMemberService)).isFalse();
    }

    @DisplayName("public 메서드는 @Transactional이 적용되어 롤백 가능하다.")
    @Test
    void saveWithPublic() {
        Member member = new Member("test");

        try {
            log.info("method call");
            customMemberService.saveWithPublic(member);
        } catch (Exception e) {
            // ignore
            log.error("catch exception");
        }

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isZero();
    }

    @DisplayName("CGLIB Proxy는 protected 접근 제어자도 aop 가능하다. 즉, 롤백 가능하다.")
    @Test
    void saveWithProtected() {
        Member member = new Member("test");

        try {
            customMemberService.saveWithProtected(member);
        } catch (Exception e) {
            // ignore
        }

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isZero();
    }

    @DisplayName("CGLIB Proxy는 package-private 접근 제어자도 aop 가능하다. 즉, 롤백 가능하다.")
    @Test
    void saveWithPackagePrivate() {
        Member member = new Member("test");

        try {
            customMemberService.saveWithPackage(member);
        } catch (Exception e) {
            // ignore
        }

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isZero();
    }

    @DisplayName("트랜잭션이 선언되지 않은 외부 메서드에서 트랜잭션이 선언된 private 메서드를 내부에서 호출하면 aop가 동작하지 않는다.")
    @Test
    void outerMethod() {
        /**
         * @Transactional self-invocation
         * (in effect, a method within the target object calling another method of the target object)
         * does not lead to an actual transaction at runtime
         */
        Member member = new Member("test");

        try {
            customMemberService.saveOuterMethod(member);
        } catch (Exception e) {
            // ignore
        }

        List<Member> members = memberRepository.findAll();
        assertThat(members.size()).isOne();
    }
}
