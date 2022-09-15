package top.hlmy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.hlmy.uitl.IpUtil;

@SpringBootTest
class IpAffiliationApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(IpUtil.getIp("103.100.64.94"));
    }

}
