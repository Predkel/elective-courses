package by.it.academy.adorop.dao.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class CatchAndRethrowAnnotationHandlerBeanPostProcessor implements BeanPostProcessor, Ordered {
    private Map<String, Class> beanNamesToOriginalClasses = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(CatchAndRethrow.class)) {
            beanNamesToOriginalClasses.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class originalBeanClass = beanNamesToOriginalClasses.get(beanName);
        if (originalBeanClass != null) {
            CatchAndRethrow annotation = (CatchAndRethrow) originalBeanClass.getDeclaredAnnotation(CatchAndRethrow.class);
            Class<? extends RuntimeException> exceptionToCatch = annotation.exceptionToCatch();
            Class<? extends RuntimeException> rethrow = annotation.rethrow();
            return Proxy.newProxyInstance(originalBeanClass.getClassLoader(), originalBeanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    try {
                        return method.invoke(bean, args);
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (exceptionToCatch.isAssignableFrom(cause.getClass())){
                            Constructor<? extends RuntimeException> constructor = rethrow.getConstructor(Throwable.class);
                            throw constructor.newInstance(cause);
                        } else {
                            throw cause;
                        }
                    }
                }
            });
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
