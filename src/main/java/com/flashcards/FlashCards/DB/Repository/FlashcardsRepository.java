package com.flashcards.FlashCards.DB.Repository;

import com.flashcards.FlashCards.DB.Model.Category;
import com.flashcards.FlashCards.DB.Model.Flashcard;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Optional;

@Getter @Setter
@Repository
public class FlashcardsRepository {
    @Autowired
    private NamedParameterJdbcTemplate paramTemplate;
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private CategoryRepository categoryRepository;

    public String save(Flashcard flashcard){
        Category category = categoryRepository.save(flashcard.getCategoryName());

        String sql = "INSERT INTO FLASHCARD(fc_cat_id, fc_question, fc_answer) values (:category_id, :question, :answer)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("category_id", category.getId())
                .addValue("question", flashcard.getQuestion())
                .addValue("answer", flashcard.getAnswer());
        paramTemplate.update(sql, namedParameters);
        return "SUCCESS";
    }

    public Optional<Flashcard> getRandomFlashcard() {
        String sql = "SELECT fc_id, cat_name, fc_question, fc_answer FROM FLASHCARD JOIN CATEGORY ON fc_cat_id = cat_id ORDER BY RAND() LIMIT 1";
        RowMapper<Flashcard> mapper = (ResultSet rs, int rowNum) -> {
            Flashcard flashcard = new Flashcard();
            flashcard.setId(rs.getInt("fc_id"));
            flashcard.setCategoryName(rs.getString("cat_name"));
            flashcard.setQuestion(rs.getString("fc_question"));
            flashcard.setAnswer(rs.getString("fc_answer"));
            return flashcard;
        };
        try {
            return Optional.ofNullable(template.queryForObject(sql, mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Flashcard> findById(int id){
        String sql = "SELECT fc_id, cat_name, fc_question, fc_answer FROM FLASHCARD JOIN CATEGORY ON fc_cat_id = cat_id WHERE fc_id = :fc_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("fc_id",id);
        RowMapper<Flashcard> mapper = (ResultSet rs, int rowNum) -> {
            Flashcard flashcard = new Flashcard();
            flashcard.setId(rs.getInt("fc_id"));
            flashcard.setCategoryName(rs.getString("cat_name"));
            flashcard.setQuestion(rs.getString("fc_question"));
            flashcard.setAnswer(rs.getString("fc_answer"));
            return flashcard;
        };
        try {
            return Optional.ofNullable(paramTemplate.queryForObject(sql, namedParameters, mapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
