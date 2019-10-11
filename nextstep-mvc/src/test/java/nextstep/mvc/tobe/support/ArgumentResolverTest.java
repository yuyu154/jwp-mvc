package nextstep.mvc.tobe.support;

import nextstep.mvc.tobe.ModelAndView;
import nextstep.mvc.tobe.TestUser;
import nextstep.mvc.tobe.TestUserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentResolverTest {

    ArgumentResolver argumentResolver;
    Class<TestUserController> clazz;

    @BeforeEach
    public void setUp() {
        argumentResolver = new ArgumentResolver();
        clazz = TestUserController.class;
    }

    @Test
    public void resolveCreateString()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userId", "javajigi");
        request.addParameter("password", "password");

        Method method = clazz.getMethod("create_string", String.class, String.class);

        ModelAndView modelAndView = (ModelAndView) argumentResolver.resolve(
                request,
                clazz.getDeclaredConstructor().newInstance(),
                method);

        assertThat(modelAndView.getObject("userId")).isEqualTo("javajigi");
        assertThat(modelAndView.getObject("password")).isEqualTo("password");
    }

    @Test
    public void resolveCreateIntLong()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("id", "1");
        request.addParameter("age", "26");

        Method method = clazz.getMethod("create_int_long", long.class, int.class);

        ModelAndView modelAndView = (ModelAndView) argumentResolver.resolve(
                request,
                clazz.getDeclaredConstructor().newInstance(),
                method);

        assertThat(modelAndView.getObject("id")).isEqualTo(1L);
        assertThat(modelAndView.getObject("age")).isEqualTo(26);
    }

    @Test
    public void resolveCreateJavabean()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("userId", "javajigi");
        request.addParameter("password", "password");
        request.addParameter("age", "26");

        Method method = clazz.getMethod("create_javabean", TestUser.class);

        ModelAndView modelAndView = (ModelAndView) argumentResolver.resolve(
                request,
                clazz.getDeclaredConstructor().newInstance(),
                method);

        assertThat(modelAndView.getObject("userId")).isEqualTo("javajigi");
        assertThat(modelAndView.getObject("password")).isEqualTo("password");
        assertThat(modelAndView.getObject("age")).isEqualTo(26L);
    }

    @Test
    public void resolveShowPathVariable()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("id", "1");

        Method method = clazz.getMethod("show_pathvariable", long.class);

        ModelAndView modelAndView = (ModelAndView) argumentResolver.resolve(
                request,
                clazz.getDeclaredConstructor().newInstance(),
                method);

        assertThat(modelAndView.getObject("id")).isEqualTo(1L);
    }
}