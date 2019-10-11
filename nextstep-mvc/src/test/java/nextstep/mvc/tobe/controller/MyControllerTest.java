package nextstep.mvc.tobe.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import nextstep.mvc.tobe.TestUserController;

import java.lang.reflect.Method;

public class MyControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(MyControllerTest.class);

    @Test
    public void test() {
        ParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

        Class clazz = TestUserController.class;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            String[] parameterNames = nameDiscoverer.getParameterNames(method);
            Class<?>[] types = method.getParameterTypes();
            logger.debug("method : {} of {}", method.getName(), method.hashCode());
            for (int i = 0; i < parameterNames.length; i++) {
                String parameterName = parameterNames[i];
                logger.debug("parameter name: {}", parameterName);
                logger.debug("parameter type: {}", types[i]);
            }
            logger.debug("\n");
        }
    }
}
