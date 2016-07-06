package by.it.academy.adorop.web.config;

import by.it.academy.adorop.model.Mark;
import by.it.academy.adorop.service.api.MarkService;
import by.it.academy.adorop.web.utils.RequestParamValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class MarkByIdHandlerMethodArgumentResolverImpl implements MarkByIdHandlerMethodArgumentResolver {

    private final MarkService markService;

    @Autowired
    public MarkByIdHandlerMethodArgumentResolverImpl(MarkService markService) {
        this.markService = markService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(ModelById.class) != null && parameter.getParameterType().equals(Mark.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String markId = webRequest.getParameter("markId");
        if (!RequestParamValidator.isPositiveNumber(markId)) {
            return ResponseEntity.badRequest();
        }
        Mark mark = markService.find(Long.valueOf(markId));
        if (mark == null) {
            return ResponseEntity.badRequest();
        }
        return mark;
    }
}
