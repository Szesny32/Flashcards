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
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Repository
public class FlashcardsRepository {
    @Autowired
    private NamedParameterJdbcTemplate paramTemplate;
    @Autowired
    private JdbcTemplate template;

    @Autowired
    private CategoryRepository categoryRepository;

    public boolean save(Flashcard flashcard) {
        Category category = categoryRepository.findByName(flashcard.getCategoryName()).orElseThrow(() ->
                new RuntimeException("Failed to retrieve the newly inserted category"));

        String sql = "INSERT INTO FLASHCARD(fc_cat_id, fc_question, fc_answer) values (:category_id, :question, :answer)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("category_id", category.getId())
                .addValue("question", flashcard.getQuestion())
                .addValue("answer", flashcard.getAnswer());
        paramTemplate.update(sql, namedParameters);
        System.out.println("INSERTED NEW FLASHCARD");
        return true;
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM FLASHCARD WHERE FC_ID = :fc_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("fc_id", id);
        paramTemplate.update(sql, namedParameters);
        System.out.println("DELETED FLASHCARD ID: " + id);
        return true;
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

    public Optional<Flashcard> findById(int id) {
        String sql = "SELECT fc_id, cat_name, fc_question, fc_answer FROM FLASHCARD JOIN CATEGORY ON fc_cat_id = cat_id WHERE fc_id = :fc_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("fc_id", id);
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

    public List<Flashcard> findByCategory(String path) {
        String sql = "SELECT fc_id, cat_name, fc_question, fc_answer "
                + "FROM FLASHCARD JOIN CATEGORY ON fc_cat_id = cat_id "
                + "WHERE cat_id in ( SELECT DISTINCT cat_id FROM CATEGORY WHERE cat_path LIKE :cat_path )";

        SqlParameterSource namedParameters = new MapSqlParameterSource("cat_path", path + '%');
        RowMapper<Flashcard> mapper = (ResultSet rs, int rowNum) -> {
            Flashcard flashcard = new Flashcard();
            flashcard.setId(rs.getInt("fc_id"));
            flashcard.setCategoryName(rs.getString("cat_name"));
            flashcard.setQuestion(rs.getString("fc_question"));
            flashcard.setAnswer(rs.getString("fc_answer"));
            return flashcard;
        };
        return paramTemplate.query(sql, namedParameters, mapper);

    }


}
