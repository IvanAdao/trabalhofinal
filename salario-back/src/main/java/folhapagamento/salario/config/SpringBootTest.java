package folhapagamento.salario.config;

import org.apache.catalina.security.SecurityConfig;

public @interface SpringBootTest {

    Class<SecurityConfig>[] exclude();

}
