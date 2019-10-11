package nextstep.mvc.tobe.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

// @TODO 인터페이스로 분리해 볼 것
public class ArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(ArgumentResolver.class);

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    public Object resolve(HttpServletRequest request, Object instance, Method method)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        List<String> requestNames = getParameterNames(request, method);
        Class<?> parameterTypes[] = method.getParameterTypes();
        List<Object> parameters = new ArrayList<>();

        //@TODO 객체로 바꾸기
        for (int i = 0; i < requestNames.size(); i++) {
            parameters.add(getRealObject(parameterTypes[i], requestNames.get(i), request));
        }
        return method.invoke(instance, parameters.toArray());
    }

    private Object getRealObject(Class<?> parameterType, String value, HttpServletRequest request) throws NoSuchMethodException {
        if (parameterType.equals(String.class)) {
            return value;
        }
        if (parameterType.equals(long.class)) {
            return Long.parseLong(value);
        }
        if (parameterType.equals(int.class)) {
            return Integer.parseInt(value);
        }
        throw new IllegalArgumentException();
    }

    private List<String> getParameterNames(final HttpServletRequest request, final Method method) {
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        List<String> requestNames = new ArrayList<>();
        for (final String parameterName : parameterNames) {
            requestNames.add(request.getParameter(parameterName));
        }
        return requestNames;
    }
}
