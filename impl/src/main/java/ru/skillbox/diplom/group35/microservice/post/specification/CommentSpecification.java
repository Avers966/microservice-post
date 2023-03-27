package ru.skillbox.diplom.group35.microservice.post.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.skillbox.diplom.group35.library.core.dto.base.BaseSearchDto;
import ru.skillbox.diplom.group35.library.core.model.base.BaseEntity_;

import javax.persistence.metamodel.SingularAttribute;
import java.util.function.Supplier;
@UtilityClass
public class CommentSpecification {
    public final Specification EMPTY_SPECIFICATION = (root, query, criteriaBuilder) -> null;

    public static <T, V> Specification<T> equal(SingularAttribute<T, V> field, V value, boolean isSkipNullValues) {
        return nullValueCheck(value, isSkipNullValues, () -> ((root, query, builder) -> {
            query.distinct(true);
            return builder.equal(root.get(field), value);
        }));
    }

    public static  <T, V> Specification<T> nullValueCheck(V value, boolean isSkipNullValues, Supplier<Specification<T>> specificationSupplier) {
        return value == null && isSkipNullValues ? EMPTY_SPECIFICATION : (Specification) specificationSupplier.get();
    }

    public static Specification getBaseSpecification(BaseSearchDto searchDto) {
        return equal(BaseEntity_.id, searchDto.getId(), true)
                .and(equal(BaseEntity_.isDeleted, searchDto.getIsDeleted(), true));
    }
}
