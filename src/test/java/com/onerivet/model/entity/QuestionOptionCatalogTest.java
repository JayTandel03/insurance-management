package com.onerivet.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class QuestionOptionCatalogTest {

    @Test
    void shouldSetAndGetAllFields() {

        QuestionOptionCatalog catalog = new QuestionOptionCatalog();

        Option option = new Option();
        option.setOptionId(1);

        Question question = new Question();
        question.setQuestionId(10);

        LocalDateTime deletedDate = LocalDateTime.now();

        catalog.setQuestionOptionCatalogId(100);
        catalog.setOption(option);
        catalog.setQuestion(question);
        catalog.setDeletedDate(deletedDate);

        assertEquals(100, catalog.getQuestionOptionCatalogId());
        assertEquals(option, catalog.getOption());
        assertEquals(question, catalog.getQuestion());
        assertEquals(deletedDate, catalog.getDeletedDate());
    }

    @Test
    void shouldHandleNullValues() {

        QuestionOptionCatalog catalog = new QuestionOptionCatalog();

        catalog.setQuestionOptionCatalogId(null);
        catalog.setOption(null);
        catalog.setQuestion(null);
        catalog.setDeletedDate(null);

        assertNull(catalog.getQuestionOptionCatalogId());
        assertNull(catalog.getOption());
        assertNull(catalog.getQuestion());
        assertNull(catalog.getDeletedDate());
    }
}
