package com.example.student_portfolio.specification;

import com.example.student_portfolio.model.Achievement;
import com.example.student_portfolio.model.AchievementType;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;

public class AchievementSpecification {

    public static Specification<Achievement> hasType(AchievementType type) {
        return (Root<Achievement> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("type"), type);
    }

    public static Specification<Achievement> hasTag(String tag) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("tags")), "%" + tag.toLowerCase() + "%");
    }

    public static Specification<Achievement> hasFaculty(String faculty) {
        return (root, query, cb) ->
                cb.equal(root.join("student").get("faculty"), faculty);
    }

    public static Specification<Achievement> hasGroup(String groupName) {
        return (root, query, cb) ->
                cb.equal(root.join("student").get("groupName"), groupName);
    }

    public static Specification<Achievement> dateAfterOrEq(LocalDate from) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("date"), from);
    }

    public static Specification<Achievement> dateBeforeOrEq(LocalDate to) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("date"), to);
    }
}
